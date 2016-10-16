package com.skedsoft.compartirit.home.settings;

import com.skedsoft.compartirit.BasePresenter;
import com.skedsoft.compartirit.BaseView;
import com.skedsoft.compartirit.home.player.VideoType;

/**
 * CompartirIt, All rights Reserved
 * Created by Sanjeet on 14-Oct-16.
 */

/**
 * Setting Contract Interface
 */
 interface SettingsContract {
    interface View extends BaseView<Presenter> {
        void showVideoType(VideoType type);
    }


    interface Presenter extends BasePresenter {
        void setPlayer(boolean isDefaultPlayer);
        void setVideoType(VideoType type);
        void loadVideoType();
    }
}
