package com.navoki.khanakhazana.adapters;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.navoki.khanakhazana.R;
import com.navoki.khanakhazana.interfaces.OnAdapterClickListener;
import com.navoki.khanakhazana.models.IngredientsModel;
import com.navoki.khanakhazana.models.VideoStepModel;
import com.navoki.khanakhazana.utils.BitmapCacheAsyncTask;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsAdapter
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    private final List<IngredientsModel> ingredientsList;
    private final List<VideoStepModel> videoStepList;
    private final int HEADER = 0;
    private final int ITEM = 1;
    private final OnAdapterClickListener listener;

    public StepsAdapter(Context context, List<IngredientsModel> ingredientsList, List<VideoStepModel> videoStepList) {
        this.context = context;
        this.ingredientsList = ingredientsList;
        this.videoStepList = videoStepList;
        listener = (OnAdapterClickListener) context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HEADER) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_foods_ingredients, parent, false);
            return new IngredientsHolder(view);
        } else if (viewType == ITEM) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_foods_steps, parent, false);
            return new StepsHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        // show first video by default
        if (position == 0 && videoStepList.size()>0)
            listener.onItemClick(position, null);

        if (holder instanceof IngredientsHolder) {
            final IngredientsHolder ingredientsHolder = (IngredientsHolder) holder;
            IngredientsAdpater adapter = new IngredientsAdpater(context, ingredientsList);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            ingredientsHolder.recyclerView.setLayoutManager(linearLayoutManager);
            ingredientsHolder.recyclerView.setAdapter(adapter);

            Typeface typeface = Typeface.createFromAsset(context.getAssets(), context.getString(R.string.fontPath));
            ingredientsHolder.txt_ingredients.setTypeface(typeface);

            StringBuilder builder = new StringBuilder();
            builder.append(String.valueOf(ingredientsList.size()));
            builder.append(context.getString(R.string.space));
            builder.append(context.getString(R.string.ingredients));

            ingredientsHolder.txt_ingredients.setTypeface(typeface);
            ingredientsHolder.txt_ingredients.setText(builder.toString());

            ingredientsHolder.txt_toggle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ingredientsHolder.recyclerView.getVisibility() == View.GONE) {
                        ingredientsHolder.txt_toggle.setText(context.getString(R.string.collapse));
                        ingredientsHolder.recyclerView.animate().alpha(1f).start();
                        expand(ingredientsHolder.recyclerView);
                    } else {
                        ingredientsHolder.txt_toggle.setText(context.getString(R.string.expand));
                        ingredientsHolder.recyclerView.animate().alpha(0f).start();
                        collapse(ingredientsHolder.recyclerView);
                    }
                }
            });
        } else if (holder instanceof StepsHolder) {
            final int pos = position - 1;
            final StepsHolder stepsHolder = (StepsHolder) holder;
            StringBuilder builder = new StringBuilder();
            builder.append(context.getString(R.string.step));
            builder.append(context.getString(R.string.space));
            builder.append(String.valueOf(Integer.parseInt(videoStepList.get(pos).getId()) + 1));
            stepsHolder.txtStepNo.setText(builder.toString());
            stepsHolder.txtTitle.setText(videoStepList.get(pos).getShortDescription());


            stepsHolder.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(pos, null);
                }
            });

            Picasso.get().load(videoStepList.get(pos).getThumbnailURL()).placeholder(R.drawable.placeholder_video)
                    .error(R.drawable.placeholder_video).into(stepsHolder.img_step_thumb, new Callback() {
                @Override
                public void onSuccess() {
                }

                @Override
                public void onError(Exception e) {
                    //Thumbnail download of videos and cache it
                    BitmapCacheAsyncTask.retriveVideoFrameFromVideo(videoStepList.get(pos).getVideoURL(), stepsHolder.img_step_thumb);
                }
            });
        }
        Animation animation = AnimationUtils.loadAnimation(context,
                R.anim.anim_from_bottom);
        holder.itemView.startAnimation(animation);
    }

    @Override
    public int getItemCount() {
        return videoStepList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return HEADER;
        return ITEM;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class IngredientsHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_ingredients)
        TextView txt_ingredients;
        @BindView(R.id.rycle)
        RecyclerView recyclerView;
        @BindView(R.id.container)
        LinearLayout container;
        @BindView(R.id.txt_toggle)
        TextView txt_toggle;
        @BindView(R.id.cardView)
        CardView cardView;

        IngredientsHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    class StepsHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_step_no)
        TextView txtStepNo;
        @BindView(R.id.txt_title)
        TextView txtTitle;
        @BindView(R.id.container)
        LinearLayout container;
        @BindView(R.id.cardView)
        CardView cardView;
        @BindView(R.id.img_step_thumb)
        ImageView img_step_thumb;

        StepsHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    private void expand(View view) {
        //set Visible
        view.setVisibility(View.VISIBLE);

        final int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        final int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(widthSpec, heightSpec);

        ValueAnimator mAnimator = slideAnimator(view, 0, view.getMeasuredHeight());
        mAnimator.start();
    }

    private void collapse(final View view) {
        final int finalHeight = view.getHeight();

        ValueAnimator mAnimator = slideAnimator(view, finalHeight, 0);
        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                //Height=0, but it set visibility to GONE
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        mAnimator.start();
    }

    private ValueAnimator slideAnimator(final View view, int start, int end) {

        ValueAnimator animator = ValueAnimator.ofInt(start, end);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                //Update Height
                int value = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.height = value;
                view.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }
}