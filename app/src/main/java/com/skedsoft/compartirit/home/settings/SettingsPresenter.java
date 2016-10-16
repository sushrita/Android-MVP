package com.skedsoft.compartirit.home.settings;

import com.skedsoft.compartirit.data.Backend;
import com.skedsoft.compartirit.home.player.VideoType;

/**
 * CompartirIt, All rights Reserved
 * Created by Sanjeet on 14-Oct-16.
 */

public class SettingsPresenter implements SettingsContract.Presenter {
    private final Backend backend;
    @SuppressWarnings("FieldCanBeLocal")
    private final SettingsContract.View settingsView;
    private boolean destroyed = false;

    public SettingsPresenter(Backend backend, SettingsContract.View settingsView) {
        this.backend = backend;
        this.settingsView = settingsView;
        this.settingsView.setPresenter(this);
    }

    @Override
    public void onDestroy() {
        destroyed = true;
    }

    @Override
    public void start() {
        loadVideoType();
    }

    @Override
    public void setPlayer(boolean isDefaultPlayer) {
    }

    @Override
    public void setVideoType(VideoType type) {
        if (destroyed) return;
        backend.setVideoType(type);
    }

    @Override
    public void loadVideoType() {
        settingsView.showVideoType(backend.getVideoType());
    }
}
