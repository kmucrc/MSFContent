package com.msfrc.msfcontent.connection;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.msfrc.msfcontent.R;
import com.msfrc.msfcontent.base.Constants;
import com.msfrc.msfcontent.click.mannermode.MannerModeSceneAdapter;
import com.msfrc.msfcontent.click.music.MusicSceneAdapter;
import com.msfrc.msfcontent.home.UIScene;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ConnectionScene extends AppCompatActivity implements LocationListener{

    Uri myPicture = null;
    private static final String TAG = "ConnectionScene";
    AudioManager mAudioManager;
    Button start;

    //Bluetooth
    static BluetoothAdapter mBluetoothAdapter;
    private boolean isPlaying = false;
    private String mConnectedDeviceName = null;

    private boolean isGpsEnabled;
    private boolean isNetworkEnabled;
    private int count = 0;
    //GPS
    private LocationManager mLocationManager;
    private String mProvider;
    private Location location;
    private double latitude;
    private double longitude;
    private double altitude;

    //ble
    public static final String EXTRAS_DEVICE_NAME = "DEVICE_NAME";
    public static final String EXTRAS_DEVICE_ADDRESS = "DEVICE_ADDRESS";
    public static String device_address;
    private String device_name;
    public static BluetoothLeService mBluetoothLeService;
    public static boolean mConnected = false;
    // Code to manage Service lifecycle.
    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mBluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();
            if (!mBluetoothLeService.initialize()) {
                Log.e(TAG, "Unable to initialize Bluetooth");
                finish();
            }
            // Automatically connects to the device upon successful start-up initialization.
            mBluetoothLeService.connect(device_address);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBluetoothLeService = null;
           // start.setVisibility(View.INVISIBLE);
        }
    };
    public static String mdata;
    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {
                mConnected = true;
//                updateConnectionState(R.string.connected); //아래줄
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mConnected = true;
                        connectDevice();
                        start.setVisibility(View.VISIBLE);
                    }
                });
                invalidateOptionsMenu();
            } else if (BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action)) {
                mConnected = false;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        start.setVisibility(View.INVISIBLE);
                    }
                });
//                updateConnectionState(R.string.disconnected);
                invalidateOptionsMenu();
//                clearUI();
            } else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
                // Show all the supported services and characteristics on the user interface.
//                displayGattServices(mBluetoothLeService.getSupportedGattServices());
            } else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
                //데이터 읽어서 뿌리는 부분
                mdata = intent.getStringExtra(BluetoothLeService.EXTRA_DATA);
//                displayData(intent.getStringExtra(BluetoothLeService.EXTRA_DATA));
            }
        }
    };

    private int cameraId;

    //Find phone
    private Spinner mSminner;
    MediaPlayer mAudio = null;
    boolean isAudioPlay = false;
    int streamType = AudioManager.STREAM_RING;

//    private GoogleApiClient client;

    public ConnectionScene(){}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scence_connection);

        getSupportActionBar().hide();
        start = (Button)findViewById(R.id.startButton);

        mAudioManager = (AudioManager) getBaseContext().getSystemService(Context.AUDIO_SERVICE);
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        mProvider = mLocationManager.getBestProvider(criteria, false);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);

//        if(isGpsEnabled) {
//            location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//            Log.d(TAG, "GPSProvider");
//        }
//        else if(isNetworkEnabled){
//            location = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//            Log.d(TAG, "NetworkProvider");
//        }
        cameraId = findFrontCameraId();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        if(location!= null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            altitude = location.getAltitude();
            if(isNetworkEnabled) {
                mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, this);
                Log.d(TAG, "NETWORKENABLED");
            }
            if(isGpsEnabled){
                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            }
            onLocationChanged(location);
        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
