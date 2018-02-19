package com.msfrc.msfcontent.connection;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
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
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import com.msfrc.msfcontent.R;
import com.msfrc.msfcontent.base.Constants;
import com.msfrc.msfcontent.home.UIScene;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ConnectionScene extends AppCompatActivity implements LocationListener{

    Uri myPicture = null;
    private static final String TAG = "ConnectionScene";
    AudioManager mAudioManager;

    //Bluetooth
    private BluetoothAdapter mBluetoothAdapter;
    public static BluetoothService mBluetoothService;
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
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

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
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            setScene();
        }
        return super.onTouchEvent(event);
    }

    public void setScene() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mBluetoothService = new BluetoothService(this, mHandler);
        Intent connectionSceneIntent = new Intent(getApplicationContext(), DeviceListActivity.class);
        startActivityForResult(connectionSceneIntent, Constants.REQUEST_CONNECT_DEVICE);
    }

    private void connectDevice(Intent data, boolean secure) {
        // Get the device MAC address
        String address = data.getExtras()
                .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
        // Get the BluetoothDevice object
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        // Attempt to connect to the device
        mBluetoothService.connect(device, secure);
        Log.d(TAG, "Connect finish");
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "super called");
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG, "requestCode:" + requestCode);

        switch (requestCode) {
            case Constants.REQUEST_CONNECT_DEVICE:
                if (resultCode == Activity.RESULT_OK) {
                    Log.d(TAG, "BluetoothDevice connect");
                    connectDevice(data, false);
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

    @SuppressLint("HandlerLeak")
    public final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constants.MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case BluetoothService.STATE_CONNECTED:
                            Log.d(TAG, "STATE CONNECTED");
                            /*Intent uiSceneIntent = new Intent(getApplicationContext(), UIScene.class);
                            startActivity(uiSceneIntent);*/
                            break;
                    }
                    break;
                case Constants.MESSAGE_WRITE:
                    byte[] writeBuf = (byte[]) msg.obj;
                    // construct a string from the buffer
                    String writeMessage = new String(writeBuf);
                    Log.d(TAG, writeMessage);
                    break;
                case Constants.MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;
                    // construct a string from the valid bytes in the buffer
                    String readMessage = new String(readBuf, 0, msg.arg1);
                    Log.d(TAG, readMessage);
                    try {
                        actions(readMessage);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    //Toast.makeText(getApplicationContext(), readMessage, Toast.LENGTH_SHORT).show();
                    break;
                case Constants.MESSAGE_DEVICE_NAME:
                    // save the connected device's name
                    mConnectedDeviceName = msg.getData().getString(Constants.DEVICE_NAME);
                    if (null != this) {
                        Toast.makeText(getApplicationContext(), "Connected to "
                                + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                    }
                    if (mBluetoothService.getState() == mBluetoothService.STATE_CONNECTED) {
                        Intent uiHomeIntent = new Intent(getApplicationContext(), UIScene.class);
                        startActivity(uiHomeIntent);
                    }
                    break;
            }
        }
    };

    public void actions(String message) throws ClassNotFoundException, NoSuchMethodException, RemoteException, IllegalAccessException, InvocationTargetException {

        Log.d(TAG, "actions function called");
        if(message.equals("SingleClick")){
//            if(Constants.musicPage){
//                if(MusicSceneAdapter.isFirstLineSingleChecked) {
//                    playMusic();
//                }
//                else if(MusicSceneAdapter.isSecondLineSingleChecked){
//                    nextMusic();
//                }
//                else{
//                    prevMusic();
//                }
//            }
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
//            else if(Constants.mannermodePage){
//                if(MannerModeSceneAdapter.isFirstLineSingleChecked) {
//                    mAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
//                }
//                else if(MannerModeSceneAdapter.isSecondLineSingleChecked){
//                    mAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
//                }
//                else{
//                    rejectCall();
//                }
//            }
//            else if(Constants.findPhonePage){
//                findPhone();
//            }
        }
        else if(message.equals("DoubleClick")){
//            if(Constants.musicPage){
//                if(MusicSceneAdapter.isFirstLineDoubleChecked){
//                    playMusic();
//                }
//                else if(MusicSceneAdapter.isSecondLineDoubleChecked){
//                    nextMusic();
//                }
//                else{
//                    prevMusic();
//                }
//            }
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
//            else if(Constants.mannermodePage){
//                if(MannerModeSceneAdapter.isFirstLineDoubleChecked) {
//                    mAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
//                }
//                else if(MannerModeSceneAdapter.isSecondLineDoubleChecked){
//                    mAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
//                }
//                else{
//                    rejectCall();
//                }
//            }
        }
        else if(message.equals("Hold")){
//            if(Constants.musicPage){
//                if(MusicSceneAdapter.isFirstLineHoldChecked){
//                    playMusic();
//                }
//                else if(MusicSceneAdapter.isSecondLineHoldChecked){
//                    nextMusic();
//                }
//                else {
//                    prevMusic();
//                }
//            }
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
//            else if(Constants.mannermodePage){
//                if(MannerModeSceneAdapter.isFirstLineHoldChecked){
//                    mAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
//                }
//                else if(MannerModeSceneAdapter.isSecondLineHoldChecked){
//                    mAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
//                }
//                else {
//                    rejectCall();
//                }
//            }
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
//        Intent musicPlayerStartIntent = new Intent(MediaStore.INTENT_ACTION_MUSIC_PLAYER);
//        startActivity(musicPlayerStartIntent);
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
    public void onTestButtondClicked(View v){
        Intent uiHomeIntent = new Intent(getApplicationContext(), UIScene.class);
        startActivity(uiHomeIntent);
    }
}
