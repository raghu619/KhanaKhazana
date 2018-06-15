package com.example.navoki.khanakhazana.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.example.navoki.khanakhazana.database.IngredientsConvertor;
import com.example.navoki.khanakhazana.database.VideoStepsConvertor;

import java.util.List;

@Entity(tableName = "recipes")
public class RecipesModel {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int uid;
    private String id;
    private String name;

    @ColumnInfo(name = "ingredients")
    @TypeConverters(IngredientsConvertor.class)
    private List<IngredientsModel> ingredients;

    @ColumnInfo(name = "steps")
    @TypeConverters(VideoStepsConvertor.class)
    private List<VideoStepModel> steps;

    @NonNull
    public int getUid() {
        return uid;
    }

    public void setUid(@NonNull int uid) {
        this.uid = uid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<IngredientsModel> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientsModel> ingredients) {
        this.ingredients = ingredients;
    }

    public List<VideoStepModel> getSteps() {
        return steps;
    }

    public void setSteps(List<VideoStepModel> steps) {
        this.steps = steps;
    }
}
