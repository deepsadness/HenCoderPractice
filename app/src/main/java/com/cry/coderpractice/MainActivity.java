package com.cry.coderpractice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public ZangView2 mZangView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mZangView2 = (ZangView2) findViewById(R.id.z2);
    }

//    public void setZ2Number(View view) {
//        count++;
//        ZangView2 zangView2 = (ZangView2) view;
////        zangView2.setNumbers(count + "");
//        zangView2.subNumbers(count);
//    }

    public void addNumber(View view) {
        mZangView2.AddAndgetChangeNumberPart();
    }

    public void subNumber(View view) {
        mZangView2.subAndgetChangeNumberPart();
    }
}
