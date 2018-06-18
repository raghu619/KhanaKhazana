package com.navoki.khanakhazana.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.navoki.khanakhazana.R;
import com.navoki.khanakhazana.fragments.VideoDetailFragment;
import com.navoki.khanakhazana.interfaces.OnFragmentListener;
import com.navoki.khanakhazana.utils.AppConstants;
import com.navoki.khanakhazana.utils.Globle;


public class VideoDetailActivity extends AppCompatActivity implements OnFragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            arguments.putInt(AppConstants.ARG_VIDEO_STEP,
                    getIntent().getIntExtra(AppConstants.ARG_VIDEO_STEP, -1));

            setTitle(getIntent().getStringExtra(AppConstants.ARG_RECIPE_NAME));

            VideoDetailFragment fragment = new VideoDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.item_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            overridePendingTransition(R.anim.anim_slide_in_from_left, R.anim.anim_slide_out_to_right);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_slide_in_from_left, R.anim.anim_slide_out_to_right);
    }

    @Override
    public void onFragmentRecreate(long seekTo, boolean isPlaying) {
        Bundle bundle = new Bundle();
        bundle.putLong(AppConstants.VID_SEEKTO, seekTo);
        bundle.putBoolean(AppConstants.VID_IS_PLAYING, isPlaying);
        Globle.getAppInstance().setBundle(bundle);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
