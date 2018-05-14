package com.eliassilva.bakingapp.activities;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.eliassilva.bakingapp.Ingredient;
import com.eliassilva.bakingapp.R;
import com.eliassilva.bakingapp.Step;
import com.eliassilva.bakingapp.adapters.IngredientAdapter;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Elias on 02/05/2018.
 */
public class StepDetailsFragment extends Fragment{
    private Step mStep;
    @BindView(R.id.step_video)
    PlayerView mStepVideo;
    @BindView(R.id.step_description_tv)
    TextView mStepDescription;
    SimpleExoPlayer mExoPlayer;
    String mDescription;
    String mVideoUrl;
    public static final String DESCRIPTION = "description";
    public static final String VIDEO_URL = "video_url";



    public StepDetailsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mDescription = savedInstanceState.getString(DESCRIPTION);
            mVideoUrl = savedInstanceState.getString(VIDEO_URL);
        } else {
           mDescription = mStep.getDescription();
           mVideoUrl = mStep.getVideoUrl();
        }

        final View rootView = inflater.inflate(R.layout.fragment_step_details, container, false);
        ButterKnife.bind(this, rootView);
        mStepDescription.setText(mDescription);
        if (mVideoUrl.isEmpty() || mStepVideo == null) {
            mStepVideo.setVisibility(View.GONE);
        } else {
            initializePlayer(Uri.parse(mVideoUrl));
        }
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    private void initializePlayer(Uri uri) {
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

        mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector);
        mStepVideo.setPlayer(mExoPlayer);

        int display_mode = getResources().getConfiguration().orientation;
        if (display_mode == Configuration.ORIENTATION_LANDSCAPE && !getResources().getBoolean(R.bool.isTablet)) {
            ViewGroup.LayoutParams params = mStepVideo.getLayoutParams();
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
            mStepVideo.setLayoutParams(params);
            mStepDescription.setVisibility(View.GONE);
        }

        DefaultBandwidthMeter bandwidthMeter2 = new DefaultBandwidthMeter();
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getActivity(), Util.getUserAgent(getActivity(), getResources().getString(R.string.app_name)), bandwidthMeter2);
        MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
        mExoPlayer.prepare(videoSource);
    }

    public void setStepData(Step step) {
        mStep = step;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(DESCRIPTION, mDescription);
        outState.putString(VIDEO_URL, mVideoUrl);
    }
}
