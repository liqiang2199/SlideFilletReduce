package com.emptycup.slidefilletreduce;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView);
        LiearSildeReduceView liner_slide = (LiearSildeReduceView) findViewById(R.id.liner_slide);
        try {
            liner_slide.Bind_ScrollVeiw(scrollView);
        } catch (BindViewException e) {
            e.printStackTrace();
        }
    }
}
