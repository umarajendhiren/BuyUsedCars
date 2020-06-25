package com.androidapps.buyusedcars.utilities;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleySingleton {
    //we can use Retrofit or volley to parse json.
    //No need to run volley in separate background thread.Volley run in bg by default.
//we are creating this single ton class because we need to create one object
// to this class for the entire application
    private static VolleySingleton mInstance;
    private RequestQueue mRequestQueue;


    //private constructor does not allow us to create new object from any other activity.(new instance)
    //we need to call getInstance() to get an instance of this class.
//    getApplicationContext(),this class not for particular activity ,it is for entire application

    private VolleySingleton(Context context) {
        mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }


    //this is the way we have to create one object at a time
    //if mInstance is null ,it will create new one else return already existing instance.
//    Synchronized key word,at a time ,it allows only one thread can  access this method
    //if two thread access this method,it will create two object to avoid that we have to use synchronize method.
//     Synchronized keyword,block this method untill the current running thread finished.after finished the current thread
//    it will allow next thread
    public static synchronized VolleySingleton getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new VolleySingleton(context);
        }

        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }
}

