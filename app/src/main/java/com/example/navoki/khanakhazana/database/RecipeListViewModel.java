package com.example.navoki.khanakhazana.database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.navoki.khanakhazana.models.RecipesModel;

import java.util.List;

/**
 * Created by Shivam Srivastava on 6/10/2018.
 */
public class RecipeListViewModel extends AndroidViewModel {
    private LiveData<List<RecipesModel>> listLiveData;

    public RecipeListViewModel(Application application) {
        super(application);
        listLiveData = AppDatabase.getInstance(application).recipeDao().getAll();
    }

    public LiveData<List<RecipesModel>> getListLiveData() {
        return listLiveData;
    }

}
