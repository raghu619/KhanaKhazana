package com.example.navoki.khanakhazana.database;

import android.arch.persistence.room.TypeConverter;

import com.example.navoki.khanakhazana.models.VideoStepModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Shivam Srivastava on 6/8/2018.
 */
public class VideoStepsConvertor {

    @TypeConverter
    public static String encode(List<VideoStepModel> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }

    @TypeConverter
    public static List<VideoStepModel> decode(String value) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<VideoStepModel>>() {
        }.getType();
        return gson.fromJson(value, listType);
    }
}
