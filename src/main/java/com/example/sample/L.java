package com.example.sample;

import android.util.Log;

public class L {

    private static boolean debug;
    private static final String TAG = "www.baidu.com";

    public static void e(String msg) {
        if (debug) {
            Log.e(TAG, msg);
        }
    }
}