//        }//error원인!!!

        //BLE지원여부
        if(!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)){
            Toast.makeText(this, R.string.ble_not_supported, Toast.LENGTH_SHORT).show();
        }
        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
        //Bluetooth지원여부
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, R.string.error_bluetooth_not_supported, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        //bluetooth활성화
        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 2);
        }

    }
    //ble scanner문제해결
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 456;
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_COARSE_LOCATION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted, yay! Start the Bluetooth device scan.
                } else {
                    // Alert the user that this application requires the location permission to perform the scan.
                }
            }
        }
    }
    private int findFrontCameraId() {
        int cameraId = -1;
//        // Search for the front facing camera
//        int numberOfCameras = Camera.getNumberOfCameras();
//        for (int i = 0; i < numberOfCameras; i++) {
//            Camera.CameraInfo info = new Camera.CameraInfo();
//            Camera.getCameraInfo(i, info);
//            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
//                cameraId = i;
//                break;
//            }
//        }
        return cameraId;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(mBluetoothLeService!=null)
            mBluetoothLeService.disconnect();
        if(event.getActionMasked()==MotionEvent.ACTION_DOWN){
            Log.d(TAG, "Scan Device");
            setScene();
        }

        return super.onTouchEvent(event);
    }

    public void setScene() {

        Intent i = new Intent(getApplicationContext(), DeviceScanActivity.class);
        startActivityForResult(i, Constants.REQUEST_CONNECT_DEVICE);

    }

    Intent data;
    private void connectDevice() {

        device_name = data.getExtras().getString(EXTRAS_DEVICE_NAME);
        device_address = data.getExtras().getString(EXTRAS_DEVICE_ADDRESS);
        Log.d(TAG, device_name);
        mBluetoothLeService.connect(device_address);
        if(mConnected){
            Log.d(TAG, "Connect finish");
            mConnected = true;
            //add UI scene
            Intent uiHomeIntent = new Intent(getApplicationContext(), UIScene.class);
            startActivity(uiHomeIntent);
        }else{
            Toast.makeText(this, "블루투스가 연결되지 않았습니다.",Toast.LENGTH_SHORT).show();
        }

//        if(mBluetoothLeService.connect(device_address)){
//            Log.d(TAG, "Connect finish");
//            //add UI scene
//            Intent uiHomeIntent = new Intent(getApplicationContext(), UIScene.class);
//            startActivity(uiHomeIntent);
//        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "super called");
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG, "requestCode:" + requestCode);
        Log.d(TAG, "resultCode:" + resultCode);

        switch (requestCode) {
            case Constants.REQUEST_CONNECT_DEVICE://2
                if (resultCode == Activity.RESULT_OK) {
                    Log.d(TAG, "BluetoothDevice scan");
                    Intent gattServiceIntent = new Intent(this, BluetoothLeService.class);
                    bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
                    this.data = data;
                    start.setVisibility(View.VISIBLE);
                }
                break;
            case Constants.CAMERA_CAPTURE:
                Log.d(TAG, "CAMERA CAPTURE");
//                onCaptureImageResult(data);
//                break;

                if (resultCode == RESULT_OK)
                    try {
                        data.getExtras().get("data"); //섬네일
                    } catch (Exception e) {
                    }
                break;
        }
    }

