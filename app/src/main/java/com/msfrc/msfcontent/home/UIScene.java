package com.msfrc.msfcontent.home;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.msfrc.msfcontent.FuntionTest.FuntionTest;
import com.msfrc.msfcontent.R;
import com.msfrc.msfcontent.base.Constants;
import com.msfrc.msfcontent.click.ClickScene;
import com.msfrc.msfcontent.colorpicker.ColorPickerActivity;
import com.msfrc.msfcontent.connection.ConnectionScene;
import com.msfrc.msfcontent.contacts.ContactScene;
import com.msfrc.msfcontent.help.Help;
import com.msfrc.msfcontent.notification.NotificationScene;
import com.msfrc.msfcontent.preference.PreferenceScene;

import java.util.ArrayList;

/**
 * Created by kmuvcl_laptop_dell on 2016-07-19.
 */
public class UIScene extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ArrayList<UiSceneData> datas = new ArrayList<UiSceneData>();

    private static final String TAG = "UIScene";
    private TextView selected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_main);
        getSupportActionBar().hide();
        addToList();
        UiSceneAdapter mAdapter = new UiSceneAdapter(getLayoutInflater(), datas);
        ListView list = (ListView) findViewById(R.id.list);
        list.setAdapter(mAdapter);
        list.setOnItemClickListener(this);
        setCustomerActionBar();
    }

    private void setCustomerActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.rgb(51, 51, 51)));
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);

        View mCustomView = LayoutInflater.from(this).inflate(R.layout.actionbar, null);
        actionBar.setCustomView(mCustomView);
        actionBar.setTitle("");
    }

    public void addToList() {
        datas.add(new UiSceneData(R.drawable.connection, "CONNECTION"));
        datas.add(new UiSceneData(R.drawable.notification, "NOTIFICATIONS"));
//        datas.add(new UiSceneData(R.drawable.contacts, "CONTACTS"));
        datas.add(new UiSceneData(R.drawable.click, "CLICK"));
        datas.add(new UiSceneData(R.drawable.colorpicker, "COLOR PICKERS"));
        datas.add(new UiSceneData(R.drawable.preference, "PREFERENCES"));
        datas.add(new UiSceneData(R.drawable.help, "HELP & REVIEW"));
//        datas.add(new UiSceneData(R.drawable.help, "FUNTIONTEST"));
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case Constants.MAIN_MENU_CONNECTION:
                Intent connectionSceneIntent = new Intent(getApplicationContext(), ConnectionScene.class);
//                startActivity(connectionSceneIntent);
                setResult(Activity.RESULT_OK, connectionSceneIntent);
                finish();
                break;
            case Constants.MAIN_MENU_NOTIFICATIONS:
                Intent notificationSceneIntent = new Intent(getApplicationContext(), NotificationScene.class);
                startActivity(notificationSceneIntent);
                break;
//            case Constants.MAIN_MENU_CONTACTS:
//                Intent contactSceneIntent = new Intent(getApplicationContext(), ContactScene.class);
//                startActivity(contactSceneIntent);
//                break;
            case Constants.MAIN_MENU_CLICK:
                Intent clickSceneIntent = new Intent(getApplicationContext(), ClickScene.class);
                startActivity(clickSceneIntent);
                break;
            case Constants.MAIN_MENU_COLOR_PICKERS:
                Intent colorPickerSceneIntent = new Intent(getApplicationContext(), ColorPickerActivity.class);
                startActivity(colorPickerSceneIntent);
                break;
            case Constants.MAIN_MENU_PREFERENCES:
                Intent preferenceSceneIntent = new Intent(getApplicationContext(), PreferenceScene.class);
                startActivity(preferenceSceneIntent);
                break;
            case Constants.MAIN_MENU_HELP_REVIEW:
                Intent helpSceneIntent = new Intent(getApplicationContext(), Help.class);
                startActivity(helpSceneIntent);
                break;
            case Constants.MAIN_MENU_TEST:
                Intent testSceneIntent = new Intent(getApplicationContext(), FuntionTest.class);
                startActivity(testSceneIntent);
                break;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if(ConnectionScene.mBluetoothLeService!=null)
//            ConnectionScene.mBluetoothLeService.disconnect();
    }
}