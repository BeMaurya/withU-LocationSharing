package com.bewithmaurya.withu.activities;

import android.app.Application;

import com.google.android.gms.ads.MobileAds;
import com.bewithmaurya.withu.R;

// just create for adding some advisement in the future, you can ignore it
public class MyApp extends Application
{
    @Override
    public void onCreate() {
        super.onCreate();
        MobileAds.initialize(this,getResources().getString(R.string.ADS_APP_ID));
    }
}
