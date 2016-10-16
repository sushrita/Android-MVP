package com.skedsoft.compartirit.home.contacts;

import com.skedsoft.compartirit.data.Backend;
import com.skedsoft.compartirit.data.BackendSource;
import com.skedsoft.compartirit.data.Contact;

import java.util.List;

/**
 * CompartirIt, All rights Reserved
 * Created by Sanjeet on 14-Oct-16.
 */

public class ContactsPresenter implements ContactsContract.Presenter {
    private final Backend backend;
    private final ContactsContract.View contactView;
    private boolean destroyed = false;

    public ContactsPresenter(Backend backend, ContactsContract.View contactView) {
        this.backend = backend;
        this.contactView = contactView;
        this.contactView.setPresenter(this);
    }

    @Override
    public void onDestroy() {
        destroyed = true;
    }

    @Override
    public void start() {

    }

    @Override
    public void loadContacts() {
        backend.getContacts(new BackendSource.ContactsCallback() {
            @Override
            public void onContactsFetched(List<Contact> contacts) {
                contactView.showContacts(contacts);
            }

            @Override
            public void onContactsNotFetched(String error) {
                contactView.showError(error);
            }
        });
    }
}
