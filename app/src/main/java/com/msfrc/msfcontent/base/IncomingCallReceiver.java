package com.msfrc.msfcontent.base;

import android.content.Context;

import com.msfrc.msfcontent.connection.ConnectionScene;

public class IncomingCallReceiver extends CallReceiver {

    @Override
    protected void onIncomingCallReceived(Context ctx) {
        switch (Constants.notiCallColor) {
            case Constants.COLOR_WHITE:
                ConnectionScene.mBluetoothLeService.writeColorCharacteristic("1106020d");
                break;
            case Constants.COLOR_RED:
                ConnectionScene.mBluetoothLeService.writeColorCharacteristic("1123E119");
                break;
            case Constants.COLOR_GREEN:
                ConnectionScene.mBluetoothLeService.writeColorCharacteristic("11B9194B");
                break;
            case Constants.COLOR_YELLOW:
                ConnectionScene.mBluetoothLeService.writeColorCharacteristic("11020BDB");
                break;
            case Constants.COLOR_BLUE:
                ConnectionScene.mBluetoothLeService.writeColorCharacteristic("11F66100");
                break;
            default:
                ConnectionScene.mBluetoothLeService.writeColorCharacteristic("1106020d");
                break;
        }
    }

    @Override
    protected void onIncomingCallEnded(Context ctx) {

    }
}
