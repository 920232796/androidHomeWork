package com.example.sample;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

public class MainPro extends AppCompatActivity {

    private FragmentManager manager;

    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        manager = getFragmentManager();
        fragmentTransaction = manager.beginTransaction();
//        fragmentTransaction.add(R.id.mainPage, new MainActivitySample());
    }
}
