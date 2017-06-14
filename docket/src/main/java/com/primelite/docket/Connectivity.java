package com.primelite.docket;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by Anjali Chawla on 9/6/17.
 */

public class Connectivity {
    static String TAG = Connectivity.class.getCanonicalName();

    public static boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) BaseRequestAdapter.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null) {
            if (networkInfo.isConnected()) {
                Log.d(TAG, "active network isConnected: ");
                return true;
            } else {
                Log.d(TAG, "active network is not Connected: ");
                return false;
            }
        }
        Log.d(TAG, "active network is null ");
        return false;

    }
}
