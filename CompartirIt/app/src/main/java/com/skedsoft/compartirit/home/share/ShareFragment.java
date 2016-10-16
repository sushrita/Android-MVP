package com.skedsoft.compartirit.home.share;


import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skedsoft.compartirit.BaseActivity;
import com.skedsoft.compartirit.R;
import com.skedsoft.compartirit.data.Contact;
import com.skedsoft.compartirit.home.contacts.ContactAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */

/**
 * Fragment used to share the link to contacts
 */
public class ShareFragment extends Fragment implements ShareContract.View, View.OnClickListener {

    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 1245;
    private ShareContract.Presenter presenter;
    private List<Contact> contactList;
    private ContactAdapter contactAdapter;
    private RecyclerView contactsView;
    private FloatingActionButton fab;

    public ShareFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_share, container, false);
        contactsView = (RecyclerView) view.findViewById(R.id.contactsList);
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(this);
        contactList = new ArrayList<>();
        contactsView.setLayoutManager(new LinearLayoutManager(getActivity()));
        contactAdapter = new ContactAdapter(contactList, ContactAdapter.Mode.MULTIPLE);
        contactsView.setAdapter(contactAdapter);
        checkPermissionAndLoadContacts();
        return view;
    }

    @Override
    public void setPresenter(ShareContract.Presenter presenter) {
        this.presenter = presenter;
    }

    /**
     * Show and hide progress / loading when a task is suppose to take more time
     *
     * @param active true/false
     */
    @Override
    public void showProgressIndicator(boolean active) {

    }

    /**
     * Shows if error occurred during presentations in Snackbar
     *
     * @param error the error to show the user as Snackbar
     */
    @Override
    public void showError(String error) {
        Snackbar.make(contactsView, error, Snackbar.LENGTH_LONG).show();
    }

    /**
     * Sets number of available contacts to actionbar subtitle
     *
     * @param contactsCount number of contacts
     */
    @Override
    public void showTitle(int contactsCount) {
        ActionBar actionBar = ((BaseActivity) getActivity()).getSupportActionBar();
        if (actionBar != null)
            actionBar.setSubtitle(getString(R.string.title_contacts, contactsCount));

    }

    @Override
    public void sendMail(String title, String body, String[] recipients) {
        //Send email and ensure the no client found case
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_EMAIL, recipients);
            intent.putExtra(Intent.EXTRA_SUBJECT, title);
            intent.putExtra(Intent.EXTRA_TEXT, body);
            intent.setType("message/rfc822");
            startActivity(Intent.createChooser(intent, "Choose an email app"));
        } catch (ActivityNotFoundException e) {
            showError("There is no default app to send the email!");
        }

    }

    /**
     * Adds all contacts received to @param contactList
     *
     * @param contacts fetched contacts
     */
    @Override
    public void showContacts(List<Contact> contacts) {
        contactList.clear();
        contactList.addAll(contacts);
        contactAdapter.notifyDataSetChanged();
    }

    /**
     * Static method to create fragment instance with bundle as a parameter
     *
     * @param link link to be shared
     * @return instance of this fragment
     */
    public static ShareFragment newInstance(String link) {
        Bundle bundle = new Bundle();
        bundle.putString(ShareActivity.EXTRA_LINK, link);
        ShareFragment shareFragment = new ShareFragment();
        shareFragment.setArguments(bundle);
        return shareFragment;
    }

    @Override
    public void onClick(View v) {
        presenter.share(getString(R.string.app_name),
                getString(R.string.share_body, getArguments().getString(ShareActivity.EXTRA_LINK)),
                contactAdapter.getSelectedContacts());

    }

    private void checkPermissionAndLoadContacts() {
        int hasPermission = ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_CONTACTS);
        if (hasPermission == PackageManager.PERMISSION_GRANTED) {
            //if user has granted the permission
            presenter.loadContacts();
        } else {
            requestPermissions(
                    new String[]{Manifest.permission.READ_CONTACTS},
                    PERMISSIONS_REQUEST_READ_CONTACTS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    checkPermissionAndLoadContacts();
                } else {
                    showError("You cannot access your contacts unless you allow to read!");
                }
            }
        }
    }
}
