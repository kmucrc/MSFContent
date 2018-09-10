package com.msfrc.msfcontent.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.msfrc.msfcontent.connection.ConnectionScene;

public abstract class CallReceiver extends BroadcastReceiver {

    private static int nLastState = TelephonyManager.CALL_STATE_IDLE;

    @Override
    public void onReceive(Context context, Intent intent) {
        String strState = intent.getExtras().getString(TelephonyManager.EXTRA_STATE);
        int nState = TelephonyManager.CALL_STATE_IDLE;

        if(strState.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
            nState = TelephonyManager.CALL_STATE_IDLE;
        } else if(strState.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
            nState = TelephonyManager.CALL_STATE_OFFHOOK;
        } else if(strState.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
            nState = TelephonyManager.CALL_STATE_RINGING;
        }
        
        onCallStateChanged(context, nState);
    }

    private void onCallStateChanged(Context context, int nState) {
        if(nLastState == nState || nState == TelephonyManager.CALL_STATE_OFFHOOK) {
            return;
        }

        switch (nState) {
            case TelephonyManager.CALL_STATE_IDLE:
                Log.e("eleutheria", "CALL_STATE_IDLE");
                break;
            case TelephonyManager.CALL_STATE_RINGING:
                Log.e("eleutheria", "CALL_STATE_RINGING");
                switch (Constants.notiCallColor) {
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
        nLastState = nState;
    }
}
