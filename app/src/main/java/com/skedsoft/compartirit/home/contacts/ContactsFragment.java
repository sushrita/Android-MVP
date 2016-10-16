package com.skedsoft.compartirit.home.contacts;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skedsoft.compartirit.R;
import com.skedsoft.compartirit.data.Contact;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 */
public class ContactsFragment extends Fragment implements ContactsContract.View {

    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 1254;
    private ContactsContract.Presenter presenter;
    private List<Contact> contactList;
    private ContactAdapter contactAdapter;

    public ContactsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        RecyclerView contactsView = (RecyclerView) inflater.inflate(R.layout.fragment_contact, container, false);
        contactList = new ArrayList<>();
        contactsView.setLayoutManager(new LinearLayoutManager(getActivity()));
        contactAdapter = new ContactAdapter(contactList, ContactAdapter.Mode.NONE);
        contactsView.setAdapter(contactAdapter);

        checkPermissionAndLoadContacts();
        return contactsView;
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
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void setPresenter(ContactsContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showProgressIndicator(boolean active) {

    }

    @Override
    public void showError(String error) {
        if (getView() == null) return;
        Snackbar.make(getView(), error, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showContacts(List<Contact> contacts) {
        contactList.clear();
        contactList.addAll(contacts);
        contactAdapter.notifyDataSetChanged();
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
