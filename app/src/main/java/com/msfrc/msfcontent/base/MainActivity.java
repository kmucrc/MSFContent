package com.msfrc.msfcontent.base;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.msfrc.msfcontent.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
