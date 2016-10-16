package com.skedsoft.compartirit.home.share;

import android.os.Bundle;

import com.skedsoft.compartirit.BaseActivity;
import com.skedsoft.compartirit.R;
import com.skedsoft.compartirit.data.Backend;
import com.skedsoft.compartirit.util.ActivityUtils;

/**
 * Activity presents selectable contacts list to share the link with users from the phone book
 */
public class ShareActivity extends BaseActivity {

    public static final String EXTRA_LINK = "link";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        enableBackPress();
        ShareFragment shareFragment =
                (ShareFragment) getSupportFragmentManager().findFragmentById(R.id.container);
        String link = getIntent().getStringExtra(EXTRA_LINK);
        if (shareFragment == null) {
            // Create the fragment
            shareFragment = ShareFragment.newInstance(link);
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), shareFragment, R.id.container);
        }

        new SharePresenter(Backend.with(getApplicationContext()), shareFragment);
    }
}
