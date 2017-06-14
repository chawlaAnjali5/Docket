package com.primelite.volleydemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.primelite.docket.MainVolleyClient;
import com.primelite.docket.VollyCallback;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements VollyCallback {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.response);
        TestRequest testRequest = new TestRequest();
        testRequest.setRequestType(MainVolleyClient.RequestTypes.STRING_REQUEST);
        testRequest.setMobileNumber("919654554783");
        testRequest.execute(this);


    }


    @Override
    public void onSuccess(JSONObject jsonObject) {
        System.out.println(jsonObject);
        textView.setText(jsonObject.toString());
    }

    @Override
    public void onFailure(Throwable throwable) {

    }

    @Override
    public void onException(Exception e) {

    }


}
