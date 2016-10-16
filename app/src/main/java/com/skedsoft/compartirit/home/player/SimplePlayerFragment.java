package com.skedsoft.compartirit.home.player;


import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.VideoView;

import com.skedsoft.compartirit.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class SimplePlayerFragment extends Fragment {


    private static final String ARG_URL = "url";

    public SimplePlayerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_simple_player, container, false);;
        VideoView mVideoView = (VideoView) view.findViewById(R.id.videoView);
        final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progress);
        Uri uri = Uri.parse(getArguments().getString(ARG_URL));
        mVideoView.setVideoURI(uri);
        mVideoView.setMediaController(new MediaController(getActivity()));
        mVideoView.requestFocus();
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                progressBar.setVisibility(View.GONE);
                mp.start();
            }
        });
        return view;
    }

    public static SimplePlayerFragment newInstance(String url) {
        Bundle bundle = new Bundle();
        bundle.putString(ARG_URL, url);
        SimplePlayerFragment fragment = new SimplePlayerFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

}
