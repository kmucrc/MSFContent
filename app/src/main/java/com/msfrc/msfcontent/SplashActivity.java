package com.msfrc.msfcontent;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.felipecsl.gifimageview.library.GifImageView;
import com.msfrc.msfcontent.base.Constants;
import com.msfrc.msfcontent.connection.ConnectionScene;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by iny00 on 2018-02-09.
 */

public class SplashActivity extends AppCompatActivity {

    private GifImageView gifImageView;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getSupportActionBar().hide();

        preferences = getSharedPreferences("setupdData", MODE_PRIVATE);
        loadPref(preferences);

        gifImageView =(GifImageView) findViewById(R.id.loadingGif);

        try{
            InputStream inputStream = getAssets().open("loading.gif");
            byte[] bytes = IOUtils.toByteArray(inputStream);
            gifImageView.setBytes(bytes);
            gifImageView.startAnimation();
//            Thread.sleep(4000);
        }catch (IOException ex) {}
//        catch (InterruptedException e){
//            e.printStackTrace();
//        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SplashActivity.this.startActivity(new Intent(SplashActivity.this, ConnectionScene.class));
                SplashActivity.this.finish();
            }
        }, 3000);

//        startActivity(new Intent(this, MainActivity.class));
//        finish();
    }

    private void loadPref(SharedPreferences pref) {
        Constants.clickIndex = pref.getInt("clickIndex", Constants.CLICK_MUSIC_PLAY);
        Log.e("eleutheria", "clickIndex : "  + Constants.clickIndex);

    }
}
