package com.msfrc.msfcontent.click.emergency;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.msfrc.msfcontent.R;
import com.msfrc.msfcontent.base.Constants;
import com.msfrc.msfcontent.base.FirstRow;
import com.msfrc.msfcontent.base.FirstRowAdapter;

import java.util.ArrayList;

/**
 * Created by kmuvcl_laptop_dell on 2016-07-25.
 */
public class EmergencyScene extends AppCompatActivity implements MenuItem.OnMenuItemClickListener {

    private ArrayList<FirstRow> firstRow = new ArrayList<FirstRow>();
    private TextView titleText;
    private SharedPreferences settings;
    private ArrayList<EmergencySceneData> emergencyListData = new ArrayList<EmergencySceneData>();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_main);
        ListView emergencyView;
        ListView firstRowView;
        settings = getPreferences(MODE_PRIVATE);
        boolean firstSingleChecked = settings.getBoolean("FirstSingleCheck", true);
        boolean firstDoubleChecked = settings.getBoolean("FirstDoubleCheck", false);
        boolean firstHoldCheck = settings.getBoolean("FirstHoldCheck", false);
        firstRow.add(new FirstRow(R.drawable.click, "Click1", "Click2", "Hold"));
        if(Constants.isEmergencySave){
            emergencyListData.add(new EmergencySceneData(R.drawable.location2, "SEND YOUR LOCATION", firstSingleChecked, firstDoubleChecked, firstHoldCheck));
        }
        else {
            emergencyListData.add(new EmergencySceneData(R.drawable.location2, "SEND YOUR LOCATION", true, false, false));
        }
        firstRowView = (ListView)findViewById(R.id.first);
        emergencyView = (ListView)findViewById(R.id.musicList);
        FirstRowAdapter firstAdapter = new FirstRowAdapter(getLayoutInflater(), firstRow);
        EmergencySceneAdapter emergencyAdapter = new EmergencySceneAdapter(getLayoutInflater(), emergencyListData);
        firstRowView.setAdapter(firstAdapter);
        emergencyView.setAdapter(emergencyAdapter);
        setCustomerActionBar();
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
        titleText.setText("EMERGENCY");
        titleText.setTextColor(Color.WHITE);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.home:
                Constants.emergencyPage = false;
                NavUtils.navigateUpFromSameTask(this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.click_option_menu, menu);
        MenuItem saveItem = menu.findItem(R.id.EDIT);
        saveItem.setOnMenuItemClickListener(this);
        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("FirstSingleCheck", EmergencySceneAdapter.isFirstLineSingleChecked);
        editor.putBoolean("FirstDoubleCheck", EmergencySceneAdapter.isFirstLineDoubleChecked);
        editor.putBoolean("FirstHoldCheck", EmergencySceneAdapter.isFirstLineHoldChecked);
        editor.commit();
        Constants.isEmergencySave = true;
        return false;
    }
}
