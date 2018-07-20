package com.msfrc.msfcontent.click.camera;

import android.app.Activity;
import android.os.Bundle;

import com.msfrc.msfcontent.R;

/**
 * Created by kmuvcl_laptop_dell on 2016-08-17.
 */
public class SelfieCamera extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        if (null == savedInstanceState) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, SelfieCamera2.newInstance())
                    .commit();
        }
    }
}
