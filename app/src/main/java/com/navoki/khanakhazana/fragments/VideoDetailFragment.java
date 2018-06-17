package com.navoki.khanakhazana.fragments;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.navoki.khanakhazana.R;
import com.navoki.khanakhazana.interfaces.OnFragmentListener;
import com.navoki.khanakhazana.models.VideoStepModel;
import com.navoki.khanakhazana.utils.AppConstants;
import com.navoki.khanakhazana.utils.Globle;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class VideoDetailFragment extends Fragment {

    private VideoStepModel model;
    private SimpleExoPlayer mExoPlayer;
    Unbinder unbinder;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_desc)
    TextView txtDesc;
    @BindView(R.id.cardView)
    CardView cardView;
    @BindView(R.id.player)
    SimpleExoPlayerView mPlayerView;
    @BindView(R.id.txt_no_video)
    TextView txt_no_video;
    @BindView(R.id.img_sad_chef)
    ImageView img_sad_chef;

    private long seekTo = 0;
    private boolean isPlaying = false;
    private OnFragmentListener onFragmentListener;
    private Globle globle;

    public VideoDetailFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onFragmentListener = (OnFragmentListener) context;
        globle = Globle.getAppInstance();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int pos = getArguments().getInt(AppConstants.ARG_VIDEO_STEP, -1);
        if (pos != -1) {
            model = Globle.getAppInstance().getRecipesModel().getSteps().get(pos);
        }
        seekTo = globle.getBundle().getLong(AppConstants.VID_SEEKTO, 0);
        isPlaying = globle.getBundle().getBoolean(AppConstants.VID_IS_PLAYING, false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_video, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        txtTitle.setText(model.getShortDescription());
        txtDesc.setText(model.getDescription());
        Animation animation = AnimationUtils.loadAnimation(getContext(),
                R.anim.anim_from_bottom);
        rootView.startAnimation(animation);
        mPlayerView.setDefaultArtwork(BitmapFactory.decodeResource(getResources(), R.drawable.placeholder_video));

        if (model.getVideoURL().equals(AppConstants.NOT_AVAILABLE)) {
            img_sad_chef.setVisibility(View.VISIBLE);
            txt_no_video.setVisibility(View.VISIBLE);
        }
        return rootView;
    }

    private void initializePlayer(Uri uri) {
        if (mExoPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);

            String userAgent = Util.getUserAgent(getContext(), getContext().getString(R.string.app_name));
            MediaSource mediaSource = new ExtractorMediaSource(uri, new DefaultDataSourceFactory(getContext(), userAgent)
                    , new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(isPlaying);
            mExoPlayer.seekTo(seekTo);
        }
    }

    private void getCurrentData() {
        seekTo = mExoPlayer.getContentPosition();
        isPlaying = mExoPlayer.getPlayWhenReady();
    }

    private void releasePlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    private void pausePlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.setPlayWhenReady(false);
            mExoPlayer.getPlaybackState();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > Build.VERSION_CODES.M) {
            initializePlayer(Uri.parse(model.getVideoURL()));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <=  Build.VERSION_CODES.M || mExoPlayer == null)) {
            initializePlayer(Uri.parse(model.getVideoURL()));
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        getCurrentData();
        pausePlayer();
        onFragmentListener.onFragmentRecreate(seekTo, isPlaying);
        if (Util.SDK_INT <=  Build.VERSION_CODES.M) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT >  Build.VERSION_CODES.M) {
            releasePlayer();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        releasePlayer();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }
}
