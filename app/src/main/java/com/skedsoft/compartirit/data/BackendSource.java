package com.skedsoft.compartirit.data;

import com.skedsoft.compartirit.home.player.VideoType;

import java.util.List;

/**
 * CompartirIt, All rights Reserved
 * Created by Sanjeet on 13-Oct-16.
 */

public interface BackendSource {
    interface LoginCallback {
        void onLoginSuccess(User user);

        void onLoginFailure(String error);
    }

    void login(User user, LoginCallback callback);

    interface LogoutCallback{
        void onLogoutSuccess();
        void onLogoutFailure();
    }

    void logout(LogoutCallback logoutCallback);
    interface ContactsCallback {

        void onContactsFetched(List<Contact> contacts);

        void onContactsNotFetched(String error);
    }

    void getContacts(ContactsCallback contactsCallback);

    interface VideoCallback{
        void onVideoLinkFetched(String url);
        void onVideoLinkNotFetched(String error);
    }

    void getVideoLink(VideoType videoType ,VideoCallback videoCallback);
}
