package com.example.navoki.khanakhazana.models;

import android.text.TextUtils;

import com.example.navoki.khanakhazana.utils.AppConstants;

public class VideoStepModel {

    private String id, shortDescription, description, videoURL, thumbnailURL;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoURL() {
        if (TextUtils.isEmpty(videoURL))
            return AppConstants.NOT_AVAILABLE;
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getThumbnailURL() {
        if (TextUtils.isEmpty(thumbnailURL))
            return AppConstants.NOT_AVAILABLE;
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }
}
