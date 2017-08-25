package com.cognitive.face.recognition.api.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by nellipc on 8/19/17.
 */

public class MyActivity extends Activity {
    String ourString = "abc";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) { //most used, main start method
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() { //after onCreate
        super.onStart();
    }

    @Override
    protected void onResume() {//most used, after onStart method or after onPause or onStop
        super.onResume();
    }

    @Override
    protected void onPause() {//most used , when we turn screen off or we minimize the app
        super.onPause();
    }

    @Override
    protected void onStop() {//after onPause
        super.onStop();
    }

    @Override
    protected void onDestroy() {//after onStop, Garbage collector calls this
        super.onDestroy();
    }
}
