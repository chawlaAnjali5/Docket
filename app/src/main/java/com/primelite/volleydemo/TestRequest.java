package com.primelite.volleydemo;

import com.primelite.docket.MainVolleyClient;
import com.primelite.docket.Request;
import com.primelite.docket.VollyCallback;

/**
 * Created by Anjali Chawla on 13/6/17.
 */

class TestRequest extends MainVolleyClient {


    private final static String REQUEST_URL = "http://85.13.213.94/agent_api/api_dashboard.php";
    private static final String KEY = "eb@2016_agent*62#a%";
    private RequestTypes requestType;
    private String mobileNumber;


    void execute(VollyCallback vollyCallback) {
        Request testRequest = new Request(vollyCallback)
                .setRequestUrl(REQUEST_URL)
                .setRequestMethod(RequestMethods.POST)
                .setRequestType(getRequestType())
                .setPerams("key", KEY)
                .setPerams("mobile_number", getMobileNumber())
                .setTag("testRequest")
                .setConnectionTimeOut(3000);
        super.execute(testRequest);
    }

    void setRequestType(RequestTypes requestType) {
        this.requestType = requestType;
    }

    private RequestTypes getRequestType() {
        return requestType;
    }

    void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    private String getMobileNumber() {
        return mobileNumber;
    }
}
