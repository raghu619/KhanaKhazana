package com.example.navoki.khanakhazana.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.navoki.khanakhazana.R;
import com.example.navoki.khanakhazana.interfaces.OnAdapterClickListener;
import com.example.navoki.khanakhazana.models.RecipesModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class RecipeListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<RecipesModel> recipesModelArrayList;
    private final Context context;
    private final OnAdapterClickListener listener;

    class RecipeHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.videosCount)
        TextView videosCount;
        @BindView(R.id.container)
        LinearLayout container;
        @BindView(R.id.img_food)
        ImageView img_food;

        RecipeHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.container)
        public void onTap() {
            listener.onItemClick(getAdapterPosition(), img_food);
        }
    }

    public RecipeListAdapter(Context context, List<RecipesModel> recipesModelArrayList) {
        this.recipesModelArrayList = recipesModelArrayList;
        this.context = context;
        this.listener = (OnAdapterClickListener) context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_foods, null);
        return new RecipeHolder(view);
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        RecipeHolder recipeHolder = (RecipeHolder) holder;
        RecipesModel recipesModel = recipesModelArrayList.get(position);
        recipeHolder.name.setText(recipesModel.getName());
        recipeHolder.videosCount.setText(new StringBuilder().append(recipesModel.getSteps().size())
                .append(context.getString(R.string.space))
                .append(context.getString(R.string.videos)).toString());
    }

    @Override
    public int getItemCount() {
        return recipesModelArrayList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

}
