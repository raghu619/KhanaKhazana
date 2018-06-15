package com.example.navoki.khanakhazana.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.navoki.khanakhazana.models.RecipesModel;

import java.util.List;

/**
 * Created by Shivam Srivastava on 6/8/2018.
 */
@Dao
public interface RecipeDao {
    @Query("SELECT * FROM recipes")
    LiveData<List<RecipesModel>> getAll();

    @Insert
    void insertAll(List<RecipesModel> movieDataList);

    @Query("DELETE FROM recipes")
    void deleteAll();
}
