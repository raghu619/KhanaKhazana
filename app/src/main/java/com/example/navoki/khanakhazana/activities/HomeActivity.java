package com.example.navoki.khanakhazana.activities;

import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.navoki.khanakhazana.BuildConfig;
import com.example.navoki.khanakhazana.R;
import com.example.navoki.khanakhazana.adapters.RecipeListAdapter;
import com.example.navoki.khanakhazana.database.AppDatabase;
import com.example.navoki.khanakhazana.database.RecipeListViewModel;
import com.example.navoki.khanakhazana.interfaces.OnAdapterClickListener;
import com.example.navoki.khanakhazana.models.RecipesModel;
import com.example.navoki.khanakhazana.utils.AppConstants;
import com.example.navoki.khanakhazana.utils.Globle;
import com.example.navoki.khanakhazana.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements OnAdapterClickListener {

    @BindView(R.id.message)
    TextView message;
    @BindView(R.id.recipeRecycle)
    RecyclerView recipeRecycle;

    private ProgressDialog progressDialog;
    private Context context;
    private Globle global;
    private AppDatabase appDatabase;
    private List<RecipesModel> recipesList;
    private boolean isFromSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        context = this;
        global = Globle.getAppInstance();
        appDatabase = AppDatabase.getInstance(context);

        Typeface typeface = Typeface.createFromAsset(getAssets(), getString(R.string.fontPath));
        message.setTypeface(typeface);

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setCancelable(false);

        if (getIntent() != null) {
            isFromSplash = getIntent().getBooleanExtra(AppConstants.ARG_FROM_SPLASH, false);
        }

        int spanCount = 2;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            spanCount = 3;
        }
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, spanCount);
        recipeRecycle.setLayoutManager(gridLayoutManager);

        setUpViewModel();
        getRecipies();
    }

    /*
     * get list of Recipies
     * */
    private void getRecipies() {
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, BuildConfig.BASE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray dataArray = new JSONArray(response);
                    if (dataArray.length() > 0) {
                        appDatabase.recipeDao().deleteAll();
                        Gson gson = new Gson();
                        Type listType = new TypeToken<ArrayList<RecipesModel>>() {
                        }.getType();
                        recipesList = gson.fromJson(dataArray.toString(), listType);
                        appDatabase.recipeDao().insertAll(recipesList);
                        populateList();

                    } else
                        Toast.makeText(context, getString(R.string.noDataFound), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                } catch (JSONException e) {

                    progressDialog.dismiss();
                    Toast.makeText(context, getString(R.string.somethingGoneWrong), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                Utils.showError(error, context);
            }
        });
        global.addToRequestQueue(stringRequest);
    }


    private void setUpViewModel() {
        RecipeListViewModel model = ViewModelProviders.of((FragmentActivity) context).get(RecipeListViewModel.class);
        model.getListLiveData().observe(HomeActivity.this, observer);
    }

    final Observer<List<RecipesModel>> observer = new Observer<List<RecipesModel>>() {
        @Override
        public void onChanged(@Nullable List<RecipesModel> list) {
            if (list != null) {
                recipesList = new ArrayList<>();
                recipesList.addAll(list);
                populateList();
            }
        }
    };

    private void populateList() {
        if (recipesList.size() != 0) {
            RecipeListAdapter adapter = new RecipeListAdapter(context, recipesList);
            recipeRecycle.setAdapter(adapter);
        }
    }

    @Override
    public void onItemClick(int pos, ImageView imageView) {
        if (isFromSplash) {
            global.setRecipesModel(recipesList.get(pos));
            Intent intent = new Intent(context, ItemListActivity.class);
            Utils.finishEntryAnimation(context, intent);
        } else {
            global.saveRecipeName(context, recipesList.get(pos).getName());
            global.saveIngredients(context, recipesList.get(pos).getIngredients());
            ((AppCompatActivity) context).finish();
            ((AppCompatActivity) context).overridePendingTransition(R.anim.anim_slide_in_from_left, R.anim.anim_slide_out_to_right);
        }
    }
}
