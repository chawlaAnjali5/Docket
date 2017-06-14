package com.primelite.docket;

import org.json.JSONObject;

/**
 * Created by Anjali Chawla on 13/6/17.
 */

/**
 * Callback interface for delivering parsed responses.
 */

public interface VollyCallback {

    void onSuccess(JSONObject jsonObject);

    void onFailure(Throwable throwable);

    void onException(Exception e);

}
