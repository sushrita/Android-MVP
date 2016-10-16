package com.skedsoft.compartirit.data;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;

import com.google.gson.Gson;
import com.skedsoft.compartirit.home.player.VideoType;

import java.util.ArrayList;
import java.util.List;

/**
 * Compartir It, All rights Reserved
 * Created by Sanjeet on 12-Oct-16.
 */

/**
 * The Singleton data source backend for the app
 */
public class Backend implements BackendSource {
    private Context context;
    private static Backend backend;
    private AssetManager manager;
    private SharedPreferences preferences;
    private ContentResolver contentResolver;

    private Backend(Context context) {
        manager = context.getAssets();
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        contentResolver = context.getContentResolver();
    }

    // Making backend Singleton, Please not here used context is and application context so there is
    // no chances of memory leak, also instead of storing the context in to static field , a filed
    // instantiation is used directly eg <<code>contentResolver</code> , <code>assetManager<> and
    // <code>preferences</code>
    public static Backend with(Context context) {
        return backend == null ? backend = new Backend(context) : backend;
    }

    /**
     * Log in to the simulated user account
     *
     * @param user     the @link {@link User}
     * @param callback the @link {@link LoginCallback}
     */
    @Override
    public void login(final User user, final LoginCallback callback) {
        //Dummy code to simulate a api like response
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               if (user.getUsername().equals("test05media") && user.getPassword().equals("test05media")) {
                    user.setLoggedIn(true);
                    saveUser(user);
                    callback.onLoginSuccess(user);
                } else
                    callback.onLoginFailure("Invalid credentials!");
            }
        }, 3000);
    }

    @Override
    public void logout(LogoutCallback logoutCallback) {
        User user = getUser();
        if (user.isRemember()) {
            user.setLoggedIn(false);
            saveUser(user);
        } else
            removeUser();
        logoutCallback.onLogoutSuccess();
    }

    /**
     * Requesting contacts on separate thread , on AsyncTask here
     *
     * @param contactsCallback return data over this callback
     */
    @Override
    public void getContacts(final ContactsCallback contactsCallback) {

        new AsyncTask<Void, Void, List<Contact>>() {

            @Override
            protected List<Contact> doInBackground(Void... params) {
                return fetchContacts();
            }

            @Override
            protected void onPostExecute(List<Contact> contacts) {
                super.onPostExecute(contacts);
                //return contacts if count is positive
                if (contacts.size() > 0) {
                    contactsCallback.onContactsFetched(contacts);
                } else contactsCallback.onContactsNotFetched("Cannot load contacts from phone!");
            }
        }.execute();
    }

    @Override
    public void getVideoLink(VideoType type, VideoCallback videoCallback) {
        if (type == VideoType.YOUTUBE)
            videoCallback.onVideoLinkFetched("1KhZKNZO8mQ");//Default video for youtube
        else if (type == VideoType.M3U8)//Default video for u3m8
            videoCallback.onVideoLinkFetched("http://skedsoft.com:3002/images/lets_wake_up.mp4");
        else videoCallback.onVideoLinkNotFetched("Video for this type is not available!");
    }

    /**
     * Fetching contacts from phone's contact database using content resolver
     *
     * @return @link {@link List<Contact>}
     */
    private ArrayList<Contact> fetchContacts() {
        ArrayList<Contact> contacts = new ArrayList<>();
        Uri URI_CONTACT_DATA = ContactsContract.Data.CONTENT_URI;
        String COLUMN_EMAIL = ContactsContract.CommonDataKinds.Email.ADDRESS;
        String COLUMN_DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME_PRIMARY;
        String COLUMN_MIME_TYPE = ContactsContract.Data.MIMETYPE;

        String[] PROJECTION = {
                COLUMN_DISPLAY_NAME,
                COLUMN_EMAIL,
                COLUMN_MIME_TYPE
        };
        String selection = COLUMN_MIME_TYPE + "=?";
        final String[] selectionArgs = {ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE};
        Cursor dataCursor = contentResolver.query(URI_CONTACT_DATA, PROJECTION, selection, selectionArgs, COLUMN_DISPLAY_NAME);
        if (dataCursor != null) {
            while (dataCursor.moveToNext()) {
                String name = dataCursor.getString(dataCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String email = dataCursor.getString(dataCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                if (email != null) {//adding if email is available
                    contacts.add(new Contact(name, email));
                }
            }
            dataCursor.close();
        }
        return contacts;
    }

    /**
     * Sets VideoType
     *
     * @param videoType @link {@link VideoType}
     */
    public void setVideoType(VideoType videoType) {
        preferences.edit().putInt("playerType", videoType.ordinal()).apply();
    }

    /**
     * Gets data from shared preferences and creates the enum instance
     *
     * @return @link {@link VideoType}
     */
    public VideoType getVideoType() {
        return VideoType.values()[preferences.getInt("playerType", 0)];
    }

    /**
     * Saves user to shared preference
     * @param user
     */
    private void saveUser(User user) {
        preferences.edit().putString("user", new Gson().toJson(user)).apply();
    }

    /**
     * Returns user instance from sharedpreference
     */
    public User getUser() {
        return new Gson().fromJson(preferences.getString("user", "{}"), User.class);
    }

    /**
     * Remove user from saved preferences
     */
    private void removeUser() {
        preferences.edit().clear().apply();
    }

}


