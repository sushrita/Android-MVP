package com.skedsoft.compartirit.login;

import com.skedsoft.compartirit.BasePresenter;
import com.skedsoft.compartirit.BaseView;
import com.skedsoft.compartirit.data.User;

/**
 * Compartir It, All rights Reserved
 * Created by Sanjeet on 12-Oct-16.
 */

/**
 * The contract for the login fragment/activity
 */
interface LoginContract {
    interface View extends BaseView<Presenter> {
        void showLoginSuccess(User user);

        void remember(User user);
    }

    interface Presenter extends BasePresenter {
        void login(User user);
    }
}
