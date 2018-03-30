package com.msfrc.msfcontent.click.mannermode;

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
import com.msfrc.msfcontent.base.FirstRowAdapter;
import com.msfrc.msfcontent.base.FirstRow;

import java.util.ArrayList;

/**
 * Created by kmuvcl_laptop_dell on 2016-07-25.
 */
public class MannerModeScene extends AppCompatActivity implements MenuItem.OnMenuItemClickListener {
    private ArrayList<FirstRow> firstRow = new ArrayList<FirstRow>();
    private TextView titleText;
    private ArrayList<MannerModeSceneData> mannerModeListData = new ArrayList<MannerModeSceneData>();
    private SharedPreferences settings;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_main);
        ListView emergencyView;
        ListView firstRowView;
        settings = getPreferences(MODE_PRIVATE);
        boolean firstSingleChecked = settings.getBoolean("FirstSingleCheck", true);
        boolean firstDoubleChecked = settings.getBoolean("FirstDoubleCheck", false);
        boolean firstHoldCheck = settings.getBoolean("FirstHoldCheck", false);
        boolean secondSingleChecked = settings.getBoolean("SecondSingleCheck", false);
        boolean secondDoubleChecked = settings.getBoolean("SecondDoubleCheck", true);
        boolean secondHoldChecked = settings.getBoolean("SecondHoldCheck", false);
        boolean thirdSingleChecked = settings.getBoolean("ThirdSingleCheck", false);
        boolean thirdDoubleChecked = settings.getBoolean("ThirdDoubleCheck", false);
        boolean thirdHoldChecked = settings.getBoolean("ThirdHoldCheck", true);
        firstRow.add(new FirstRow(R.drawable.click, "Click1", "Click2", "Hold"));
        if(Constants.isMannermodeSave){
            mannerModeListData.add(new MannerModeSceneData(R.drawable.mannermode, "SILENT MODE", firstSingleChecked, firstDoubleChecked, firstHoldCheck));
            mannerModeListData.add(new MannerModeSceneData(R.drawable.soundmode, "SOUND MODE", secondSingleChecked, secondDoubleChecked, secondHoldChecked));
            mannerModeListData.add(new MannerModeSceneData(R.drawable.rejectcall, "REJECT CALL", thirdSingleChecked, thirdDoubleChecked, thirdHoldChecked));
        }
        else {
            mannerModeListData.add(new MannerModeSceneData(R.drawable.mannermode, "SILENT MODE", true, false, false));
            mannerModeListData.add(new MannerModeSceneData(R.drawable.soundmode, "SOUND MODE", false, true, false));
            mannerModeListData.add(new MannerModeSceneData(R.drawable.rejectcall, "REJECT CALL", false, false, true));
        }
        firstRowView = (ListView)findViewById(R.id.first);
        emergencyView = (ListView)findViewById(R.id.musicList);
        FirstRowAdapter firstAdapter = new FirstRowAdapter(getLayoutInflater(), firstRow);
        MannerModeSceneAdapter emergencyAdapter = new MannerModeSceneAdapter(getLayoutInflater(), mannerModeListData);
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
        titleText.setText("MANNER MODE");
        titleText.setTextColor(Color.WHITE);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.home:
                Constants.mannermodePage = false;
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
        editor.putBoolean("FirstSingleCheck", MannerModeSceneAdapter.isFirstLineSingleChecked);
        editor.putBoolean("FirstDoubleCheck", MannerModeSceneAdapter.isFirstLineDoubleChecked);
        editor.putBoolean("FirstHoldCheck", MannerModeSceneAdapter.isFirstLineHoldChecked);
        editor.putBoolean("SecondSingleCheck", MannerModeSceneAdapter.isSecondLineSingleChecked);
        editor.putBoolean("SecondDoubleCheck", MannerModeSceneAdapter.isSecondLineDoubleChecked);
        editor.putBoolean("SecondHoldCheck", MannerModeSceneAdapter.isSecondLineHoldChecked);
        editor.putBoolean("ThirdSingleCheck", MannerModeSceneAdapter.isThirdLineSingleChecked);
        editor.putBoolean("ThirdDoubleCheck", MannerModeSceneAdapter.isThirdLineDoubleChecked);
        editor.putBoolean("ThirdHoldCheck", MannerModeSceneAdapter.isThirdLineHoldChecked);
        editor.commit();
        Constants.isMannermodeSave = true;
        return false;
    }
}
