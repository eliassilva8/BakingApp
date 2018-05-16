package com.eliassilva.bakingapp.activities;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.eliassilva.bakingapp.R;
import com.eliassilva.bakingapp.Step;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Elias on 02/05/2018.
 */
public class StepDetailsFragment extends Fragment {
    private Step mStep;
    @BindView(R.id.step_video)
    PlayerView mStepVideo;
    @BindView(R.id.step_description_tv)
    TextView mStepDescription;
    @BindView(R.id.step_image_iv)
    ImageView mStepImage;
    SimpleExoPlayer mExoPlayer;
    String mDescription;
    String mVideoUrl;
    String mImageUrl;
    Long mVideoPosition;
    public static final String DESCRIPTION = "description";
    public static final String VIDEO_URL = "video_url";
    private static final String VIDEO_POSITION = "video_position";
    private static final String IMAGE_URL = "image_url";
    private static final String VIDEO_STATE = "video_state";
    private boolean mPlayWhenReady = true;
    private int mCurrentWindow;

    public StepDetailsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mDescription = savedInstanceState.getString(DESCRIPTION);
            mVideoUrl = savedInstanceState.getString(VIDEO_URL);
            mImageUrl = savedInstanceState.getString(IMAGE_URL);
            mVideoPosition = savedInstanceState.getLong(VIDEO_POSITION);
            mPlayWhenReady = savedInstanceState.getBoolean(VIDEO_STATE);
        } else {
            mDescription = mStep.getDescription();
            mVideoUrl = mStep.getVideoUrl();
            mImageUrl = mStep.getImageUrl();
            mVideoPosition = C.TIME_UNSET;
            mPlayWhenReady = true;
        }

        final View rootView = inflater.inflate(R.layout.fragment_step_details, container, false);
        ButterKnife.bind(this, rootView);

        int display_mode = getResources().getConfiguration().orientation;
        if (display_mode == Configuration.ORIENTATION_LANDSCAPE && !getResources().getBoolean(R.bool.isTablet)) {
            mStepDescription.setVisibility(View.GONE);
            mStepVideo.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        } else {
            mStepDescription.setText(mDescription);
        }
        if (mVideoUrl.isEmpty() || mVideoUrl == null) {
            mStepVideo.setVisibility(View.GONE);
        }
        if (mImageUrl.isEmpty() || mImageUrl == null) {
            mStepImage.setVisibility(View.GONE);
        } else {
            Picasso.get().load(mImageUrl).into(mStepImage);
        }
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23 || mExoPlayer == null)) {
            initializePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    private void initializePlayer() {
        mExoPlayer = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(getActivity()),
                new DefaultTrackSelector(),
                new DefaultLoadControl());

        mStepVideo.setPlayer(mExoPlayer);
        mExoPlayer.seekTo(mCurrentWindow, mVideoPosition);
        mExoPlayer.setPlayWhenReady(mPlayWhenReady);

        MediaSource videoSource = new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory(getResources().getString(R.string.app_name)))
                .createMediaSource(Uri.parse(mVideoUrl));

        mExoPlayer.prepare(videoSource, false, false);
    }

    public void setStepData(Step step) {
        mStep = step;
    }

    private void releasePlayer() {
        if (mExoPlayer != null) {
            mVideoPosition = mExoPlayer.getCurrentPosition();
            mCurrentWindow = mExoPlayer.getCurrentWindowIndex();
            mPlayWhenReady = mExoPlayer.getPlayWhenReady();
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        releasePlayer();
        outState.putString(DESCRIPTION, mDescription);
        outState.putString(VIDEO_URL, mVideoUrl);
        outState.putLong(VIDEO_POSITION, mVideoPosition);
        outState.putString(IMAGE_URL, mImageUrl);
        outState.putBoolean(VIDEO_STATE, mPlayWhenReady);
    }
}
