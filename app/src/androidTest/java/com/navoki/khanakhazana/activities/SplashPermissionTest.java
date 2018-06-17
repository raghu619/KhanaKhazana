package com.navoki.khanakhazana.activities;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.ActivityCompat;

import com.navoki.khanakhazana.utils.AppConstants;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;

@RunWith(AndroidJUnit4.class)
public class SplashPermissionTest {

    @Rule
    public ActivityTestRule<SplashActivity> mActivityTestRule = new ActivityTestRule<>(SplashActivity.class);


    @Before
    public void allowPermissionsIfNeeded() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void sdcardTest() {

        Assert.assertEquals("Media is not mounted!", Environment.getExternalStorageState(), Environment.MEDIA_MOUNTED);
        //get and check Permissions
        int grantResult = ActivityCompat.checkSelfPermission(InstrumentationRegistry.getTargetContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        Assert.assertEquals(PackageManager.PERMISSION_GRANTED, grantResult);
        //finally try to create folder
        File f = new File(Environment.getExternalStorageDirectory() + File.separator + AppConstants.APP_FOLDER);
        if (!f.exists()) {
            boolean mkdir = f.mkdirs();
        }
        boolean delete = f.delete();
    }
}
