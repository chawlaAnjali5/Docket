package com.primelite.docket;

import android.content.Context;

/**
 * Created by Anjali Chawla on 13/6/17.
 */

public class BaseRequestAdapter {
    static Context context;

    public static void init(Context context) {
        BaseRequestAdapter.context = context;
        initMyVolleyClient();
    }

    /**
     * cbvdgdgdgdgd
     * fhfhfhfmnhk,hj
     * @link BaseRequestAdapter()
     * @return mainclie()
     */
    private static MainVolleyClient initMyVolleyClient() {

        return new MainVolleyClient();

    }

}
