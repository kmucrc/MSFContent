package com.msfrc.msfcontent;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.felipecsl.gifimageview.library.GifImageView;
import com.msfrc.msfcontent.connection.ConnectionScene;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by iny00 on 2018-02-09.
 */

public class SplashActivity extends AppCompatActivity {

    private GifImageView gifImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getSupportActionBar().hide();

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
}
