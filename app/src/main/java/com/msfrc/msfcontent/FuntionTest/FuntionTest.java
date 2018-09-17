package com.msfrc.msfcontent.FuntionTest;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.msfrc.msfcontent.R;
import com.msfrc.msfcontent.connection.ConnectionScene;

public class FuntionTest extends AppCompatActivity implements View.OnClickListener {;
    private TextView titleText;
    private EditText etInputValue;
    private Button btWhite;
    private Button btRed;
    private Button btGreen;
    private Button btYellow;
    private Button btBlue;
    private Button btSend;
    private String strColorValue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_functiontest);
        setCustomerActionBar();
        strColorValue = "";

        btWhite = (Button)findViewById(R.id.bt_white);
        btWhite.setOnClickListener(this);
        btRed  = (Button)findViewById(R.id.bt_red);
        btRed.setOnClickListener(this);
        btGreen = (Button)findViewById(R.id.bt_green);
        btGreen.setOnClickListener(this);
        btYellow = (Button)findViewById(R.id.bt_yellow);
        btYellow.setOnClickListener(this);
        btBlue = (Button)findViewById(R.id.bt_blue);
        btBlue.setOnClickListener(this);

        etInputValue = (EditText) findViewById(R.id.et_inputcolor);
        btSend = (Button)findViewById(R.id.bt_send);
        btSend.setOnClickListener(this);

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
        titleText.setText("FUNTION TEST");
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_white:
                Log.e("eleutheria", "onClick White");
                ConnectionScene.mBluetoothLeService.writeColorCharacteristic("1106020d");
                break;
            case R.id.bt_red:
                Log.e("eleutheria", "onClick Red");
                ConnectionScene.mBluetoothLeService.writeColorCharacteristic("1123E119");
                break;
            case R.id.bt_green:
                Log.e("eleutheria", "onClick Green");
                ConnectionScene.mBluetoothLeService.writeColorCharacteristic("11B9194B");
                break;
            case R.id.bt_yellow:
                Log.e("eleutheria", "onClick Yellow");
                ConnectionScene.mBluetoothLeService.writeColorCharacteristic("11020BDB");
                break;
            case R.id.bt_blue:
                Log.e("eleutheria", "onClick Blue");
                ConnectionScene.mBluetoothLeService.writeColorCharacteristic("11F66100");
                break;
            case R.id.bt_send:
                strColorValue = "11" + etInputValue.getText().toString();
                Log.e("eleutheria", "onClick Send : " + strColorValue);
                ConnectionScene.mBluetoothLeService.writeColorCharacteristic(strColorValue);
        }
    }
}
