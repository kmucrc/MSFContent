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


    protected abstract void onIncomingCallReceived(Context ctx);
    protected abstract void onIncomingCallEnded(Context ctx);

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
                onIncomingCallReceived(context);
        }
        nLastState = nState;
    }
}
