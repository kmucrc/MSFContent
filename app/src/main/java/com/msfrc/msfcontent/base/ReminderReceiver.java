package com.msfrc.msfcontent.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.msfrc.msfcontent.connection.ConnectionScene;

public class ReminderReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("eleutheria", "ReminderReceiver onReceive");
        if(intent.getAction().equals(Constants.REMINDER_ACTION_NAME)){
            switch (Constants.notiReminderColor) {
                case Constants.COLOR_WHITE:
                    ConnectionScene.mBluetoothLeService.writeColorCharacteristic("11000000");
                    break;
                case Constants.COLOR_RED:
                    ConnectionScene.mBluetoothLeService.writeColorCharacteristic("1123E119");
                    break;
                case Constants.COLOR_GREEN:
                    ConnectionScene.mBluetoothLeService.writeColorCharacteristic("11B9194B");
                    break;
                case Constants.COLOR_YELLOW:
                    ConnectionScene.mBluetoothLeService.writeColorCharacteristic("11002699");
                    break;
                case Constants.COLOR_BLUE:
                    ConnectionScene.mBluetoothLeService.writeColorCharacteristic("11F66100");
                    break;
                default:
                    ConnectionScene.mBluetoothLeService.writeColorCharacteristic("11000000");
                    break;
            }
        }
    }
}
