package com.msfrc.msfcontent.notification;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.msfrc.msfcontent.R;
import com.msfrc.msfcontent.base.Constants;
import com.msfrc.msfcontent.connection.ConnectionScene;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by kmuvcl_laptop_dell on 2016-07-19.
 */
public class NotificationScene extends AppCompatActivity{

    private static final String TAG = "NotificationScene";

    private ArrayList<NotificationListData> datas = new ArrayList<NotificationListData>();
    private ArrayList<NotificationEditData> editDatas = new ArrayList<NotificationEditData>();
    private TextView mTextView;
    private boolean editScene = false;
    private boolean doneScene = true;
//    private TelephonyManager mTelephonyManager;
    private SmsStateListenr mSmsStateListner;
    private ReminderListener mReminderListener;
    private AccountManager mAccountManager;
    private String email;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);
        baseScene();
        mSmsStateListner = new SmsStateListenr();
        mReminderListener = new ReminderListener();
        email = getUsername();
        Log.d(TAG, ""+email);
        IntentFilter reminderFilter = new IntentFilter();
        reminderFilter.addAction(ReminderListener.reminderAction);
        registerReceiver(mReminderListener, reminderFilter);
        IntentFilter smsIntentFilter = new IntentFilter();
        smsIntentFilter.addAction(SmsStateListenr.action);
        registerReceiver(mSmsStateListner, smsIntentFilter);
        CallStateListener mCallStateListner = new CallStateListener();
