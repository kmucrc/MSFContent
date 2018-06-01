package com.msfrc.msfcontent;

public class ActionReceiver {//extends BroadcastReceiver {
//    @Override
//    public void onReceive(Context context, Intent intent) {
//        final String action = intent.getAction();
//        if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {
//            ConnectionScene.mConnected = true;
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    ConnectionScene.mConnected = true;
//                    ConnectionScene.connectDevice();
//                    ConnectionScene.start.setVisibility(View.VISIBLE);
//                }
//            });
//            invalidateOptionsMenu();
//        } else if (BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action)) {
//            mConnected = false;
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    start.setVisibility(View.INVISIBLE);
//                }
//            });
//        } else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
//
//        } else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
//            //데이터 읽어서 뿌리는 부분
//            String mdata = intent.getStringExtra(BluetoothLeService.EXTRA_DATA);
//            Log.d("ConnectionScene", "Click!");
//            if(mdata.contains("00")){
//                actions("SingleClick");
//            }
//        }
//    }
}
