package com.skedsoft.compartirit.home.contacts;

import com.skedsoft.compartirit.BasePresenter;
import com.skedsoft.compartirit.BaseView;
import com.skedsoft.compartirit.data.Contact;

import java.util.List;

/**
 * CompartirIt, All rights Reserved
 * Created by Sanjeet on 14-Oct-16.
 */

public interface ContactsContract {

    interface View extends BaseView<Presenter> {
        void showContacts(List<Contact> contacts);
    }


    interface Presenter extends BasePresenter {

        void loadContacts();
    }
}
