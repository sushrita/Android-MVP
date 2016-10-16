package com.skedsoft.compartirit.home.player;

import com.skedsoft.compartirit.BasePresenter;
import com.skedsoft.compartirit.BaseView;

/**
 * CompartirIt, All rights Reserved
 * Created by Sanjeet on 14-Oct-16.
 */

 interface PlayerContract {
    interface View extends BaseView<Presenter> {
        void share(String link);
        void showPlayer(VideoType type,String link);
    }


    interface Presenter extends BasePresenter {
        void loadSettings();
        void prepareShare();
    }
}