//        mTelephonyManager = (TelephonyManager)getSystemService(getApplicationContext().TELEPHONY_SERVICE);
//        mTelephonyManager.listen(mCallStateListner, PhoneStateListener.LISTEN_CALL_STATE);
        setCustomerActionBar();
    }
    public void baseScene(){
        ListView listView = null;
        datas.add(new NotificationListData(R.drawable.phonecall, "PHONE CALL"));
        datas.add(new NotificationListData(R.drawable.textmassage, "TEXT MESSAGE"));
        datas.add(new NotificationListData(R.drawable.reminders, "REMINDERS"));

        listView = (ListView)findViewById(R.id.list);

        NotificationAdapter mAdapter = new NotificationAdapter(getLayoutInflater(), datas);
        listView.setAdapter(mAdapter);
        doneScene= true;
        editScene = false;
    }
    public void setChangeScene(){
        ListView editListView = null;
        if(!Constants.notificationSave) {
            editDatas.add(new NotificationEditData(R.drawable.phonecall, "PHONE CALL", R.drawable.colorpicker, R.drawable.vibrate, Color.WHITE, Color.rgb(75, 173, 172)));
            editDatas.add(new NotificationEditData(R.drawable.textmassage, "TEXT MESSAGE", R.drawable.colorpicker, R.drawable.vibrate, Color.WHITE,  Color.rgb(75, 173, 172)));
            editDatas.add(new NotificationEditData(R.drawable.reminders, "REMINDERS", R.drawable.colorpicker, R.drawable.vibrate, Color.WHITE,Color.rgb(75, 173, 172)));
            editListView = (ListView) findViewById(R.id.list);
        }
        else
        {
            SetLineColor();
            editListView = (ListView) findViewById(R.id.list);
        }
        NotificationEditAdapter mAdapter = new NotificationEditAdapter(getLayoutInflater(), editDatas);
        editListView.setAdapter(mAdapter);
//        setCustomerActionBar();
        doneScene= false;
        editScene = true;
    }

    private void setCustomerActionBar(){
        ActionBar actionBar = getSupportActionBar();
        getSupportFragmentManager().executePendingTransactions();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.rgb(51, 51, 51)));

        LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        View mCustomView = inflater.inflate(R.layout.actionbar, null);
        actionBar.setCustomView(mCustomView);
        mTextView = (TextView)findViewById(R.id.title);
        mTextView.setText("NOTIFICATION");
        mTextView.setTextColor(Color.WHITE);
    }
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.notification_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.EDIT:
                if(doneScene) {
                    setChangeScene();
                    item.setTitle("DONE");
                    datas.clear();
                }
                else if(editScene) {
                    baseScene();
                    item.setTitle("EDIT");
                    Constants.notificationSave = true;
                    editDatas.clear();
                }
                break;
            case R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private class CallStateListener extends PhoneStateListener{
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            switch(state){
                case TelephonyManager.CALL_STATE_RINGING:
                    if(Constants.redColor[0])
                        ConnectionScene.mBluetoothService.write("DC1EE6".getBytes());
                    else if(Constants.blueColor[0])
                        ConnectionScene.mBluetoothService.write("099EFF".getBytes());
                    else if(Constants.greenColor[0])
                        ConnectionScene.mBluetoothService.write("46E6B4".getBytes());
                    else if(Constants.yelloColor[0])
                        ConnectionScene.mBluetoothService.write("FFD966".getBytes());
                    else if(Constants.whiteColor[0])
                        ConnectionScene.mBluetoothService.write("FFFFFF".getBytes());
                    for(int i=0; i<=8; i++) {
                        ConnectionScene.mBluetoothService.write(NotificationEditAdapter.parcelArray[0].getBytes());
                    }
                    break;
            }
        }
    }
    private class SmsStateListenr extends BroadcastReceiver{
        static final String action = "android.provider.Telephony.SMS_RECEIVED";

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(action)){
                if(Constants.redColor[1])
                    ConnectionScene.mBluetoothService.write("DC1EE6".getBytes());
                else if(Constants.blueColor[1])
                    ConnectionScene.mBluetoothService.write("099EFF".getBytes());
                else if(Constants.greenColor[1])
                    ConnectionScene.mBluetoothService.write("46E6B4".getBytes());
                else if(Constants.yelloColor[1])
                    ConnectionScene.mBluetoothService.write("FFD966".getBytes());
                else if(Constants.whiteColor[1])
                    ConnectionScene.mBluetoothService.write("FFFFFF".getBytes());

                for(int i=0; i<=8; i++) {
                    ConnectionScene.mBluetoothService.write(NotificationEditAdapter.parcelArray[1].toString().getBytes());
                }
            }
        }
    }
    public class ReminderListener extends BroadcastReceiver{

        static final String reminderAction = "android.intent.action.EVENT_REMINDER";
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(reminderAction)){
                if(Constants.redColor[2])
                    ConnectionScene.mBluetoothService.write("DC1EE6".getBytes());
                else if(Constants.blueColor[2])
                    ConnectionScene.mBluetoothService.write("099EFF".getBytes());
                else if(Constants.greenColor[2])
                    ConnectionScene.mBluetoothService.write("46E6B4".getBytes());
                else if(Constants.yelloColor[2])
                    ConnectionScene.mBluetoothService.write("FFD966".getBytes());
                else if(Constants.whiteColor[2])
                    ConnectionScene.mBluetoothService.write("FFFFFF".getBytes());

                for(int i=0; i<=8; i++) {
                    ConnectionScene.mBluetoothService.write(NotificationEditAdapter.parcelArray[2].toString().getBytes());
                }
            }
        }
    }
    public String getUsername() {
        AccountManager manager = AccountManager.get(this);
        Account[] accounts = manager.getAccountsByType("com.google");
        List<String> possibleEmails = new LinkedList<String>();

        for (Account account : accounts) {
            // TODO: Check possibleEmail against an email regex or treat
            // account.name as an email address only for certain account.type
            // values.
            possibleEmails.add(account.name);
        }

        if (!possibleEmails.isEmpty() && possibleEmails.get(0) != null) {
            String email = possibleEmails.get(0);
            return email;
            /*String[] parts = email.split("@");
            if (parts.length > 0 && parts[0] != null)
                return parts[0];
            else
                return null;*/
        } else
            return null;
    }

    public void SetLineColor()
    {
        int image;
        int color;
        if(Constants.redColor[0]){
            color = Color.rgb(220, 30, 230);
        }
        else if(Constants.blueColor[0]){
            color = Color.rgb(9, 158, 253);
        }
        else if(Constants.greenColor[0]){
            color = Color.rgb(70, 230, 180);
        }
        else if(Constants.yelloColor[0]){
            color = Color.rgb(255, 217, 102);
        }
        else if(Constants.whiteColor[0]){
            color = Color.rgb(255, 255, 255);
        }
        else{
            color = Color.rgb(75, 173, 172);
        }
        if(Constants.isParecel[0][0]) image = R.drawable.vibeone;
        else if(Constants.isParecel[0][1]) image = R.drawable.vibetwo;
        else if(Constants.isParecel[0][2]) image = R.drawable.vibethree;
        else if(Constants.isParecel[0][3]) image = R.drawable.vibefour;
        else if(Constants.isParecel[0][4]) image = R.drawable.infinity;
        else if(Constants.isParecel[0][5]) image = R.drawable.none;
        else image = R.drawable.vibrate;

        editDatas.add(new NotificationEditData(R.drawable.phonecall, "PHONE CALL", R.drawable.colorpicker, image, Color.WHITE, color));

        if(Constants.redColor[1]){
            color = Color.rgb(220, 30, 230);
        }
        else if(Constants.blueColor[1]){
            color = Color.rgb(9, 158, 253);
        }
        else if(Constants.greenColor[1]){
            color = Color.rgb(70, 230, 180);
        }
        else if(Constants.yelloColor[1]){
            color = Color.rgb(255, 217, 102);
        }
        else if(Constants.whiteColor[1]){
            color = Color.rgb(255, 255, 255);
        }
        else{
            color = Color.rgb(75, 173, 172);
        }
        if(Constants.isParecel[1][0]) image = R.drawable.vibeone;
        else if(Constants.isParecel[1][1]) image = R.drawable.vibetwo;
        else if(Constants.isParecel[1][2]) image = R.drawable.vibethree;
        else if(Constants.isParecel[1][3]) image = R.drawable.vibefour;
        else if(Constants.isParecel[1][4]) image = R.drawable.infinity;
        else if(Constants.isParecel[1][5]) image = R.drawable.none;
        else image = R.drawable.vibrate;

        editDatas.add(new NotificationEditData(R.drawable.textmassage, "TEXT MESSAGE", R.drawable.colorpicker, image, Color.WHITE, color));

        if(Constants.redColor[2]){
            color = Color.rgb(220, 30, 230);
        }
        else if(Constants.blueColor[2]){
            color = Color.rgb(9, 158, 253);
        }
        else if(Constants.greenColor[2]){
            color = Color.rgb(70, 230, 180);
        }
        else if(Constants.yelloColor[2]){
            color = Color.rgb(255, 217, 102);
        }
        else if(Constants.whiteColor[2]){
            color = Color.rgb(255, 255, 255);
        }
        else{
            color = Color.rgb(75, 173, 172);
        }
        if(Constants.isParecel[2][0]) image = R.drawable.vibeone;
        else if(Constants.isParecel[2][1]) image = R.drawable.vibetwo;
        else if(Constants.isParecel[2][2]) image = R.drawable.vibethree;
        else if(Constants.isParecel[2][3]) image = R.drawable.vibefour;
        else if(Constants.isParecel[2][4]) image = R.drawable.infinity;
        else if(Constants.isParecel[2][5]) image = R.drawable.none;
        else image = R.drawable.vibrate;

        editDatas.add(new NotificationEditData(R.drawable.reminders, "REMINDERS", R.drawable.colorpicker, image, Color.WHITE, color));
//        if (Constants.redColor[0]) {
//            if(Constants.isParecel[0][0]) {
//                editDatas.add(new NotificationEditData(R.drawable.phonecall, "PHONE CALL", R.drawable.colorpicker, R.drawable.vibeone, Color.rgb(220, 30, 230), Color.rgb(220, 30, 230)));
//            }
//            else if(Constants.isParecel[0][1]) {
//                editDatas.add(new NotificationEditData(R.drawable.phonecall, "PHONE CALL", R.drawable.colorpicker, R.drawable.vibetwo, Color.rgb(220, 30, 230), Color.rgb(220, 30, 230)));
//            }
//            else if(Constants.isParecel[0][2]) {
//                editDatas.add(new NotificationEditData(R.drawable.phonecall, "PHONE CALL", R.drawable.colorpicker, R.drawable.vibethree, Color.rgb(220, 30, 230), Color.rgb(220, 30, 230)));
//            }
//            else if(Constants.isParecel[0][3]) {
//                editDatas.add(new NotificationEditData(R.drawable.phonecall, "PHONE CALL", R.drawable.colorpicker, R.drawable.vibefour, Color.rgb(220, 30, 230), Color.rgb(220, 30, 230)));
//            }
//            else if(Constants.isParecel[0][4]) {
//                editDatas.add(new NotificationEditData(R.drawable.phonecall, "PHONE CALL", R.drawable.colorpicker, R.drawable.infinity, Color.rgb(220, 30, 230), Color.rgb(220, 30, 230)));
//            }
//            else if(Constants.isParecel[0][5]) {
//                editDatas.add(new NotificationEditData(R.drawable.phonecall, "PHONE CALL", R.drawable.colorpicker, R.drawable.none, Color.rgb(220, 30, 230), Color.rgb(220, 30, 230)));
//            }
//        }
//        else if (Constants.blueColor[0]) {
//            if (Constants.isParecel[0][0]) {
//                editDatas.add(new NotificationEditData(R.drawable.phonecall, "PHONE CALL", R.drawable.colorpicker, R.drawable.vibeone, Color.rgb(9, 158, 253), Color.rgb(9, 158, 253)));
//            }
//            else if (Constants.isParecel[0][1]) {
//                editDatas.add(new NotificationEditData(R.drawable.phonecall, "PHONE CALL", R.drawable.colorpicker, R.drawable.vibetwo, Color.rgb(9, 158, 253), Color.rgb(9, 158, 253)));
//            }
//            else if (Constants.isParecel[0][2]) {
//                editDatas.add(new NotificationEditData(R.drawable.phonecall, "PHONE CALL", R.drawable.colorpicker, R.drawable.vibethree, Color.rgb(9, 158, 253), Color.rgb(9, 158, 253)));
//            }
//            else if (Constants.isParecel[0][3]) {
//                editDatas.add(new NotificationEditData(R.drawable.phonecall, "PHONE CALL", R.drawable.colorpicker, R.drawable.vibefour, Color.rgb(9, 158, 253), Color.rgb(9, 158, 253)));
//            }
//            else if (Constants.isParecel[0][4]) {
//                editDatas.add(new NotificationEditData(R.drawable.phonecall, "PHONE CALL", R.drawable.colorpicker, R.drawable.infinity, Color.rgb(9, 158, 253), Color.rgb(9, 158, 253)));
//            }
//            else if (Constants.isParecel[0][5]) {
//                editDatas.add(new NotificationEditData(R.drawable.phonecall, "PHONE CALL", R.drawable.colorpicker, R.drawable.none, Color.rgb(9, 158, 253), Color.rgb(9, 158, 253)));
//            }
//        }
//        else if (Constants.greenColor[0]) {
//            if (Constants.isParecel[0][0]) {
//                editDatas.add(new NotificationEditData(R.drawable.phonecall, "PHONE CALL", R.drawable.colorpicker, R.drawable.vibeone, Color.rgb(70, 230, 180), Color.rgb(70, 230, 180)));
//            }
//            else if (Constants.isParecel[0][1]) {
//                editDatas.add(new NotificationEditData(R.drawable.phonecall, "PHONE CALL", R.drawable.colorpicker, R.drawable.vibetwo, Color.rgb(70, 230, 180), Color.rgb(70, 230, 180)));
//            }
//            else if (Constants.isParecel[0][2]) {
//                editDatas.add(new NotificationEditData(R.drawable.phonecall, "PHONE CALL", R.drawable.colorpicker, R.drawable.vibethree, Color.rgb(70, 230, 180), Color.rgb(70, 230, 180)));
//            }
//            else if (Constants.isParecel[0][3]) {
//                editDatas.add(new NotificationEditData(R.drawable.phonecall, "PHONE CALL", R.drawable.colorpicker, R.drawable.vibefour, Color.rgb(70, 230, 180), Color.rgb(70, 230, 180)));
//            }
//            else if (Constants.isParecel[0][4]) {
//                editDatas.add(new NotificationEditData(R.drawable.phonecall, "PHONE CALL", R.drawable.colorpicker, R.drawable.infinity, Color.rgb(70, 230, 180), Color.rgb(70, 230, 180)));
//            }
//            else if (Constants.isParecel[0][5]) {
//                editDatas.add(new NotificationEditData(R.drawable.phonecall, "PHONE CALL", R.drawable.colorpicker, R.drawable.none, Color.rgb(70, 230, 180), Color.rgb(70, 230, 180)));
//            }
//        }
//        else if (Constants.yelloColor[0]) {
//            if (Constants.isParecel[0][0]) {
//                editDatas.add(new NotificationEditData(R.drawable.phonecall, "PHONE CALL", R.drawable.colorpicker, R.drawable.vibeone, Color.rgb(255, 217, 102), Color.rgb(255, 217, 102)));
//            }
//            else if (Constants.isParecel[0][1]) {
//                editDatas.add(new NotificationEditData(R.drawable.phonecall, "PHONE CALL", R.drawable.colorpicker, R.drawable.vibetwo, Color.rgb(255, 217, 102), Color.rgb(255, 217, 102)));
//            }
//            else if (Constants.isParecel[0][2]) {
//                editDatas.add(new NotificationEditData(R.drawable.phonecall, "PHONE CALL", R.drawable.colorpicker, R.drawable.vibethree, Color.rgb(255, 217, 102), Color.rgb(255, 217, 102)));
//            }
//            else if (Constants.isParecel[0][3]) {
//                editDatas.add(new NotificationEditData(R.drawable.phonecall, "PHONE CALL", R.drawable.colorpicker, R.drawable.vibefour, Color.rgb(255, 217, 102), Color.rgb(255, 217, 102)));
//            }
//            else if (Constants.isParecel[0][4]) {
//                editDatas.add(new NotificationEditData(R.drawable.phonecall, "PHONE CALL", R.drawable.colorpicker, R.drawable.infinity, Color.rgb(255, 217, 102), Color.rgb(255, 217, 102)));
//            }
//            else if (Constants.isParecel[0][5]) {
//                editDatas.add(new NotificationEditData(R.drawable.phonecall, "PHONE CALL", R.drawable.colorpicker, R.drawable.none, Color.rgb(255, 217, 102), Color.rgb(255, 217, 102)));
//            }
//        }
//        else if (Constants.whiteColor[0]) {
//            if (Constants.isParecel[0][0]) {
//                editDatas.add(new NotificationEditData(R.drawable.phonecall, "PHONE CALL", R.drawable.colorpicker, R.drawable.vibeone, Color.rgb(255, 255, 255), Color.rgb(255, 255, 255)));
//            }
//            else if (Constants.isParecel[0][1]) {
//                editDatas.add(new NotificationEditData(R.drawable.phonecall, "PHONE CALL", R.drawable.colorpicker, R.drawable.vibetwo, Color.rgb(255, 255, 255), Color.rgb(255, 255, 255)));
//            }
//            else if (Constants.isParecel[0][2]) {
//                editDatas.add(new NotificationEditData(R.drawable.phonecall, "PHONE CALL", R.drawable.colorpicker, R.drawable.vibethree, Color.rgb(255, 255, 255), Color.rgb(255, 255, 255)));
//            }
//            else if (Constants.isParecel[0][3]) {
//                editDatas.add(new NotificationEditData(R.drawable.phonecall, "PHONE CALL", R.drawable.colorpicker, R.drawable.vibefour, Color.rgb(255, 255, 255), Color.rgb(255, 255, 255)));
//            }
//            else if (Constants.isParecel[0][4]) {
//                editDatas.add(new NotificationEditData(R.drawable.phonecall, "PHONE CALL", R.drawable.colorpicker, R.drawable.infinity, Color.rgb(255, 255, 255), Color.rgb(255, 255, 255)));
//            }
//            else if (Constants.isParecel[0][5]) {
//                editDatas.add(new NotificationEditData(R.drawable.phonecall, "PHONE CALL", R.drawable.colorpicker, R.drawable.none, Color.rgb(255, 255, 255), Color.rgb(255, 255, 255)));
//            }
//        }
//        else{
//            editDatas.add(new NotificationEditData(R.drawable.phonecall, "PHONE CALL", R.drawable.colorpicker, R.drawable.vibrate, Color.rgb(255, 255, 255), Color.rgb(75, 173, 172)));
//        }
//        if (Constants.redColor[1]) {
//            if(Constants.isParecel[1][0]) {
//                editDatas.add(new NotificationEditData(R.drawable.textmassage, "TEXT MESSAGE", R.drawable.colorpicker, R.drawable.vibeone, Color.WHITE, Color.rgb(220, 30, 230)));
//            }
//            else if(Constants.isParecel[1][1]) {
//                editDatas.add(new NotificationEditData(R.drawable.textmassage, "TEXT MESSAGE", R.drawable.colorpicker, R.drawable.vibetwo, Color.WHITE, Color.rgb(220, 30, 230)));
//            }
//            else if(Constants.isParecel[1][2]) {
//                editDatas.add(new NotificationEditData(R.drawable.textmassage, "TEXT MESSAGE", R.drawable.colorpicker, R.drawable.vibethree, Color.WHITE, Color.rgb(220, 30, 230)));
//            }
//            else if(Constants.isParecel[1][3]) {
//                editDatas.add(new NotificationEditData(R.drawable.textmassage, "TEXT MESSAGE", R.drawable.colorpicker, R.drawable.vibefour, Color.WHITE, Color.rgb(220, 30, 230)));
//            }
//            else if(Constants.isParecel[1][4]) {
//                editDatas.add(new NotificationEditData(R.drawable.textmassage, "TEXT MESSAGE", R.drawable.colorpicker, R.drawable.infinity, Color.WHITE, Color.rgb(220, 30, 230)));
//            }
//            else if(Constants.isParecel[1][5]) {
//                editDatas.add(new NotificationEditData(R.drawable.textmassage, "TEXT MESSAGE", R.drawable.colorpicker, R.drawable.none, Color.WHITE, Color.rgb(220, 30, 230)));
//            }
//        }
//        else if (Constants.blueColor[1])
//        {
//            if (Constants.isParecel[1][0]) {
//                editDatas.add(new NotificationEditData(R.drawable.textmassage, "TEXT MESSAGE", R.drawable.colorpicker, R.drawable.vibeone, Color.rgb(9, 158, 253), Color.rgb(9, 158, 253)));
//            }
//            else if (Constants.isParecel[1][1]) {
//                editDatas.add(new NotificationEditData(R.drawable.textmassage, "TEXT MESSAGE", R.drawable.colorpicker, R.drawable.vibetwo, Color.rgb(9, 158, 253), Color.rgb(9, 158, 253)));
//            }
//            else if (Constants.isParecel[1][2]) {
//                editDatas.add(new NotificationEditData(R.drawable.textmassage, "TEXT MESSAGE", R.drawable.colorpicker, R.drawable.vibethree, Color.rgb(9, 158, 253), Color.rgb(9, 158, 253)));
//            }
//            else if (Constants.isParecel[1][3]) {
//                editDatas.add(new NotificationEditData(R.drawable.textmassage, "TEXT MESSAGE", R.drawable.colorpicker, R.drawable.vibefour, Color.rgb(9, 158, 253), Color.rgb(9, 158, 253)));
//            }
//            else if (Constants.isParecel[1][4]) {
//                editDatas.add(new NotificationEditData(R.drawable.textmassage, "TEXT MESSAGE", R.drawable.colorpicker, R.drawable.infinity, Color.rgb(9, 158, 253), Color.rgb(9, 158, 253)));
//            }
//            else if (Constants.isParecel[1][5]) {
//                editDatas.add(new NotificationEditData(R.drawable.textmassage, "TEXT MESSAGE", R.drawable.colorpicker, R.drawable.none, Color.rgb(9, 158, 253), Color.rgb(9, 158, 253)));
//            }
//        }
//        else if (Constants.greenColor[1])
//        {
//            if (Constants.isParecel[1][0]) {
//                editDatas.add(new NotificationEditData(R.drawable.textmassage, "TEXT MESSAGE", R.drawable.colorpicker, R.drawable.vibeone, Color.rgb(70, 230, 180), Color.rgb(70, 230, 180)));
//            }
//            else if (Constants.isParecel[1][1]) {
//                editDatas.add(new NotificationEditData(R.drawable.textmassage, "TEXT MESSAGE", R.drawable.colorpicker, R.drawable.vibetwo, Color.rgb(70, 230, 180), Color.rgb(70, 230, 180)));
//            }
//            else if (Constants.isParecel[1][2]) {
//                editDatas.add(new NotificationEditData(R.drawable.textmassage, "TEXT MESSAGE", R.drawable.colorpicker, R.drawable.vibethree, Color.rgb(70, 230, 180), Color.rgb(70, 230, 180)));
//            }
//            else if (Constants.isParecel[1][3]) {
//                editDatas.add(new NotificationEditData(R.drawable.textmassage, "TEXT MESSAGE", R.drawable.colorpicker, R.drawable.vibefour, Color.rgb(70, 230, 180), Color.rgb(70, 230, 180)));
//            }
//            else if (Constants.isParecel[1][4]) {
//                editDatas.add(new NotificationEditData(R.drawable.textmassage, "TEXT MESSAGE", R.drawable.colorpicker, R.drawable.infinity, Color.rgb(70, 230, 180), Color.rgb(70, 230, 180)));
//            }
//            else if (Constants.isParecel[1][5]) {
//                editDatas.add(new NotificationEditData(R.drawable.textmassage, "TEXT MESSAGE", R.drawable.colorpicker, R.drawable.none, Color.rgb(70, 230, 180), Color.rgb(70, 230, 180)));
//            }
//        }
//        else if (Constants.yelloColor[1])
//        {
//            if (Constants.isParecel[1][0]) {
//                editDatas.add(new NotificationEditData(R.drawable.textmassage, "TEXT MESSAGE", R.drawable.colorpicker, R.drawable.vibeone, Color.rgb(255, 217, 102), Color.rgb(255, 217, 102)));
//            }
//            else if (Constants.isParecel[1][1]) {
//                editDatas.add(new NotificationEditData(R.drawable.textmassage, "TEXT MESSAGE", R.drawable.colorpicker, R.drawable.vibetwo, Color.rgb(255, 217, 102), Color.rgb(255, 217, 102)));
//            }
//            else if (Constants.isParecel[1][2]) {
//                editDatas.add(new NotificationEditData(R.drawable.textmassage, "TEXT MESSAGE", R.drawable.colorpicker, R.drawable.vibethree, Color.rgb(255, 217, 102), Color.rgb(255, 217, 102)));
//            }
//            else if (Constants.isParecel[1][3]) {
//                editDatas.add(new NotificationEditData(R.drawable.textmassage, "TEXT MESSAGE", R.drawable.colorpicker, R.drawable.vibefour, Color.rgb(255, 217, 102), Color.rgb(255, 217, 102)));
//            }
//            else if (Constants.isParecel[1][4]) {
//                editDatas.add(new NotificationEditData(R.drawable.textmassage, "TEXT MESSAGE", R.drawable.colorpicker, R.drawable.infinity, Color.rgb(255, 217, 102), Color.rgb(255, 217, 102)));
//            }
//            else if (Constants.isParecel[1][5]) {
//                editDatas.add(new NotificationEditData(R.drawable.textmassage, "TEXT MESSAGE", R.drawable.colorpicker, R.drawable.none, Color.rgb(255, 217, 102), Color.rgb(255, 217, 102)));
//            }
//        }
//        else if (Constants.whiteColor[1])
//        {
//            if (Constants.isParecel[1][0]) {
//                editDatas.add(new NotificationEditData(R.drawable.textmassage, "TEXT MESSAGE", R.drawable.colorpicker, R.drawable.vibeone, Color.rgb(255, 255, 255), Color.rgb(255, 255, 255)));
//            }
//            else if (Constants.isParecel[1][1]) {
//                editDatas.add(new NotificationEditData(R.drawable.textmassage, "TEXT MESSAGE", R.drawable.colorpicker, R.drawable.vibetwo, Color.rgb(255, 255, 255), Color.rgb(255, 255, 255)));
//            }
//            else if (Constants.isParecel[1][2]) {
//                editDatas.add(new NotificationEditData(R.drawable.textmassage, "TEXT MESSAGE", R.drawable.colorpicker, R.drawable.vibethree, Color.rgb(255, 255, 255), Color.rgb(255, 255, 255)));
//            }
//            else if (Constants.isParecel[1][3]) {
//                editDatas.add(new NotificationEditData(R.drawable.textmassage, "TEXT MESSAGE", R.drawable.colorpicker, R.drawable.vibefour, Color.rgb(255, 255, 255), Color.rgb(255, 255, 255)));
//            }
//            else if (Constants.isParecel[1][4]) {
//                editDatas.add(new NotificationEditData(R.drawable.textmassage, "TEXT MESSAGE", R.drawable.colorpicker, R.drawable.infinity, Color.rgb(255, 255, 255), Color.rgb(255, 255, 255)));
//            }
//            else if (Constants.isParecel[1][5]) {
//                editDatas.add(new NotificationEditData(R.drawable.textmassage, "TEXT MESSAGE", R.drawable.colorpicker, R.drawable.none, Color.rgb(255, 255, 255), Color.rgb(255, 255, 255)));
//            }
//        }
//        else{
//            editDatas.add(new NotificationEditData(R.drawable.textmassage, "TEXT MESSAGE", R.drawable.colorpicker, R.drawable.vibrate, Color.rgb(255, 255, 255),  Color.rgb(75, 173, 172)));
//        }
//
//        if (Constants.redColor[2]) {
//            if(Constants.isParecel[2][0]) {
//                editDatas.add(new NotificationEditData(R.drawable.reminders, "REMINDERS", R.drawable.colorpicker, R.drawable.vibeone, Color.rgb(220, 30, 230), Color.rgb(220, 30, 230)));
//            }
//            else if(Constants.isParecel[2][1]) {
//                editDatas.add(new NotificationEditData(R.drawable.reminders, "REMINDERS", R.drawable.colorpicker, R.drawable.vibetwo, Color.rgb(220, 30, 230), Color.rgb(220, 30, 230)));
//            }
//            else if(Constants.isParecel[2][2]) {
//                editDatas.add(new NotificationEditData(R.drawable.reminders, "REMINDERS", R.drawable.colorpicker, R.drawable.vibethree, Color.rgb(220, 30, 230), Color.rgb(220, 30, 230)));
//            }
//            else if(Constants.isParecel[2][3]) {
//                editDatas.add(new NotificationEditData(R.drawable.reminders, "REMINDERS", R.drawable.colorpicker, R.drawable.vibefour, Color.rgb(220, 30, 230), Color.rgb(220, 30, 230)));
//            }
//            else if(Constants.isParecel[2][4]) {
//                editDatas.add(new NotificationEditData(R.drawable.reminders, "REMINDERS", R.drawable.colorpicker, R.drawable.infinity, Color.rgb(220, 30, 230), Color.rgb(220, 30, 230)));
//            }
//            else if(Constants.isParecel[2][5]) {
//                editDatas.add(new NotificationEditData(R.drawable.reminders, "REMINDERS", R.drawable.colorpicker, R.drawable.none, Color.rgb(220, 30, 230), Color.rgb(220, 30, 230)));
//            }
//        }
//        else if (Constants.blueColor[2])
//        {
//            if (Constants.isParecel[2][0]) {
//                editDatas.add(new NotificationEditData(R.drawable.reminders, "REMINDERS", R.drawable.colorpicker, R.drawable.vibeone, Color.rgb(9, 158, 253), Color.rgb(9, 158, 253)));
//            }
//            else if (Constants.isParecel[2][1]) {
//                editDatas.add(new NotificationEditData(R.drawable.reminders, "REMINDERS", R.drawable.colorpicker, R.drawable.vibetwo, Color.rgb(9, 158, 253), Color.rgb(9, 158, 253)));
//            }
//            else if (Constants.isParecel[2][2]) {
//                editDatas.add(new NotificationEditData(R.drawable.reminders, "REMINDERS", R.drawable.colorpicker, R.drawable.vibethree, Color.rgb(9, 158, 253), Color.rgb(9, 158, 253)));
//            }
//            else if (Constants.isParecel[2][3]) {
//                editDatas.add(new NotificationEditData(R.drawable.reminders, "REMINDERS", R.drawable.colorpicker, R.drawable.vibefour, Color.rgb(9, 158, 253), Color.rgb(9, 158, 253)));
//            }
//            else if (Constants.isParecel[2][4]) {
//                editDatas.add(new NotificationEditData(R.drawable.reminders, "REMINDERS", R.drawable.colorpicker, R.drawable.infinity, Color.rgb(9, 158, 253), Color.rgb(9, 158, 253)));
//            }
//            else if (Constants.isParecel[2][5]) {
//                editDatas.add(new NotificationEditData(R.drawable.reminders, "REMINDERS", R.drawable.colorpicker, R.drawable.none, Color.rgb(9, 158, 253), Color.rgb(9, 158, 253)));
//            }
//        }
//        else if (Constants.greenColor[2])
//        {
//            if (Constants.isParecel[2][0]) {
//                editDatas.add(new NotificationEditData(R.drawable.reminders, "REMINDERS", R.drawable.colorpicker, R.drawable.vibeone, Color.rgb(70, 230, 180), Color.rgb(70, 230, 180)));
//            }
//            else if (Constants.isParecel[2][1]) {
//                editDatas.add(new NotificationEditData(R.drawable.reminders, "REMINDERS", R.drawable.colorpicker, R.drawable.vibetwo, Color.rgb(70, 230, 180), Color.rgb(70, 230, 180)));
//            }
//            else if (Constants.isParecel[2][2]) {
//                editDatas.add(new NotificationEditData(R.drawable.reminders, "REMINDERS", R.drawable.colorpicker, R.drawable.vibethree, Color.rgb(70, 230, 180), Color.rgb(70, 230, 180)));
//            }
//            else if (Constants.isParecel[2][3]) {
//                editDatas.add(new NotificationEditData(R.drawable.reminders, "REMINDERS", R.drawable.colorpicker, R.drawable.vibefour, Color.rgb(70, 230, 180), Color.rgb(70, 230, 180)));
//            }
//            else if (Constants.isParecel[2][4]) {
//                editDatas.add(new NotificationEditData(R.drawable.reminders, "REMINDERS", R.drawable.colorpicker, R.drawable.infinity, Color.rgb(70, 230, 180), Color.rgb(70, 230, 180)));
//            }
//            else if (Constants.isParecel[2][5]) {
//                editDatas.add(new NotificationEditData(R.drawable.reminders, "REMINDERS", R.drawable.colorpicker, R.drawable.none, Color.rgb(70, 230, 180), Color.rgb(70, 230, 180)));
//            }
//        }
//        else if (Constants.yelloColor[2])
//        {
//            if (Constants.isParecel[2][0]) {
//                editDatas.add(new NotificationEditData(R.drawable.reminders, "REMINDERS", R.drawable.colorpicker, R.drawable.vibeone, Color.rgb(255, 217, 102), Color.rgb(255, 217, 102)));
//            }
//            else if (Constants.isParecel[2][1]) {
//                editDatas.add(new NotificationEditData(R.drawable.reminders, "REMINDERS", R.drawable.colorpicker, R.drawable.vibetwo, Color.rgb(255, 217, 102), Color.rgb(255, 217, 102)));
//            }
//            else if (Constants.isParecel[2][2]) {
//                editDatas.add(new NotificationEditData(R.drawable.reminders, "REMINDERS", R.drawable.colorpicker, R.drawable.vibethree, Color.rgb(255, 217, 102), Color.rgb(255, 217, 102)));
//            }
//            else if (Constants.isParecel[2][3]) {
//                editDatas.add(new NotificationEditData(R.drawable.reminders, "REMINDERS", R.drawable.colorpicker, R.drawable.vibefour, Color.rgb(255, 217, 102), Color.rgb(255, 217, 102)));
//            }
//            else if (Constants.isParecel[2][4]) {
//                editDatas.add(new NotificationEditData(R.drawable.reminders, "REMINDERS", R.drawable.colorpicker, R.drawable.infinity, Color.rgb(255, 217, 102), Color.rgb(255, 217, 102)));
//            }
//            else if (Constants.isParecel[2][5]) {
//                editDatas.add(new NotificationEditData(R.drawable.reminders, "REMINDERS", R.drawable.colorpicker, R.drawable.none, Color.rgb(255, 217, 102), Color.rgb(255, 217, 102)));
//            }
//        }
//        else if (Constants.whiteColor[2])
//        {
//            if (Constants.isParecel[2][0]) {
//                editDatas.add(new NotificationEditData(R.drawable.reminders, "REMINDERS", R.drawable.colorpicker, R.drawable.vibeone, Color.rgb(255, 255, 255), Color.rgb(255, 255, 255)));
//            }
//            else if (Constants.isParecel[2][1]) {
//                editDatas.add(new NotificationEditData(R.drawable.reminders, "REMINDERS", R.drawable.colorpicker, R.drawable.vibetwo, Color.rgb(255, 255, 255), Color.rgb(255, 255, 255)));
//            }
//            else if (Constants.isParecel[2][2]) {
//                editDatas.add(new NotificationEditData(R.drawable.reminders, "REMINDERS", R.drawable.colorpicker, R.drawable.vibethree, Color.rgb(255, 255, 255), Color.rgb(255, 255, 255)));
//            }
//            else if (Constants.isParecel[2][3]) {
//                editDatas.add(new NotificationEditData(R.drawable.reminders, "REMINDERS", R.drawable.colorpicker, R.drawable.vibefour, Color.rgb(255, 255, 255), Color.rgb(255, 255, 255)));
//            }
//            else if (Constants.isParecel[2][4]) {
//                editDatas.add(new NotificationEditData(R.drawable.reminders, "REMINDERS", R.drawable.colorpicker, R.drawable.infinity, Color.rgb(255, 255, 255), Color.rgb(255, 255, 255)));
//            }
//            else if (Constants.isParecel[2][5]) {
//                editDatas.add(new NotificationEditData(R.drawable.reminders, "REMINDERS", R.drawable.colorpicker, R.drawable.none, Color.rgb(255, 255, 255), Color.rgb(255, 255, 255)));
//            }
//        }
//        else{
//            editDatas.add(new NotificationEditData(R.drawable.reminders, "REMINDERS", R.drawable.colorpicker, R.drawable.vibrate, Color.rgb(255, 255, 255),Color.rgb(75, 173, 172)));
//        }
    }
}
