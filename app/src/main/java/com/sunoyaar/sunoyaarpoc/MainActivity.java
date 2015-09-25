package com.sunoyaar.sunoyaarpoc;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
/*
    http://192.168.1.110:9000/
*/

    private static final String STOMP_URL = "ws://10.1.14.94:9000/publish/10/dfhtrhd/websocket";
    private static final String TAG = "MainActivity";

    Stomp mStomp;
    Subscription mSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HashMap<String, String> headers = new HashMap<>();

        mStomp = new Stomp(STOMP_URL, headers, new ListenerWSNetwork() {
            @Override
            public void onState(int state) {
                Log.d(TAG, "state: " + state);
            }
        });
        mSubscription = new Subscription("/channel/sanjeev", new ListenerSubscription() {
            @Override
            public void onMessage(Map<String, String> headers, String body) {
                Log.e(TAG, "onMessage: " + headers + " body: " + body);
            }
        });

        findViewById(R.id.button_connect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new StompAsyncTask().execute();
            }
        });

        findViewById(R.id.button_disconnect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new StompDisconnectAsyncTask().execute();
            }
        });
    }

    private class StompAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            mStomp.connect();
//            mStomp.subscribe(mSubscription);
            return null;
        }
    }

    private class StompDisconnectAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            mStomp.disconnect();
//            mStomp.subscribe(mSubscription);
            return null;
        }
    }
}
