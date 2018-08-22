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
import android.widget.Toast;

import com.msfrc.msfcontent.R;
import com.msfrc.msfcontent.base.CommonDialog;
import com.msfrc.msfcontent.base.CommonUtil;
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
        int playChecked = settings.getInt("musicPlay", Constants.musicPlay);
        int forwardChecked = settings.getInt("musicForward", Constants.musicForward);
        int reverseChecked = settings.getInt("musicReverse", Constants.musicReverse);
        firstRow.add(new FirstRow(R.drawable.click, "Click1", "Click2", "Hold"));

        musicListData.add(new MusicSceneData(R.drawable.playpause, "PLAY\nPAUSE",playChecked));
        musicListData.add(new MusicSceneData(R.drawable.forward, "FORWARD", forwardChecked));
        musicListData.add(new MusicSceneData(R.drawable.reverse, "REVERSE", reverseChecked));

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

        CommonUtil commonUtil = new CommonUtil();
        if (commonUtil.getDuplicatesCheck(Constants.musicPlay, Constants.musicForward, Constants.musicReverse)) {
            final CommonDialog alertDialog = new CommonDialog(this, Constants.COMMONDIALOG_TWOBUTTON);
            alertDialog.setTitle("설정값 저장");
            alertDialog.setMessage("설정값을 저장하시겠습니까?");
            alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
            alertDialog.setPositiveButton("확인", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putInt("musicPlay", Constants.musicPlay);
                    editor.putInt("musicForward", Constants.musicForward);
                    editor.putInt("musicReverse", Constants.musicReverse);
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

        } else {
            final CommonDialog alertDialog = new CommonDialog(this, Constants.COMMONDIALOG_ONEBUTTON);
            alertDialog.setTitle("설정값 중복");
            alertDialog.setMessage("설정값이 중복되었습니다.\n확인 후 다시 저장 해 주세요.");
            alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
            alertDialog.setPositiveButton("확인", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });
            alertDialog.show();
        }
        return false;
    }
}
