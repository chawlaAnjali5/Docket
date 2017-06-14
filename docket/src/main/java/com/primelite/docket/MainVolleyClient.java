package com.primelite.docket;

import android.graphics.Bitmap;
import android.util.ArrayMap;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Anjali Chawla on 9/6/17.
 */

public class MainVolleyClient {

    protected enum RequestMethods {
        GET(0), POST(1), PUT(2), DELETE(3), PATCH(4);
        int defaultValue = 0;

        RequestMethods(int p) {
            defaultValue = p;
        }
    }

    protected enum RequestState {
        IDEAL, REQUESTED, SUCCESS, ERROR;
    }

    public enum RequestTypes {
        STRING_REQUEST("stringRequest"),
        JSON_ARRAY_REQUEST("jsonArrayRequest"),
        JSON_OBJECT_REQUEST("jsonObjectRequest"),
        IMAGE_REQUEST("imageRequest");

        private String text = "";

        private RequestTypes(final String text) {
            this.text = text;
        }
    }

    private static final String TAG = MainVolleyClient.class.getSimpleName();

    public static final String HEADER_CONTENT_TYPE = "Content-Type";
    public static final String HEADER_TEXT_JSON = "text/json";
    public static final String HEADER_URL_ENCODED = "application/x-www-form-urlencoded";


    public static final int DEFAULT_TIMEOUT_MS = 2500;
    public static int DEFAULT_MAX_RETRIES = 1;
    public static final float DEFAULT_BACKOFF_MULT = 1f;


    private RequestQueue queue;

    private HashMap<String, Request> requestList = new HashMap<>();

    public MainVolleyClient() {
        queue = Volley.newRequestQueue(BaseRequestAdapter.context);
    }


    public MainVolleyClient getMainVolleyClientObject() {
        return this;
    }

    protected void execute(Request request) {
        if (request != null && Connectivity.isConnected()) {
            request.setState(RequestState.IDEAL);
            requestList.put(request.getTag(), request);
            this.executeRequest(request.getTag());
        }
    }

    private void executeRequest(String tag) {
        final Request request = requestList.get(tag);
        switch (request.getRequestType()) {
            case STRING_REQUEST:
                StringRequest stringRequest = new StringRequest(
                        request.getRequestMethod(), request.getRequestUrl(), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        request.onGetResponse(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        if (error.networkResponse == null) {
                            if (error instanceof NetworkError) {
                                Log.d(TAG, "onNetworkError: ");
                            } else if (error instanceof ServerError) {

                            } else if (error instanceof AuthFailureError) {

                            } else if (error instanceof ParseError) {

                            } else if (error instanceof TimeoutError) {

                                Log.d(TAG, "TimeoutError: " + error.getMessage());

                            }
                        }

                    }
                }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> headers = new HashMap<>();
                        headers.put(HEADER_CONTENT_TYPE, HEADER_URL_ENCODED);
                        return headers;
                    }

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> paramsMap = new ArrayMap<>();
                        for (Param param : request.getParams()) {
                            paramsMap.put(param.getKey(), (String) param.getValue());
                        }
                        return paramsMap;
                    }
                };

                stringRequest.setShouldCache(true);
                setUpRetryPolicy(stringRequest, request);
                stringRequest.setTag(request.getTag());
                queue.add(stringRequest);
                break;
            case JSON_ARRAY_REQUEST:
                break;
            case JSON_OBJECT_REQUEST:
                JsonObjectRequest jsObjRequest = new JsonObjectRequest(request.getRequestMethod(), request.getRequestUrl(), null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> headers = new HashMap<>();
                        headers.put("Content-Type", "application/x-www-form-urlencoded");
                        return headers;
                    }

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> paramsMap = new ArrayMap<>();
                        for (Param param : request.getParams()) {
                            paramsMap.put(param.getKey(), (String) param.getValue());
                        }
                        return paramsMap;
                    }
                };
                jsObjRequest.setShouldCache(true);
                setUpRetryPolicy(jsObjRequest, request);
                jsObjRequest.setTag(request.getTag());
                queue.add(jsObjRequest);
                break;
            case IMAGE_REQUEST:
                ImageRequest imageRequest = new ImageRequest(request.getRequestUrl(), new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {

                    }
                }, 0, 0, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                imageRequest.setShouldCache(true);
                setUpRetryPolicy(imageRequest, request);
                imageRequest.setTag(request.getTag());
                queue.add(imageRequest);
                break;
            default:
                break;
        }
    }

    private int mCurrentTimeoutMs;

    private void setUpRetryPolicy(Object requestType, final Request request) {
        com.android.volley.Request requestTypename = (com.android.volley.Request) requestType;
        requestTypename.setRetryPolicy(new RetryPolicy() {

            /**
             * @return Retruns the current timeout.
             */
            @Override
            public int getCurrentTimeout() {
                mCurrentTimeoutMs = request.getConnectionTimeOut() == 0 ? DEFAULT_TIMEOUT_MS :
                        request.getConnectionTimeOut();
                return mCurrentTimeoutMs;
            }

            /**
             *
             * @return Returns the current retry count.
             */
            @Override
            public int getCurrentRetryCount() {
                return request.getRetryCount() == 0 ? DEFAULT_MAX_RETRIES : request.getRetryCount();
            }

            /**
             * Prepares for the next retry by applying a backoff to the timeout.
             * @param error The error code of the last attempt.
             */
            @Override
            public void retry(VolleyError error) throws VolleyError {
                Log.d(TAG, "getCurrentRetryCount: " + DEFAULT_MAX_RETRIES);
                DEFAULT_MAX_RETRIES++;
                mCurrentTimeoutMs += (getCurrentTimeout() * DEFAULT_BACKOFF_MULT);
                if (!hasAttemptRemaining()) {
                    throw error;
                }

            }

        });
    }

    /**
     * Returns true if this policy has attempts remaining, false otherwise.
     */
    protected boolean hasAttemptRemaining() {
        return DEFAULT_MAX_RETRIES <= 5;
    }


}
