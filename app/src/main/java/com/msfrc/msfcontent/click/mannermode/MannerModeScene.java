package com.msfrc.msfcontent.click.mannermode;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.SyncStateContract;
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
import android.widget.Toast;

import com.msfrc.msfcontent.R;
import com.msfrc.msfcontent.base.CommonDialog;
import com.msfrc.msfcontent.base.CommonUtil;
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
        int silentChecked = settings.getInt("mannermodeSilent", Constants.mannermodeSilent);
        int soundChecked = settings.getInt("mannermodeSound", Constants.mannermodeSound);
        int rejectCallChecked = settings.getInt("mannermodeRejectCall", Constants.mannermodeRejectCall);
        firstRow.add(new FirstRow(R.drawable.click, "Click1", "Click2", "Hold"));

        mannerModeListData.add(new MannerModeSceneData(R.drawable.mannermode, "SILENT MODE", silentChecked));
        mannerModeListData.add(new MannerModeSceneData(R.drawable.soundmode, "SOUND MODE", soundChecked));
        mannerModeListData.add(new MannerModeSceneData(R.drawable.rejectcall, "REJECT CALL", rejectCallChecked));


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

        CommonUtil commonUtil = new CommonUtil();
        if (commonUtil.getDuplicatesCheck(Constants.mannermodeSilent, Constants.mannermodeSound, Constants.mannermodeRejectCall)) {
            final CommonDialog alertDialog = new CommonDialog(this, Constants.COMMONDIALOG_TWOBUTTON);
            alertDialog.setTitle("설정값 저장");
            alertDialog.setMessage("설정값을 저장하시겠습니까?");
            alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
            alertDialog.setPositiveButton("확인", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putInt("mannermodeSilent", Constants.mannermodeSilent);
                    editor.putInt("mannermodeSound", Constants.mannermodeSound);
                    editor.putInt("mannermodeRejectCall", Constants.mannermodeRejectCall);
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