//    @SuppressLint("HandlerLeak")
//    public final Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case Constants.MESSAGE_STATE_CHANGE:
//                    switch (msg.arg1) {
//                        case BluetoothLeService.STATE_CONNECTED:
//                            Log.d(TAG, "STATE CONNECTED");
//                            Intent uiSceneIntent = new Intent(getApplicationContext(), UIScene.class);
//                            startActivity(uiSceneIntent);
//                            break;
//                    }
//                    break;
//                case Constants.MESSAGE_WRITE:
//                    byte[] writeBuf = (byte[]) msg.obj;
//                    // construct a string from the buffer
//                    String writeMessage = new String(writeBuf);
//                    Log.d(TAG, writeMessage);
//                    break;
//                case Constants.MESSAGE_READ:
//                    byte[] readBuf = (byte[]) msg.obj;
//                    // construct a string from the valid bytes in the buffer
//                    String readMessage = new String(readBuf, 0, msg.arg1);
//                    Log.d(TAG, readMessage);
//                    try {
//                        actions(readMessage);
//                    } catch (ClassNotFoundException e) {
//                        e.printStackTrace();
//                    } catch (NoSuchMethodException e) {
//                        e.printStackTrace();
//                    } catch (RemoteException e) {
//                        e.printStackTrace();
//                    } catch (IllegalAccessException e) {
//                        e.printStackTrace();
//                    } catch (InvocationTargetException e) {
//                        e.printStackTrace();
//                    }
//                    //Toast.makeText(getApplicationContext(), readMessage, Toast.LENGTH_SHORT).show();
//                    break;
//                case Constants.MESSAGE_DEVICE_NAME:
//                    // save the connected device's name
//                    Log.d(TAG, "checkcheck");
//                    mConnectedDeviceName = msg.getData().getString(Constants.DEVICE_NAME);
//                    if (null != this) {
//                        Toast.makeText(getApplicationContext(), "Connected to "
//                                + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
//                    }
//                    break;
//            }
//        }
//    };

    public void actions(String message) throws ClassNotFoundException, NoSuchMethodException, RemoteException, IllegalAccessException, InvocationTargetException {

        Log.d(TAG, "actions function called");
        if(message.equals("SingleClick")){
            if(Constants.musicPage){
                if(MusicSceneAdapter.isFirstLineSingleChecked) {
                    playMusic();
                }
                else if(MusicSceneAdapter.isSecondLineSingleChecked){
                    nextMusic();
                }
                else{
                    prevMusic();
                }
            }
//            else if(Constants.clickCameraPage){
//                if(CameraSceneAdapter.isFirstLineSingleChecked) {
//                    captureCamera();
//                }
//                else if(CameraSceneAdapter.isSecondLineSingleChecked){
//                    videoCamera();
//                }
//                else{
//                    selfieCamera();
//                }
//            }
//            else if(Constants.emergencyPage){
//                if(EmergencySceneAdapter.isFirstLineSingleChecked) {
//                    sendSMS();
//                }
//            }
            else if(Constants.mannermodePage){
                if(MannerModeSceneAdapter.isFirstLineSingleChecked) {
                    mAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                }
                else if(MannerModeSceneAdapter.isSecondLineSingleChecked){
                    mAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                }
                else{
                    rejectCall();
                }
            }
//            else if(Constants.findPhonePage){
//                findPhone();
//            }
        }
        else if(message.equals("DoubleClick")){
            if(Constants.musicPage){
                if(MusicSceneAdapter.isFirstLineDoubleChecked){
                    playMusic();
                }
                else if(MusicSceneAdapter.isSecondLineDoubleChecked){
                    nextMusic();
                }
                else{
                    prevMusic();
                }
            }
//            else if(Constants.clickCameraPage){
//                if(CameraSceneAdapter.isFirstLineDoubleChecked){
//                    captureCamera();
//                }
//                else if(CameraSceneAdapter.isSecondLineDoubleChecked){
//                    videoCamera();
//                }
//                else{
//                    selfieCamera();
//                }
//            }
//            else if(Constants.emergencyPage){
//                if(EmergencySceneAdapter.isFirstLineDoubleChecked){
//                    sendSMS();
//                }
//            }
            else if(Constants.mannermodePage){
                if(MannerModeSceneAdapter.isFirstLineDoubleChecked) {
                    mAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                }
                else if(MannerModeSceneAdapter.isSecondLineDoubleChecked){
                    mAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                }
                else{
                    rejectCall();
                }
            }
        }
        else if(message.equals("Hold")){
            if(Constants.musicPage){
                if(MusicSceneAdapter.isFirstLineHoldChecked){
                    playMusic();
                }
                else if(MusicSceneAdapter.isSecondLineHoldChecked){
                    nextMusic();
                }
                else {
                    prevMusic();
                }
            }
//            else if(Constants.clickCameraPage){
//                if(CameraSceneAdapter.isFirstLineHoldChecked){
//                    captureCamera();
//                }
//                else if(CameraSceneAdapter.isSecondLineHoldChecked){
//                    videoCamera();
//                }
//                else {
//                    selfieCamera();
//                }
//            }
//            else if(Constants.emergencyPage){
//                if(EmergencySceneAdapter.isFirstLineHoldChecked){
//                    sendSMS();
//                }
//            }
            else if(Constants.mannermodePage){
                if(MannerModeSceneAdapter.isFirstLineHoldChecked){
                    mAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                }
                else if(MannerModeSceneAdapter.isSecondLineHoldChecked){
                    mAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                }
                else {
                    rejectCall();
                }
            }
        }
        else if(message.equals("MusicPlayer")){
            startMusicPlayer();
        }
        else if(message.equals("OpenCamera")) {
            startCamera();
        }
    }

    //음악 플레이어 시작
    public void startMusicPlayer() {
        Intent musicPlayerStartIntent = new Intent(MediaStore.INTENT_ACTION_MUSIC_PLAYER);
        startActivity(musicPlayerStartIntent);
    }

    public void playMusic() {
        if (isPlaying == false) {
            Intent musicPlayerIntent = new Intent("com.android.music.musicservicecommand");
            musicPlayerIntent.putExtra("command", "play");
            sendBroadcast(musicPlayerIntent);
            isPlaying = true;
        } else {
            Intent musicPlayerIntent = new Intent("com.android.music.musicservicecommand");
            musicPlayerIntent.putExtra("command", "pause");
            sendBroadcast(musicPlayerIntent);
            isPlaying = false;
        }
    }

    //다음 음악 재생
    public void nextMusic() {
        /*Intent nextMusicIntent = new Intent("com.android.music.musicservicecommand");
        nextMusicIntent.putExtra("command", "next");
        sendBroadcast(nextMusicIntent);*/
        Constants.selfishot = true;
    }

    //이전 음악 재생
    public void prevMusic() {
        /*Intent nextMusicIntent = new Intent("com.android.music.musicservicecommand");
        nextMusicIntent.putExtra("command", "previous");
        sendBroadcast(nextMusicIntent);*/
        rejectCall();
    }

