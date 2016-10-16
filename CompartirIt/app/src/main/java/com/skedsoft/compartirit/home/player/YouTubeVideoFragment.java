package com.skedsoft.compartirit.home.player;

import android.os.Bundle;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

/**
 * CompartirIt, All rights Reserved
 * Created by Sanjeet on 14-Oct-16.
 */

public class YouTubeVideoFragment extends YouTubePlayerSupportFragment implements YouTubePlayer.OnInitializedListener {

    private static final String ARG_URL = "url";
    private YouTubePlayer player;
    private String videoId;

    public static YouTubeVideoFragment newInstance(String url) {
        Bundle bundle = new Bundle();
        bundle.putString(ARG_URL, url);
        YouTubeVideoFragment fragment = new YouTubeVideoFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialize("AIzaSyAi16mQ7dncI0Cbbp4FLPfrCQWn2zlU8wg", this);
    }

    @Override
    public void onDestroy() {
        if (player != null) {
            player.release();
        }
        super.onDestroy();
    }

    public void setVideoId(String videoId) {
        if (videoId != null && !videoId.equals(this.videoId)) {
            this.videoId = videoId;
            if (player != null) {
                player.cueVideo(videoId);
            }
        }
    }

    public void pause() {
        if (player != null) {
            player.pause();
        }
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean restored) {
        this.player = player;
        player.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT);
        if (!restored && videoId != null) {
            player.cueVideo(videoId);
        }
        player.cueVideo("1KhZKNZO8mQ");
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult result) {
        this.player = null;
    }

}
