package com.primelite.volleydemo;

import android.app.Application;

import com.primelite.docket.BaseRequestAdapter;

/**
 * Created by Anjali Chawla on 13/6/17.
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        BaseRequestAdapter.init(this);
    }
}
