package com.skedsoft.compartirit.home.player;

import com.skedsoft.compartirit.data.Backend;
import com.skedsoft.compartirit.data.BackendSource;

/**
 * CompartirIt, All rights Reserved
 * Created by Sanjeet on 14-Oct-16.
 */

public class PlayerPresenter implements PlayerContract.Presenter {
    private final Backend backend;
    private final PlayerContract.View playerView;
    private boolean destroyed = false;

    public PlayerPresenter(Backend backend, PlayerContract.View playerView) {
        this.backend = backend;
        this.playerView = playerView;
        this.playerView.setPresenter(this);
    }

    @Override
    public void onDestroy() {
        destroyed = true;
    }

    @Override
    public void start() {

    }

    @Override
    public void loadSettings() {
        if (destroyed) return;
        final VideoType type = backend.getVideoType();
        backend.getVideoLink(type, new BackendSource.VideoCallback() {
            @Override
            public void onVideoLinkFetched(String url) {
                playerView.showPlayer(type, url);
            }

            @Override
            public void onVideoLinkNotFetched(String error) {
                playerView.showError(error);
            }
        });
    }

    @Override
    public void prepareShare() {
        if (destroyed) return;
        final VideoType type = backend.getVideoType();
        backend.getVideoLink(type, new BackendSource.VideoCallback() {
            @Override
            public void onVideoLinkFetched(String url) {
                if (type == VideoType.M3U8)
                    playerView.share(url);
                else if (type == VideoType.YOUTUBE)
                    playerView.share("https//youtube.com/video?link=" + url);
            }

            @Override
            public void onVideoLinkNotFetched(String error) {
                playerView.showError(error);
            }
        });
    }
}
