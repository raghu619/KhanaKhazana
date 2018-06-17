package com.navoki.khanakhazana.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.navoki.khanakhazana.models.RecipesModel;

/**
 * Created by Shivam Srivastava on 6/8/2018.
 */
@Database(entities = {RecipesModel.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "recipesdb";
    private static AppDatabase instance;

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (LOCK) {
                instance = Room.databaseBuilder(context.getApplicationContext()
                        , AppDatabase.class, AppDatabase.DATABASE_NAME)
                        .allowMainThreadQueries().build();
            }
        }
        return instance;
    }

    public abstract RecipeDao recipeDao();
}
