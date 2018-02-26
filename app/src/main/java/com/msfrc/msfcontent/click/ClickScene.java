package com.msfrc.msfcontent.click;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.msfrc.msfcontent.R;

import java.util.ArrayList;

//import com.msfrc.msfcontent.click.camera.CameraScene;
//import com.msfrc.msfcontent.click.emergency.EmergencyScene;
//import com.msfrc.msfcontent.click.findphone.FindPhoneScene;
//import com.msfrc.msfcontent.click.mannermode.MannerModeScene;
//import com.msfrc.msfcontent.click.music.MusicScene;

/**
 * Created by kmuvcl_laptop_dell on 2016-07-20.
 */
public class ClickScene extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ArrayList<ClickListData> datas = new ArrayList<ClickListData>();
    ListView listView;
    private TextView titleText;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);

        datas.add(new ClickListData(R.drawable.musicplay, "MUSIC PLAY"));
        datas.add(new ClickListData(R.drawable.camera, "CAMERA"));
        datas.add(new ClickListData(R.drawable.emergency, "EMERGENCY"));
        datas.add(new ClickListData(R.drawable.mannermode, "MANNER MODE"));
        datas.add(new ClickListData(R.drawable.findphone, "FIND PHONE"));

        listView = (ListView)findViewById(R.id.list);

        ClickAdapter mAdapter = new ClickAdapter(getLayoutInflater(), datas);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(this);
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
        titleText.setText("CLICK");
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch(position){
//            case 0:
//                Intent musicSceneIntent = new Intent(getApplicationContext(), MusicScene.class);
//                startActivity(musicSceneIntent);
//                break;
//            case 1:
//                Intent cameraSceneIntent = new Intent(getApplicationContext(), CameraScene.class);
//                startActivity(cameraSceneIntent);
//                break;
//            case 2:
//                Intent emergencySceneIntent = new Intent(getApplicationContext(), EmergencyScene.class);
//                startActivity(emergencySceneIntent);
//                break;
//            case 3:
//                Intent mannerModeIntent = new Intent(getApplicationContext(), MannerModeScene.class);
//                startActivity(mannerModeIntent);
//                break;
//            case 4:
//                Intent findPhoneIntent = new Intent(getApplicationContext(), FindPhoneScene.class);
//                startActivity(findPhoneIntent);
//                break;
        }
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
