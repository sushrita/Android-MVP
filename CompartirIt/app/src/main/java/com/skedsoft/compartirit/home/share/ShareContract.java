package com.skedsoft.compartirit.home.share;

import com.skedsoft.compartirit.BasePresenter;
import com.skedsoft.compartirit.BaseView;
import com.skedsoft.compartirit.data.Contact;

import java.util.List;

/**
 * CompartirIt, All rights Reserved
 * Created by Sanjeet on 14-Oct-16.
 */

/**
 * Contracts for the presentations
 */
interface ShareContract {
    interface View extends BaseView<Presenter> {
        void showTitle(int contactsCount);
        void sendMail(String title,String body, String[] recipients);
        void showContacts(List<Contact> contacts);
    }


    interface Presenter extends BasePresenter {
        void loadContacts();
        void share(String title, String body, List<Contact> contacts);
    }
}
