package com.msfrc.msfcontent.click.music;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.msfrc.msfcontent.R;
import com.msfrc.msfcontent.base.Constants;
import com.msfrc.msfcontent.base.FirstRow;
import com.msfrc.msfcontent.base.FirstRowAdapter;

import java.util.ArrayList;

/**
 * Created by kmuvcl_laptop_dell on 2016-07-21.
 */
public class MusicScene extends AppCompatActivity implements MenuItem.OnMenuItemClickListener{
    private ArrayList<MusicSceneData> musicListData = new ArrayList<MusicSceneData>();
    private ArrayList<FirstRow> firstRow = new ArrayList<FirstRow>();
    private static final String TAG = "Music Scene";
    private TextView titleText;
    private SharedPreferences settings;
    private MusicSceneAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_main);
        ListView musicView;
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
        if(Constants.isMusicSave){
            musicListData.add(new MusicSceneData(R.drawable.playpause, "PLAY\nPAUSE", firstSingleChecked, firstDoubleChecked, firstHoldCheck));
            musicListData.add(new MusicSceneData(R.drawable.forward, "FORWARD", secondSingleChecked, secondDoubleChecked, secondHoldChecked));
            musicListData.add(new MusicSceneData(R.drawable.reverse, "REVERSE", thirdSingleChecked, thirdDoubleChecked, thirdHoldChecked));
        }
        else {
            musicListData.add(new MusicSceneData(R.drawable.playpause, "PLAY\nPAUSE", true, false, false));
            musicListData.add(new MusicSceneData(R.drawable.forward, "FORWARD", false, true, false));
            musicListData.add(new MusicSceneData(R.drawable.reverse, "REVERSE", false, false, true));
        }
        firstRowView = (ListView)findViewById(R.id.first);
        musicView = (ListView)findViewById(R.id.musicList);
        FirstRowAdapter firstAdapter = new FirstRowAdapter(getLayoutInflater(), firstRow);
        firstRowView.setAdapter(firstAdapter);
        mAdapter = new MusicSceneAdapter(getLayoutInflater(), musicListData);
        musicView.setAdapter(mAdapter);
        setCustomerActionBar();
    }


    /*class SingleButtonListener implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        }
    }*/

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch(position){
           /* case 0:
                ConnectionScene.mBluetoothService.write(("MusicPlayer").getBytes());
                break;
            case 1:
                ConnectionScene.mBluetoothService.write("PlayNext".getBytes());
                break;
            case 2:
                ConnectionScene.mBluetoothService.write("PlayPrev".getBytes());
                break;*/
        }
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
        titleText.setText("MUSIC PLAY");
        titleText.setTextColor(Color.WHITE);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.home:
                Constants.musicPage = false;
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
        Log.d(TAG, "FirstLineSingle "+MusicSceneAdapter.isFirstLineSingleChecked);
        Log.d(TAG, "FirstLineDouble "+MusicSceneAdapter.isFirstLineDoubleChecked);
        Log.d(TAG, "FirstLineHold "+MusicSceneAdapter.isFirstLineHoldChecked);
        Log.d(TAG, "SecondLineSingle "+MusicSceneAdapter.isSecondLineSingleChecked);
        Log.d(TAG, "SecondLineSingle "+MusicSceneAdapter.isSecondLineDoubleChecked);
        Log.d(TAG, "SecondLineSingle "+MusicSceneAdapter.isSecondLineHoldChecked);
        Log.d(TAG, "ThirdLineSingle "+MusicSceneAdapter.isThirdLineSingleChecked);
        Log.d(TAG, "ThirdLineSingle "+MusicSceneAdapter.isThirdLineDoubleChecked);
        Log.d(TAG, "ThirdLineSingle "+MusicSceneAdapter.isThirdLineHoldChecked);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("FirstSingleCheck", MusicSceneAdapter.isFirstLineSingleChecked);
        editor.putBoolean("FirstDoubleCheck", MusicSceneAdapter.isFirstLineDoubleChecked);
        editor.putBoolean("FirstHoldCheck", MusicSceneAdapter.isFirstLineHoldChecked);
        editor.putBoolean("SecondSingleCheck", MusicSceneAdapter.isSecondLineSingleChecked);
        editor.putBoolean("SecondDoubleCheck", MusicSceneAdapter.isSecondLineDoubleChecked);
        editor.putBoolean("SecondHoldCheck", MusicSceneAdapter.isSecondLineHoldChecked);
        editor.putBoolean("ThirdSingleCheck", MusicSceneAdapter.isThirdLineSingleChecked);
        editor.putBoolean("ThirdDoubleCheck", MusicSceneAdapter.isThirdLineDoubleChecked);
        editor.putBoolean("ThirdHoldCheck", MusicSceneAdapter.isThirdLineHoldChecked);
        editor.commit();
        Constants.isMusicSave = true;
        return false;
    }
}
