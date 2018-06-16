package com.example.navoki.khanakhazana.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.navoki.khanakhazana.R;
import com.example.navoki.khanakhazana.models.IngredientsModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class IngredientsAdpater
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    private final List<IngredientsModel> ingredientsList;

    public IngredientsAdpater(@NonNull Context context, List<IngredientsModel> ingredientsList) {
        this.ingredientsList = ingredientsList;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ingredient, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(ingredientsList.get(position).getIngredient());
        stringBuilder.append(context.getString(R.string.colon));
        stringBuilder.append(ingredientsList.get(position).getQuantity());
        stringBuilder.append(context.getString(R.string.space));
        stringBuilder.append(ingredientsList.get(position).getMeasure());
        ((ViewHolder) holder).name.setText(stringBuilder.toString());
    }

    @Override
    public int getItemCount() {
        return ingredientsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name)
        TextView name;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}