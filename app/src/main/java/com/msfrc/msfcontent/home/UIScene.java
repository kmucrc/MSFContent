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

import com.msfrc.msfcontent.R;
import com.msfrc.msfcontent.colorpicker.ColorPickerActivity;
import com.msfrc.msfcontent.connection.ConnectionScene;
import com.msfrc.msfcontent.contacts.ContactScene;
import com.msfrc.msfcontent.notification.NotificationScene;
import com.msfrc.msfcontent.preference.PreferenceScene;

import java.util.ArrayList;

//import com.msfrc.msfcontent.click.ClickScene;
//import com.msfrc.msfcontent.contacts.ContactScene;
//import com.msfrc.msfcontent.notification.NotificationScene;

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
        datas.add(new UiSceneData(R.drawable.contacts, "CONTACTS"));
        datas.add(new UiSceneData(R.drawable.click, "CLICK"));
        datas.add(new UiSceneData(R.drawable.colorpicker, "COLOR PICKERS"));
        datas.add(new UiSceneData(R.drawable.preference, "PREFERENCES"));
        datas.add(new UiSceneData(R.drawable.help, "HELP & REVIEW"));
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                Intent connectionSceneIntent = new Intent(getApplicationContext(), ConnectionScene.class);
//                startActivity(connectionSceneIntent);
                setResult(Activity.RESULT_OK, connectionSceneIntent);
                finish();
                break;
            case 1:
                Intent notificationSceneIntent = new Intent(getApplicationContext(), NotificationScene.class);
                startActivity(notificationSceneIntent);
                break;
            case 2:
                Intent contactSceneIntent = new Intent(getApplicationContext(), ContactScene.class);
                startActivity(contactSceneIntent);
                break;
//            case 3:
//                Intent clickSceneIntent = new Intent(getApplicationContext(), ClickScene.class);
//                startActivity(clickSceneIntent);
//                break;
            case 4:
                Intent colorPickerSceneIntent = new Intent(getApplicationContext(), ColorPickerActivity.class);
                startActivity(colorPickerSceneIntent);
                break;
            case 5:
                Intent preferenceSceneIntent = new Intent(getApplicationContext(), PreferenceScene.class);
                startActivity(preferenceSceneIntent);
                break;
//            case 6:
//                Intent helpSceneIntent = new Intent(getApplicationContext(), Help.class);
//                startActivity(helpSceneIntent);
//                break;
        }
    }
}