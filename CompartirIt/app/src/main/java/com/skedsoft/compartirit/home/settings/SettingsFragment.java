package com.skedsoft.compartirit.home.settings;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.skedsoft.compartirit.R;
import com.skedsoft.compartirit.home.player.VideoType;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment implements SettingsContract.View {
    private RadioGroup toggle;
    private SettingsContract.Presenter presenter;

    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        toggle = (RadioGroup) view.findViewById(R.id.toggle);
        toggle.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //Saves video type on toggle
                    presenter.setVideoType(checkedId == R.id.youtube ?
                            VideoType.YOUTUBE :
                            VideoType.M3U8);

            }
        });
        return view;
    }

    @Override
    public void setPresenter(SettingsContract.Presenter presenter) {
        this.presenter = presenter;
    }

    /**
     * Supposed to show progress view if required
     *
     * @param active true if background task is being done
     */
    /*unused*/
    @Override
    public void showProgressIndicator(boolean active) {

    }

    /**
     * Shows the error if occurs
     *
     * @param error the error message
     */
    @Override
    public void showError(String error) {
        Snackbar.make(toggle, error, Snackbar.LENGTH_LONG).show();
    }

    /**
     * Shows checked button on toggle widget,
     * Here give toggle widget is just made of radio group.
     *
     * @param type @link {@link VideoType}
     */
    @Override
    public void showVideoType(VideoType type) {
        toggle.check(type == VideoType.YOUTUBE ? R.id.youtube : R.id.m3u8);
    }

    /**
     * Starts the presenter when resumes this fragments, to load default selected settings
     */
    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }
}
