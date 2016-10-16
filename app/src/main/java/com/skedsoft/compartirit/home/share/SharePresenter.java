package com.skedsoft.compartirit.home.share;

import com.skedsoft.compartirit.data.Backend;
import com.skedsoft.compartirit.data.BackendSource;
import com.skedsoft.compartirit.data.Contact;

import java.util.List;

/**
 * CompartirIt, All rights Reserved
 * Created by Sanjeet on 14-Oct-16.
 */

class SharePresenter implements ShareContract.Presenter {
    private final Backend backend;
    private final ShareContract.View shareView;
    private boolean destroyed = false;

    SharePresenter(Backend backend, ShareContract.View shareView) {
        this.backend = backend;
        this.shareView = shareView;
        this.shareView.setPresenter(this);
    }

    @Override
    public void onDestroy() {
        destroyed = true;
    }

    @Override
    public void start() {

    }

    /**
     * Loads contacts from the backend
     */
    @Override
    public void loadContacts() {
        backend.getContacts(new BackendSource.ContactsCallback() {
            @Override
            public void onContactsFetched(List<Contact> contacts) {
                if (destroyed) return;
                shareView.showTitle(contacts.size());
                shareView.showContacts(contacts);
            }

            @Override
            public void onContactsNotFetched(String error) {
                if (destroyed) return;
                shareView.showError(error);
            }
        });
    }

    /**
     * Shares the contacts if there is one contact selected at least
     */
    @Override
    public void share(String title, String body, List<Contact> contacts) {
        if (contacts.size() > 0)
            shareView.sendMail(title, body, parseRecipients(contacts));
        else shareView.showError("Please select at least once contact to share!");
    }

    /**
     * Creates arrays of email form the ArrayList
     *
     * @param contacts the @link {@link java.util.ArrayList<Contact>}
     * @return {@link String[]}  of emails
     */
    private String[] parseRecipients(List<Contact> contacts) {
        String[] recipients = new String[contacts.size()];
        for (int i = 0; i < contacts.size(); i++) {
            recipients[i] = contacts.get(i).getEmail();
        }
        return recipients;
    }
}
