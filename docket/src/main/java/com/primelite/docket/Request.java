package com.primelite.docket;

import android.graphics.Bitmap;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
/**
 * Created by Anjali Chawla on 13/6/17.
 */

/**
 * A request for retrieving the response body at a given Request Parameters.
 */

public class Request implements ResponseHandler {
    private String requestUrl;
    private MainVolleyClient.RequestTypes requestType;
    private int requestMethod;
    private ArrayList<Param> paramList = new ArrayList<>();
    private VollyCallback mVollyCallback;
    private String tag;
    private int connectionTimeOut = 0;
    private int retryCount = 0;

    /**
     * @param vollyCallback the request callback {@link VollyCallback} to deliver response
     */
    public Request(VollyCallback vollyCallback) {
        if (vollyCallback != null) {
            this.mVollyCallback = vollyCallback;
        }
    }

    /**
     * @param requestUrl URL to fetch the string at
     * @return self instance
     */
    public Request setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
        return this;
    }

    /**
     * Returns the URL of this request.
     */
    String getRequestUrl() {
        return requestUrl;
    }

    /**
     * @param requestType Type Of Request {@link MainVolleyClient.RequestTypes} to hit
     * @return self instance
     */
    public Request setRequestType(MainVolleyClient.RequestTypes requestType) {
        this.requestType = requestType;
        return this;
    }

    /**
     * Returns the requestType of this request.Can be one of the values in {@link MainVolleyClient.RequestTypes}.
     */
    MainVolleyClient.RequestTypes getRequestType() {
        return requestType;
    }

    /**
     * @param requestMethod the request {@link MainVolleyClient.RequestMethods} to use
     * @return self instance
     */
    public Request setRequestMethod(MainVolleyClient.RequestMethods requestMethod) {
        this.requestMethod = requestMethod.defaultValue;
        return this;
    }

    /**
     * Returns the requestMethod of this request.Can be one of the values in {@link MainVolleyClient.RequestMethods}.
     */
    int getRequestMethod() {
        return requestMethod;
    }

    /**
     * @param tag the request tag to set
     * @return self instance
     */
    public Request setTag(String tag) {
        this.tag = tag;
        return this;
    }

    /**
     * @return Returns the tag of the Request
     */
    String getTag() {
        return tag;
    }

    /**
     * @param connectionTimeOut set timeout for the retry policy.
     * @return self instance
     */
    public Request setConnectionTimeOut(int connectionTimeOut) {
        this.connectionTimeOut = connectionTimeOut;
        return this;
    }

    /**
     * @return Returns the timeout of request.
     */
    int getConnectionTimeOut() {
        return connectionTimeOut;
    }

    /**
     * @param retryCount The maximum number of retries for retry policy.
     */
    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    /**
     * Returns the retry count of request.
     */
    int getRetryCount() {
        return retryCount;
    }

    /**
     * @param key    key name to be set for Post request params
     * @param values value to be assign for the params key
     * @return self instance
     */
    public Request setPerams(String key, Object values) {
        Param param = new Param();
        param.setKey(key);
        param.setValue(values);
        paramList.add(param);
        return this;
    }

    /**
     * @return Returns List of all the params associated with the request
     */
    ArrayList<Param> getParams() {
        return paramList;
    }

    /**
     * @param state state {@link MainVolleyClient.RequestState} to be set for the request
     */
    public void setState(MainVolleyClient.RequestState state) {
        this.setState(state);
    }

    /**
     * @param obj Reponse from the request as Result
     *            and parse them to JsonObject
     */
    @Override
    public void onGetResponse(Object obj) {
        System.out.println("anjali" + obj);
        try {
            if (obj.getClass().equals(String.class)) {
                System.out.println(" Response :  String Type");
                JSONObject jsonObject = new JSONObject((String) obj);
                mVollyCallback.onSuccess(jsonObject);
            }
            if (obj.getClass().equals(JSONObject.class)) {
                System.out.println(" Response :  JSONObject Type");
                JSONObject jsonObject = new JSONObject((String) obj);
                mVollyCallback.onSuccess(jsonObject);
            }
            if (obj.getClass().equals(JSONArray.class)) {
                System.out.println(" Response :  JSONArray Type");
            }
            if (obj.getClass().equals(Bitmap.class)) {
                System.out.println(" Response :  Image Type");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

/**
 * A class for setting keys and values for the request params
 */
class Param {
    private String key;
    private Object value;

    String getKey() {
        return key;
    }

    void setKey(String key) {
        this.key = key;
    }

    Object getValue() {
        return value;
    }

    void setValue(Object value) {
        this.value = value;
    }
}


