package com.skedsoft.compartirit.login;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.skedsoft.compartirit.R;
import com.skedsoft.compartirit.data.User;
import com.skedsoft.compartirit.home.HomeActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements LoginContract.View {

    private LoginContract.Presenter presenter;
    private EditText username;
    private EditText password;
    private CheckBox remember;
    private Button login;
    private ProgressBar progressBar;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        username = (EditText) view.findViewById(R.id.username);
        password = (EditText) view.findViewById(R.id.password);
        remember = (CheckBox) view.findViewById(R.id.remember);
        login = (Button) view.findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User(username.getText().toString(), password.getText().toString());
                user.setRemember(remember.isChecked());
                presenter.login(user);
            }
        });

        progressBar = (ProgressBar)view.findViewById(R.id.progress);
        return view;
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void showLoginSuccess(User user) {
        startActivity(new Intent(getActivity(), HomeActivity.class));
        getActivity().onBackPressed();
    }

    @Override
    public void remember(User user) {
        username.setText(user.getUsername());
        password.setText(user.getPassword());
        remember.setChecked(user.isRemember());
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showProgressIndicator(boolean active) {
        login.setClickable(!active);
        progressBar.setVisibility(active?View.VISIBLE:View.GONE);
    }

    @Override
    public void showError(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
    }

    /**
     * Let presenter no if the fragment has destroyed
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    /**
     * Start the presenter on resume
     */
    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }
}
