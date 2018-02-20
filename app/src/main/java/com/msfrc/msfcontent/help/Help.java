package com.msfrc.msfcontent.help;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.msfrc.msfcontent.R;

/**
 * Created by kmuvcl_laptop_dell on 2016-07-20.
 */
public class Help extends AppCompatActivity{

    private TextView mTextView;
    private TextView titleText;
    private TextView mBodyText;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);
        setCustomerActionBar();
        mBodyText = (TextView)findViewById(R.id.firstParagraph);
        mBodyText.setText("Swipe across a notificaion to change the color of that notificaion\n\n\n" +
                "Tap the dote on the right to select one of 4 vibration patterns\n\n\n" + "Hit 'Edit' in the top right to turn different notificaions on and off\n\n\n" +
        "Make sure you remember what color and vibration corresponds to which notification.\n\n\n" + "Make sure notifications are turned ON in your phone's settings for apps whose notificaion you want sent to your ring.Their alert setting needs to be on Banners of Alerts as well\n\n\n"
        + "Make sure you remember what color and vibration corresponds to which notificaion\n\n\n" + "Make sure notifications are turned ON in your phone's settings for apps whose notifications you want sent to your ring. Their alert setting needs to be on Banners or Alerts as well");

    }
    private void setCustomerActionBar(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.rgb(51, 51, 51)));
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        View mCustomView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.actionbar, null);
        actionBar.setCustomView(mCustomView);
        titleText = (TextView)findViewById(R.id.title);
        titleText.setText("HELP & REVIEW");
        titleText.setTextColor(Color.WHITE);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
