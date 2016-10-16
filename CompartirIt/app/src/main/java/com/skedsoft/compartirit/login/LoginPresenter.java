package com.skedsoft.compartirit.login;

import android.text.TextUtils;

import com.skedsoft.compartirit.data.Backend;
import com.skedsoft.compartirit.data.BackendSource;
import com.skedsoft.compartirit.data.User;

/**
 * Compartir It, All rights Reserved
 * Created by Sanjeet on 12-Oct-16.
 */
class LoginPresenter implements LoginContract.Presenter {
    private final Backend backend;
    private final LoginContract.View loginView;
    private boolean destroyed = false;

    LoginPresenter(Backend backend, LoginContract.View loginView) {
        this.backend = backend;
        this.loginView = loginView;
        this.loginView.setPresenter(this);
    }

    @Override
    public void login(User user) {
        /*Validate at least the empty condition before sending response to the API/Backend*/
        if (TextUtils.isEmpty(user.getUsername().trim())) {
            loginView.showError("User name cannot be empty!");
            return;
        }
        if (TextUtils.isEmpty(user.getPassword().trim())) {
            loginView.showError("Password cannot be empty!");
        }
        /*Change progress indicator state before calling the login method on backend*/
        loginView.showProgressIndicator(true);
        backend.login(user, new BackendSource.LoginCallback() {
            @Override
            public void onLoginSuccess(User user) {
                if (destroyed) return;//Don't call any view method if activity is destroyed!
                loginView.showProgressIndicator(false);
                loginView.showLoginSuccess(user);
            }

            @Override
            public void onLoginFailure(String error) {
                if (destroyed) return;//Don't call any view method if activity is destroyed!
                loginView.showProgressIndicator(false);
                loginView.showError(error);
            }
        });

    }

    @Override
    public void onDestroy() {
        this.destroyed = true;
    }

    /**
     * Navigates user to home if user is already logged in
     */
    @Override
    public void start() {
        User user = backend.getUser();
        if (user.isLoggedIn())//Give success callback if user has already logged in
            loginView.showLoginSuccess(user);
        else if (user.isRemember())//Set credentials if user has chosen to remember me
            loginView.remember(user);

    }
}
