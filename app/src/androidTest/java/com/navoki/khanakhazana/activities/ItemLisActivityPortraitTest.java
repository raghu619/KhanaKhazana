package com.navoki.khanakhazana.activities;


import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.navoki.khanakhazana.R;
import com.navoki.khanakhazana.models.IngredientsModel;
import com.navoki.khanakhazana.models.RecipesModel;
import com.navoki.khanakhazana.models.VideoStepModel;
import com.navoki.khanakhazana.utils.Globle;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ItemLisActivityPortraitTest {

    @Rule
    public ActivityTestRule<ItemListActivity> mActivityTestRule = new ActivityTestRule<>(ItemListActivity.class);


    // Opening VideoPage with some dummy data
    @Before
    public void intentWithStubbedNoteId() {

        Globle globle = Globle.getAppInstance();
        globle.setRecipesModel(prepareData());
        Intent startIntent = new Intent();
        mActivityTestRule.launchActivity(startIntent);

    }


    @Test
    public void splashActivityTest() {

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.item_list)).check(matches(isDisplayed()));
    }

    private RecipesModel prepareData() {
        RecipesModel model = new RecipesModel();
        model.setName("Maggie");

        IngredientsModel ingredientsModel = new IngredientsModel();
        ingredientsModel.setMeasure("1");
        ingredientsModel.setQuantity("Cup");
        ingredientsModel.setIngredient("Boil Water");
        ArrayList<IngredientsModel> ingredientsModelArrayList = new ArrayList<>();
        ingredientsModelArrayList.add(ingredientsModel);

        VideoStepModel videoStepModel = new VideoStepModel();
        videoStepModel.setThumbnailURL("http://www.jinlongmart.com/wp-content/uploads/2016/12/maggie-300x300.png");
        videoStepModel.setVideoURL("https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd974_-intro-creampie/-intro-creampie.mp4");
        videoStepModel.setShortDescription("Boild 1 cup of water");
        ArrayList<VideoStepModel> videoStepModelArrayList = new ArrayList<>();
        videoStepModelArrayList.add(videoStepModel);

        model.setIngredients(ingredientsModelArrayList);
        model.setSteps(videoStepModelArrayList);

        return model;
    }

}