//    private Intent cameraIntent;
//    private Camera myCamera;
//    SurfaceView view;
//    private Camera.Parameters parameters;
//    private SurfaceHolder sHolder;
    public void startCamera() {
//        cameraIntent = new Intent(this, CameraActivity.class);
//        Constants.cameraPage = true;
//        Constants.selfiePage = false;
//        Constants.videoPage = false;
//        startActivity(cameraIntent);
    }
    public void selfieCamera(){
//        cameraIntent = new Intent(this, SelfieCamera.class);
//        Constants.selfiePage = true;
//        Constants.cameraPage = false;
//        Constants.videoPage = false;
//        startActivity(cameraIntent);
    }
    public void videoCamera(){
//        cameraIntent = new Intent(this, VideoActivity.class);
//        Constants.videoPage = true;
//        Constants.cameraPage = false;
//        Constants.selfiePage = false;
//        startActivity(cameraIntent);
    }
    public void captureCamera() {
        Log.d(TAG, "Capture start");
        if(Constants.cameraPage){
            Constants.cameraShot = true;
        }
        else if(Constants.selfiePage) {
            Constants.selfishot = true;
        }
        // Video
        else if(Constants.videoPage) {
            if (count % 2 == 0) {
                Constants.videoShot = true;
                count++;
            }
            else {
                Constants.videoShot = false;
                count++;
            }
        }
        //Get a surface
//        Instrumentation instrumentation = new Instrumentation();
//        instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_VOLUME_UP);
        //mAudioManager.adjustStreamVolume(AudioManager.STREAM_VOICE_CALL, AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
        //myCamera.takePicture(null, null, new PhotoHandler(getApplicationContext()));
        Log.d(TAG, ""+cameraId);
        /*new Thread(new Runnable() {
            public void run() {
                new Instrumentation().sendKeyDownUpSync(KeyEvent.KEYCODE_VOLUME_UP);
            }
        }).start();*/
        //Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //startActivityForResult(cameraIntent, Constants.CAMERA_CAPTURE);

//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//        }
    }


    public void sendSMS() {
        Log.d(TAG, "Send SMS");
        String phoneNumber = "01055201059";
        String message = "위도: " + latitude + ", 경도 :" + longitude + ", 고도" + altitude;

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber, null, message, null, null);
        Toast.makeText(getApplicationContext(), "위도: " + latitude + ", 경도 :" + longitude + ", 고도" + altitude, Toast.LENGTH_LONG).show();

    }



    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        altitude = location.getAltitude();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    public void rejectCall() {
        TelephonyManager telephonyManager = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        Class clazz = null;
        try {
            clazz = Class.forName(telephonyManager.getClass().getName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Method method = null;
        try {
            method = clazz.getDeclaredMethod("getITelephony");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        method.setAccessible(true);
//        ITelephony telephonyService = null;
//        try {
//            telephonyService = (ITelephony) method.invoke(telephonyManager);
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }
//        try {
//            telephonyService.endCall();
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }
    }

    public void findPhone() {
        if (!isAudioPlay) {
            try {
                mAudio = new MediaPlayer();
                Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
                mAudio.setDataSource(this, alert);
                mAudio.setAudioStreamType(streamType);
                mAudio.setLooping(true);
                mAudio.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mAudio.start();
            isAudioPlay = true;
        } else {
            mAudio.stop();
            isAudioPlay = false;
        }
    }
    public void onStartButtonClicked(View v){
        connectDevice();
    }
    public void onTestButtonClicked(View v){
        Intent uiHomeIntent = new Intent(getApplicationContext(), UIScene.class);
        startActivity(uiHomeIntent);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mBluetoothLeService!=null) {
            mBluetoothLeService.disconnect();
            unbindService(mServiceConnection);
        }
        mBluetoothLeService = null;
    }
    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
        if (mBluetoothLeService != null) {
            final boolean result = mBluetoothLeService.connect(device_address);
            Log.d(TAG, "Connect request result=" + result);
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mGattUpdateReceiver);
    }
    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
        return intentFilter;
    }

}
