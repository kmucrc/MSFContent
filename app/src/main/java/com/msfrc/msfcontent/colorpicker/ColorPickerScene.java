package com.msfrc.msfcontent.colorpicker;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.msfrc.msfcontent.R;
import com.msfrc.msfcontent.connection.ConnectionScene;

/**
 * Created by kmuvcl_laptop_dell on 2016-07-26.
 */
public class ColorPickerScene extends AppCompatActivity{
    /** Called when the activity is first created. */
    private Context context;
    private int mPickedColor = Color.WHITE;
    TextView mTextView;
    AppCompatActivity mActivity;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivity=this;
        GridView mGridView = (GridView)ColorList.getColorPicker(getApplicationContext());
        setContentView(mGridView);
        //new ColorPicker(mActivity, ColorPickerScene.this, Color.WHITE).show();
        setCustomerActionBar();
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the pickedColor from AdapterView
                mPickedColor = (int) parent.getItemAtPosition(position);
                ConnectionScene.mBluetoothService.write(Integer.toHexString(mPickedColor).getBytes());
            }
        });
    }
    private void setCustomerActionBar(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.rgb(51, 51, 51)));
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        View mCustomView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.actionbar, null);
        actionBar.setCustomView(mCustomView);
        mTextView = (TextView)findViewById(R.id.title);
        mTextView.setText("Color Picker");
        mTextView.setTextColor(Color.WHITE);
    }
}
