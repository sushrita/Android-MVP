package com.skedsoft.compartirit.login;

import android.os.Bundle;

import com.skedsoft.compartirit.BaseActivity;
import com.skedsoft.compartirit.R;
import com.skedsoft.compartirit.data.Backend;
import com.skedsoft.compartirit.util.ActivityUtils;

/**
 * Login Screen used as launcher Activity
 */
public class LoginActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LoginFragment loginFragment =
                (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (loginFragment == null) {
            // Create the fragment
            loginFragment = LoginFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), loginFragment, R.id.contentFrame);
        }
        //Instantiating the Presenter
        new LoginPresenter(Backend.with(getApplicationContext()), loginFragment);
    }
}
