package com.skedsoft.compartirit.home;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.skedsoft.compartirit.R;
import com.skedsoft.compartirit.data.Backend;
import com.skedsoft.compartirit.data.BackendSource;
import com.skedsoft.compartirit.home.contacts.ContactsFragment;
import com.skedsoft.compartirit.home.contacts.ContactsPresenter;
import com.skedsoft.compartirit.home.player.PlayerFragment;
import com.skedsoft.compartirit.home.player.PlayerPresenter;
import com.skedsoft.compartirit.home.settings.SettingsFragment;
import com.skedsoft.compartirit.home.settings.SettingsPresenter;

/**
 * Activity holds all fragment and home screen widgets for user interactions
 */
@SuppressWarnings("ConstantConditions")
public class HomeActivity extends AppCompatActivity {

    private PlayerPresenter playerPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final HomePagerAdapter homePagerAdapter = new HomePagerAdapter(getSupportFragmentManager());

        ViewPager mViewPager = (ViewPager) findViewById(R.id.container);
        //Setting offscreen limits two prevents the recreation of fragments going beyond the
        //default offscreen limit which is 1
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(homePagerAdapter);
        //Adding tab layout once adapter is set to view pager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        /**
         * Adding page change listener to the pager adapter
         */
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //Reloads the video when user navigates back to the first fragment in the tab
                if (position == 0) {
                    if (playerPresenter == null) {//Not supposed to be happen
                        return;
                    }
                    playerPresenter.loadSettings();//calls presenter to reload the settings
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    /**
     * Used to present logout actions, Alert dialog comes when user presses the logout option
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            new AlertDialog.Builder(this)
                    .setMessage(R.string.alert_logout)
                    .setPositiveButton(R.string.action_logout, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Logout this user if chooses logout option from dialog
                            Backend.with(getApplicationContext()).logout(new BackendSource.LogoutCallback() {
                                @Override
                                public void onLogoutSuccess() {
                                    finish();
                                }

                                @Override
                                public void onLogoutFailure() {

                                }
                            });
                        }
                    }).setNegativeButton(android.R.string.cancel, null).show();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * FragmentPagerAdapter for the tabs used in the home screen
     * Its also instantiates the presenter when creates new fragment instances
     */
    public class HomePagerAdapter extends FragmentPagerAdapter {

        HomePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Overridden to ensure that presenter get instantiated when new instance of the fragment
         * is (Here the PlayerFragment) is being created.
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Object object = super.instantiateItem(container, position);
            if (object instanceof PlayerFragment) {//Create instance of PlayerPresenter if its a new instance of PlayerFragment
                playerPresenter = new PlayerPresenter(Backend.with(getApplicationContext()), (PlayerFragment) object);
            }
            return object;
        }

        /**
         * All fragment are retained to save recreation of the fragments
         * Along with the fragment creation , there corresponding presenter are also being initialized.
         */
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    PlayerFragment playerFragment = new PlayerFragment();
                    playerFragment.setRetainInstance(true);
                    playerPresenter = new PlayerPresenter(Backend.with(getApplicationContext()), playerFragment);
                    return playerFragment;
                case 1:
                    SettingsFragment settingsFragment = new SettingsFragment();
                    settingsFragment.setRetainInstance(true);
                    new SettingsPresenter(Backend.with(getApplicationContext()), settingsFragment);
                    return settingsFragment;
                case 2:
                    ContactsFragment contactsFragment = new ContactsFragment();
                    contactsFragment.setRetainInstance(true);
                    new ContactsPresenter(Backend.with(getApplicationContext()), contactsFragment);
                    return contactsFragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.title_tab_watch_now);
                case 1:
                    return getString(R.string.title_tab_prefereces);
                case 2:
                    return getString(R.string.title_tab_peoples);
            }
            return null;
        }
    }
}
