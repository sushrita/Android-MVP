package com.skedsoft.compartirit.home.player;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.skedsoft.compartirit.R;
import com.skedsoft.compartirit.home.share.ShareActivity;
import com.skedsoft.compartirit.util.ActivityUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlayerFragment extends Fragment implements PlayerContract.View, View.OnClickListener {
    private PlayerContract.Presenter presenter;
    private VideoType runningVideoType;//keeps last player state(default or m3u8) for the current activity like cycle

    public PlayerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_player, container, false);
        runningVideoType = VideoType.NONE;//The you tube case default and hence assigning un-true condition
        return view;
    }

    @Override
    public void setPresenter(PlayerContract.Presenter presenter) {
        this.presenter = presenter;
    }

    /**
     * Can be used when some background task is running , like waiting for response from server
     *
     * @param active if task is active
     */
    /*unused*/
    @Override
    public void showProgressIndicator(boolean active) {

    }

    /**
     * Shows error to the user in Snackbar
     *
     * @param error the error message
     */
    @Override
    public void showError(String error) {
        if (getView() == null) return;
        Snackbar.make(getView(), error, Snackbar.LENGTH_LONG).show();
    }

    /**
     * Choosing the player type based upon setting
     * Note that a youtube video cannot be played in the normal @link {@link android.widget.VideoView}
     * for this either we can show in web view or we can have Youtube APIs. Here youtube api is used
     * shows video in an fragment from the Youtube APIS @link {@link com.google.android.youtube.player.YouTubePlayerSupportFragment}
     *
     * @param type @link {@link VideoType}
     */
    private void choosePlayer(VideoType type, String link) {
        if (type == VideoType.YOUTUBE) {
            YouTubeVideoFragment youTubeVideoFragment = null;

            if (getChildFragmentManager().findFragmentById(R.id.container) instanceof YouTubeVideoFragment) {
                youTubeVideoFragment = (YouTubeVideoFragment) getChildFragmentManager().findFragmentById(R.id.container);
            }
            if (youTubeVideoFragment == null) {
                // Create the fragment
                youTubeVideoFragment = YouTubeVideoFragment.newInstance(link);
                if (getActivity() != null)
                    ActivityUtils.replaceFragmentToActivity(
                            getChildFragmentManager(), youTubeVideoFragment, R.id.container);
            }
        } else {

            SimplePlayerFragment simplePlayerFragment = null;

            if (getChildFragmentManager().findFragmentById(R.id.container) instanceof SimplePlayerFragment) {
                simplePlayerFragment = (SimplePlayerFragment) getChildFragmentManager().findFragmentById(R.id.container);
            }
            if (simplePlayerFragment == null) {
                // Create the fragment
                simplePlayerFragment = SimplePlayerFragment.newInstance(link);
                if (getActivity() != null)
                    ActivityUtils.replaceFragmentToActivity(
                            getChildFragmentManager(), simplePlayerFragment, R.id.container);

            }
        }
    }

    @Override
    public void share(String link) {
        Intent intent = new Intent(getActivity(), ShareActivity.class);
        intent.putExtra(ShareActivity.EXTRA_LINK, link);
        startActivity(intent);
    }

    /**
     * Saves current videoType applied
     *
     * @param type @link {@link VideoType}
     */
    @Override
    public void showPlayer(VideoType type, String link) {
        if (runningVideoType != type) {
            choosePlayer(type, link);
            runningVideoType = type;
        }
    }

    @Override
    public void onClick(View view) {
        presenter.prepareShare();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        if (presenter == null) {
            System.out.println("Null presenter");
            return;
        }
        presenter.loadSettings();

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_player, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_share)
            presenter.prepareShare();
        return super.onOptionsItemSelected(item);
    }

    private PlayerContract.Presenter getPresenter() {
        return presenter;
    }
}
