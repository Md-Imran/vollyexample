package com.example.imran.vollyexample.app;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.imran.vollyexample.utils.Glider;

/**
 * Created by Imran on 4/26/2018.
 */

public class AppController extends Application {

    private RequestQueue mRequestQueue;
    private static AppController appcontrolinstance;
    public static String TAG = AppController.class.getCanonicalName();

    @Override
    public void onCreate() {
        super.onCreate();
        appcontrolinstance = this;
        Glider.init(getApplicationContext());
    }

    public static synchronized AppController getInstance() {
        return appcontrolinstance;
    }

    public RequestQueue getmRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> request, String tag) {
        request.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getmRequestQueue().add(request);
    }
    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getmRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}
