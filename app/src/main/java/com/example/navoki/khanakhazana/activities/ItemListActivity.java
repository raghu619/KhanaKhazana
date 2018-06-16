package com.example.navoki.khanakhazana.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.navoki.khanakhazana.R;
import com.example.navoki.khanakhazana.adapters.StepsAdapter;
import com.example.navoki.khanakhazana.fragments.VideoDetailFragment;
import com.example.navoki.khanakhazana.interfaces.OnAdapterClickListener;
import com.example.navoki.khanakhazana.models.RecipesModel;
import com.example.navoki.khanakhazana.utils.AppConstants;
import com.example.navoki.khanakhazana.utils.Globle;
import com.example.navoki.khanakhazana.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ItemListActivity extends AppCompatActivity implements OnAdapterClickListener {


    private boolean mTwoPane;
    private Context context;
    private RecipesModel recipesModel;

    @BindView(R.id.item_list)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = this;
        recipesModel = Globle.getAppInstance().getRecipesModel();

        if (findViewById(R.id.item_detail_container) != null) {
            mTwoPane = true;
        }

        if (null != recipesModel) {
            setTitle(recipesModel.getName());
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
            final StepsAdapter adapter = new StepsAdapter(context, recipesModel.getIngredients(), recipesModel.getSteps());

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    recyclerView.setAdapter(adapter);
                }
            }, 200);
        } else
            Toast.makeText(context, getString(R.string.somethingGoneWrong), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            overridePendingTransition(R.anim.anim_slide_in_from_left, R.anim.anim_slide_out_to_right);
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_slide_in_from_left, R.anim.anim_slide_out_to_right);
    }

    @Override
    public void onItemClick(int pos, ImageView imageView) {

        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putInt(AppConstants.ARG_VIDEO_STEP, pos);
            VideoDetailFragment fragment = new VideoDetailFragment();
            fragment.setArguments(arguments);
            ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction()
                    .replace(R.id.item_detail_container, fragment)
                    .commit();
        } else {
            Intent intent = new Intent(context, VideoDetailActivity.class);
            intent.putExtra(AppConstants.ARG_VIDEO_STEP, pos);
            intent.putExtra(AppConstants.ARG_RECIPE_NAME, recipesModel.getName());
            Utils.finishEntryAnimation(context, intent);
        }
    }
}
