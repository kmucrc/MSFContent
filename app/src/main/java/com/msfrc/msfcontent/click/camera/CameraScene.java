package com.msfrc.msfcontent.click.camera;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.msfrc.msfcontent.R;
import com.msfrc.msfcontent.base.CommonDialog;
import com.msfrc.msfcontent.base.Constants;
import com.msfrc.msfcontent.base.FirstRow;
import com.msfrc.msfcontent.base.FirstRowAdapter;
import com.msfrc.msfcontent.base.MainActivity;
import com.msfrc.msfcontent.click.music.MusicSceneAdapter;
import com.msfrc.msfcontent.click.music.MusicSceneData;
import com.msfrc.msfcontent.connection.ConnectionScene;

import java.util.ArrayList;

/**
 * Created by kmuvcl_laptop_dell on 2016-07-21.
 */
public class CameraScene  extends AppCompatActivity implements MenuItem.OnMenuItemClickListener {

    private ArrayList<CameraSceneData> cameraListData = new ArrayList<CameraSceneData>();
    private ArrayList<FirstRow> firstRow = new ArrayList<FirstRow>();
    private TextView titleText;
    private SharedPreferences settings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_main);
        ListView cameraView;
        ListView firstRowView;
        firstRow.add(new FirstRow(R.drawable.click, "Click1", "Click2", "Hold"));
        settings = getPreferences(MODE_PRIVATE);
        int cameraChecked = settings.getInt("cameraCamera", Constants.cameraCamera);

//        if(Constants.isCameraSave){
            cameraListData.add(new CameraSceneData(R.drawable.playpause, "PHOTO", cameraChecked));
//            cameraListData.add(new CameraSceneData(R.drawable.forward, "VIDEO", secondSingleChecked, secondDoubleChecked, secondHoldChecked));
//            cameraListData.add(new CameraSceneData(R.drawable.reverse, "SELFIE", thirdSingleChecked, thirdDoubleChecked, thirdHoldChecked));
//        }
//        else {
//            cameraListData.add(new CameraSceneData(R.drawable.takephoto, "PHOTO", true, false, false));
//            cameraListData.add(new CameraSceneData(R.drawable.takevideo, "VIDEO", false, true, false));
//            cameraListData.add(new CameraSceneData(R.drawable.selfie, "SELFIE", false, false, true));
//        }
        cameraView = (ListView)findViewById(R.id.cameraList);
        firstRowView = (ListView)findViewById(R.id.first);
        CameraSceneAdapter mAdapter = new CameraSceneAdapter(getLayoutInflater(), cameraListData);
        FirstRowAdapter firstAdapter = new FirstRowAdapter(getLayoutInflater(), firstRow);
        firstRowView.setAdapter(firstAdapter);
        cameraView.setAdapter(mAdapter);
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
        titleText.setText("CAMERA");
        titleText.setTextColor(Color.WHITE);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.home:
                Constants.clickCameraPage = false;
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
        final CommonDialog alertDialog = new CommonDialog(this, Constants.COMMONDIALOG_TWOBUTTON);
        alertDialog.setTitle("설정값 저장");
        alertDialog.setMessage("설정값을 저장하시겠습니까?");
        alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
        alertDialog.setPositiveButton("확인", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                SharedPreferences.Editor editor = settings.edit();
                editor.putInt("cameraCamera", Constants.cameraCamera);
                editor.commit();
                Toast.makeText(getApplicationContext(),"설정값이 저장되었습니다.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        alertDialog.setNegativeButton("취소", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

            }
        });
        alertDialog.show();
        return false;
    }
}
