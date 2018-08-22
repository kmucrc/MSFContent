package com.msfrc.msfcontent.connection;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.NotificationManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
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
import com.msfrc.msfcontent.click.camera.CameraActivity;
import com.msfrc.msfcontent.click.camera.CameraSceneAdapter;
import com.msfrc.msfcontent.click.camera.SelfieCamera;
import com.msfrc.msfcontent.click.camera.VideoActivity;
import com.msfrc.msfcontent.click.emergency.EmergencySceneAdapter;
import com.msfrc.msfcontent.click.mannermode.MannerModeSceneAdapter;
import com.msfrc.msfcontent.click.music.MusicSceneAdapter;
import com.msfrc.msfcontent.home.UIScene;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConnectionScene extends AppCompatActivity implements LocationListener{

    public static Uri myPicture = null;
    private static final String TAG = "ConnectionScene";
    public static AudioManager mAudioManager;
    public static Button start;

    private static Camera mCamera;
    private static CameraManager camManager;
    private static Camera.Parameters parameters;

    //Bluetooth
    static BluetoothAdapter mBluetoothAdapter;
    private static Vibrator mVibe;
    private static long[] parcelArray = {0, 1000, 500};
    //recorder
    private static MediaRecorder mRecorder;
    public static boolean isPlaying = false;
    private String mConnectedDeviceName = null;

    private boolean isGpsEnabled;
    private boolean isNetworkEnabled;
    private int count = 0;
    //GPS
    private LocationManager mLocationManager;
    private String mProvider;
    private Location location;
    public static double latitude;
    public static double longitude;
    public static double altitude;

    //ble
    public static final String EXTRAS_DEVICE_NAME = "DEVICE_NAME";
    public static final String EXTRAS_DEVICE_ADDRESS = "DEVICE_ADDRESS";
    public static String device_address;
    public static String device_name;
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
        }
    };
    public String mdata="";
    public final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {
                mConnected = true;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mConnected = true;
                        connectDevice();
                        start.setVisibility(View.VISIBLE);
                    }
                });
//                invalidateOptionsMenu();
            } else if (BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action)) {
                mConnected = false;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        start.setVisibility(View.INVISIBLE);
                    }
                });
//                invalidateOptionsMenu();
            } else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
                // Show all the supported services and characteristics on the user interface.
//                displayGattServices(mBluetoothLeService.getSupportedGattServices());
                Log.d("ConnectionScene", "Click?");
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
    public static MediaPlayer mAudio = null;
    public static boolean isAudioPlay = false;
    public static int streamType = AudioManager.STREAM_RING;

//    private GoogleApiClient client;
    public static Context context;
    static Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        mActivity = this;
        setContentView(R.layout.scence_connection);

        getSupportActionBar().hide();
        start = (Button)findViewById(R.id.startButton);

//        mAudioManager = (AudioManager) getBaseContext().getSystemService(Context.AUDIO_SERVICE);
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        mProvider = mLocationManager.getBestProvider(criteria, false);

        //무음모드 에러
        NotificationManager notificationManager =
                (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //퍼미션 상태 확인
            if (!hasPermissions(PERMISSIONS)) {
                //퍼미션 허가 안되어있다면 사용자에게 요청
                requestPermissions(PERMISSIONS, PERMISSIONS_REQUEST_CODE);
            }
            if (!notificationManager.isNotificationPolicyAccessGranted()){
                Toast.makeText(context,"방해금지모드를 해제해주세요",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(android.provider.Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
                startActivity(intent);
            }
        }
//        if(isGpsEnabled) {
//            location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//            Log.d(TAG, "GPSProvider");
//        }
//        else if(isNetworkEnabled){
//            location = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//            Log.d(TAG, "NetworkProvider");
//        }
        cameraId = findFrontCameraId();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
            mBluetoothAdapter = bluetoothManager.getAdapter();
        }
    }
    //여기서부턴 퍼미션 관련 메소드
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 456;
    static final int PERMISSIONS_REQUEST_CODE = 1000;
    String[] PERMISSIONS  = {"android.permission.INTERNET"
            , "android.permission.ACCESS_FINE_LOCATION"
            , "android.permission.BLUETOOTH", "android.permission.BLUETOOTH_ADMIN"
            , "android.permission.SEND_SMS", "android.permission.READ_PHONE_STATE"
            , "android.permission.ACCESS_NOTIFICATION_POLICY"
            , "android.permission.READ_CONTACTS", "android.permission.CAMERA"
            , "android.permission.RECORD_AUDIO", "android.permission.WRITE_EXTERNAL_STORAGE"};

    private boolean hasPermissions(String[] permissions) {
        int result;
        //스트링 배열에 있는 퍼미션들의 허가 상태 여부 확인
        for (String perms : permissions){
            result = ContextCompat.checkSelfPermission(this, perms);
            if (result == PackageManager.PERMISSION_DENIED){
                //허가 안된 퍼미션 발견
                return false;
            }
        }
        //모든 퍼미션이 허가되었음
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode){
            case PERMISSIONS_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean cameraPermissionAccepted = grantResults[0]
                            == PackageManager.PERMISSION_GRANTED;
                    if (!cameraPermissionAccepted)
                        showDialogForPermission("앱을 실행하려면 퍼미션을 허가하셔야합니다.");
                }
                break;
            case PERMISSION_REQUEST_COARSE_LOCATION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted, yay! Start the Bluetooth device scan.
                } else {
                    // Alert the user that this application requires the location permission to perform the scan.
                }
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void showDialogForPermission(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder( ConnectionScene.this);
        builder.setTitle("알림");
        builder.setMessage(msg);
        builder.setCancelable(false);
        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id){
                requestPermissions(PERMISSIONS, PERMISSIONS_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                finish();
            }
        });
        builder.create().show();
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

    public static Intent data;
    public static void connectDevice() {
        device_name = data.getExtras().getString(EXTRAS_DEVICE_NAME);
        device_address = data.getExtras().getString(EXTRAS_DEVICE_ADDRESS);
        Log.d(TAG, device_name);
        mBluetoothLeService.connect(device_address);
        if(mConnected){
            Log.d(TAG, "Connect finish");
            mConnected = true;
            //add UI scene
            Intent uiHomeIntent = new Intent(context, UIScene.class);
            context.startActivity(uiHomeIntent);
        }else{
            Toast.makeText(context, "블루투스가 연결되지 않았습니다.",Toast.LENGTH_SHORT).show();
        }
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


    public static void actions(String message) {//throws ClassNotFoundException, NoSuchMethodException, RemoteException, IllegalAccessException, InvocationTargetException {

        Log.d(TAG, "actions function called : "+message);

        if(Constants.clickIndex == Constants.CLICK_MUSIC_PLAY){//Constants.musicPage){
            if(message.equals("SingleClick")) {
                if (Constants.musicPlay == Constants.CLICK_SINGLE) {
                    playMusic();
                    Log.d(TAG, "SingleClick playMusic");
                }
            } else if(message.equals("DoubleClick")) {
                if (Constants.musicForward == Constants.CLICK_DOUBLE) {
                    nextMusic();
                    Log.d(TAG, "SingleClick nextMusic");
                }
            } else if(message.equals("Hold")) {
                if (Constants.musicReverse == Constants.CLICK_HOLD) {
                    prevMusic();
                    Log.d(TAG, "SingleClick prevMusic");
                }
            }

        }
        else if(Constants.clickIndex == Constants.CLICK_CAMERA){//Constants.clickCameraPage){
            if(message.equals("SingleClick")) {
                if (Constants.cameraCamera == Constants.CLICK_SINGLE) {
                    captureCamera();
                }
            } else if(message.equals("DoubleClick")) {
                if (Constants.cameraCamera == Constants.CLICK_DOUBLE) {
                    captureCamera();
                }
            } else if(message.equals("Hold")) {
                if (Constants.cameraCamera == Constants.CLICK_HOLD) {
                    captureCamera();
                }
            }
        }
        else if(Constants.clickIndex == Constants.CLICK_EMERGENCY){//Constants.emergencyPage){
            if(message.equals("SingleClick")) {
                sendSMS();
            } else if(message.equals("DoubleClick")) {
                sendSMS();
            } else if(message.equals("Hold")) {
                sendSMS();
            }
        }
        else if(Constants.clickIndex == Constants.CLICK_MANNER_MODE){//Constants.mannermodePage){
            if(message.equals("SingleClick")) {
                if (Constants.mannermodeSilent == Constants.CLICK_SINGLE && mAudioManager.getRingerMode() != AudioManager.RINGER_MODE_SILENT) {
                    setSilentMode();
                } else if (Constants.mannermodeSound == Constants.CLICK_SINGLE) {
                    mAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                } else if (Constants.mannermodeRejectCall == Constants.CLICK_SINGLE) {
                    rejectCall();
                }
            } else if(message.equals("DoubleClick")) {
                if (Constants.mannermodeSilent == Constants.CLICK_DOUBLE && mAudioManager.getRingerMode() != AudioManager.RINGER_MODE_SILENT) {
                    setSilentMode();
                } else if (Constants.mannermodeSound == Constants.CLICK_DOUBLE) {
                    mAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                } else if (Constants.mannermodeRejectCall == Constants.CLICK_DOUBLE) {
                    rejectCall();
                }
            } else if(message.equals("Hold")) {
                if (Constants.mannermodeSilent == Constants.CLICK_HOLD && mAudioManager.getRingerMode() != AudioManager.RINGER_MODE_SILENT) {
                    setSilentMode();
                } else if (Constants.mannermodeSound == Constants.CLICK_HOLD) {
                    mAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                } else if (Constants.mannermodeRejectCall == Constants.CLICK_HOLD) {
                    rejectCall();
                }
            }
        }
        else if(Constants.clickIndex == Constants.CLICK_FIND_PHONE) {
            if(message.equals("SingleClick")) {
                if (Constants.findphoneFindPhone == Constants.CLICK_SINGLE) {
                    findPhone();
                }
            } else if(message.equals("DoubleClick")) {
                if (Constants.findphoneFindPhone == Constants.CLICK_DOUBLE) {
                    findPhone();
                }
            } else if(message.equals("Hold")) {
                if (Constants.findphoneFindPhone == Constants.CLICK_HOLD) {
                    findPhone();
                }
            }
        }
        else if(Constants.clickIndex == Constants.CLICK_LIGHT_CONTROL) {
            if(message.equals("SingleClick")) {
                if (Constants.lightLight == Constants.CLICK_SINGLE) {
                    if(Constants.light)
                        turnOffLight();
                    else turnOnLight();
                }
            } else if(message.equals("DoubleClick")) {
                if (Constants.lightLight == Constants.CLICK_DOUBLE) {
                    if(Constants.light)
                        turnOffLight();
                    else turnOnLight();
                }
            } else if(message.equals("Hold")) {
                if (Constants.lightLight == Constants.CLICK_HOLD) {
                    if(Constants.light)
                        turnOffLight();
                    else turnOnLight();
                }
            }
        }
        else if(Constants.clickIndex == Constants.CLICK_RECORD_VOICE) {
            if(message.equals("SingleClick")) {
                if (Constants.voicerecordRecord == Constants.CLICK_SINGLE) {
                    if(Constants.record)
                        stopRecordingVoice();
                    else recordingVoice();
                }
            } else if(message.equals("DoubleClick")) {
                if (Constants.voicerecordRecord == Constants.CLICK_DOUBLE) {
                    if(Constants.record)
                        stopRecordingVoice();
                    else recordingVoice();
                }
            } else if(message.equals("Hold")) {
                if (Constants.voicerecordRecord == Constants.CLICK_HOLD) {
                    if(Constants.record)
                        stopRecordingVoice();
                    else recordingVoice();
                }
            }
        }

        /*
        else if(message.equals("DoubleClick")){
            Log.e("eleutheria", "action DoubleClick");
            if(Constants.musicPage){
                if(MusicSceneAdapter.isFirstLineDoubleChecked){
                    playMusic();
                    Log.d(TAG, "DoubleClick playMusic");
                }
                else if(MusicSceneAdapter.isSecondLineDoubleChecked){
                    nextMusic();
                    Log.d(TAG, "DoubleClick nextMusic");
                }
                else{
                    prevMusic();
                    Log.d(TAG, "DoubleClick prevMusic");
                }
            }
//            else if(Constants.clickCheck[1]){
//                if(Constants.clickCameraValue[0][1]){//CameraSceneAdapter.isFirstLineDoubleChecked){
//                    captureCamera();
//                }
//                else if(Constants.clickCameraValue[1][1]){//CameraSceneAdapter.isSecondLineDoubleChecked){
//                    videoCamera();
//                }
//                else if(Constants.clickCameraValue[2][1]){
//                    selfieCamera();
//                }
//            }
            else if(Constants.emergencyPage){
                if(EmergencySceneAdapter.isFirstLineDoubleChecked){
                    sendSMS();
                }
            }
            else if(Constants.mannermodePage){
                if(MannerModeSceneAdapter.isFirstLineDoubleChecked) {
                    setSilentMode();
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
//            else if(Constants.clickCheck[1]){
//                if(Constants.clickCameraValue[0][2]){//CameraSceneAdapter.isFirstLineHoldChecked){
//                    captureCamera();
//                }
//                else if(Constants.clickCameraValue[1][2]){//CameraSceneAdapter.isSecondLineHoldChecked){
//                    videoCamera();
//                }
//                else if(Constants.clickCameraValue[0][2]){
//                    selfieCamera();
//                }
//            }
            else if(Constants.emergencyPage){
                if(EmergencySceneAdapter.isFirstLineHoldChecked){
                    sendSMS();
                }
            }
            else if(Constants.mannermodePage){
                if(MannerModeSceneAdapter.isFirstLineHoldChecked){
                    setSilentMode();
                }
                else if(MannerModeSceneAdapter.isSecondLineHoldChecked){
                    mAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                }
                else {
                    rejectCall();
                }
            }
        }

        if(Constants.findPhonePage||Constants.clickCheck[4]){
            findPhone();
        }
        if(Constants.lightPage||Constants.clickCheck[5]){
            if(Constants.light)
                turnOffLight();
            else turnOnLight();
        }if(Constants.recordPage||Constants.clickCheck[6]){
            if(Constants.record)
                stopRecordingVoice();
            else recordingVoice();
        }
//        else if(message.equals("MusicPlayer")){
//            startMusicPlayer();
//        }
//        else if(message.equals("OpenCamera")) {
//            startCamera();
//        }else if(message.equals("CaptureCamera")){
//            captureCamera();
//        }
//        else if(message.equals("playMusic")){
//            playMusic();
//        }
//        else if(message.equals("playNext")){
//            nextMusic();
//        }
//        else if(message.equals("PlayPrev")){
//            prevMusic();
//        }
//        else if(message.equals("TurnOnLignt")){
//            turnOnLight();
//        }
//        else if(message.equals("TurnOffLignt")){
//            turnOffLight();
//        }
//        else if(message.equals("VoiceRecord")){
//            recordingVoice();
//        }
//        else if(message.equals(("VoiceRecordStop"))){
//            stopRecordingVoice();
//        }
        else{
            try{
                if(message.equals("onevibe")){
                    mVibe.vibrate(parcelArray, 1);
                }
                else if(message.equals("twovibe")){
                    mVibe.vibrate(parcelArray, 2);
                }
                else if(message.equals("threevibe")){
                    mVibe.vibrate(parcelArray, 3);
                }
                else if(message.equals("fourvibe")){
                    mVibe.vibrate(parcelArray, 4);
                }
                else if(message.equals("infinitevibe")){
                    mVibe.vibrate(parcelArray, 10);
                }
                else if(message.equals("nonevibe")){
                    mVibe.vibrate(parcelArray, 0);
                }
                else{
//                    ConnectionScene.this.findViewById(android.R.id.content).setBackgroundColor(Color.parseColor("#"+message));
                }
            }
            catch(Exception e){
            }
        }*/
    }

    //추가기능 light
    public static void turnOnLight(){
        Constants.light = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                camManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
                String cameraId = null; // Usually front camera is at 0 position.
                if (camManager != null) {
                    cameraId = camManager.getCameraIdList()[0];
                    camManager.setTorchMode(cameraId, true);
                }
            } catch (CameraAccessException e) {
                Log.e(TAG, e.toString());
            }
        } else {
            mCamera = Camera.open();
            parameters = mCamera.getParameters();
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            mCamera.setParameters(parameters);
            mCamera.startPreview();
        }
//        Camera camera = Camera.open();
//        Camera.Parameters p = camera.getParameters();
//        p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
//        camera.setParameters(p);
//        camera.startPreview();
    }
    public static void turnOffLight(){
        Constants.light = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                String cameraId;
                camManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
                if (camManager != null) {
                    cameraId = camManager.getCameraIdList()[0]; // Usually front camera is at 0 position.
                    camManager.setTorchMode(cameraId, false);
                }
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        } else {
            mCamera = Camera.open();
            parameters = mCamera.getParameters();
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            mCamera.setParameters(parameters);
            mCamera.stopPreview();
        }
//        Camera camera = Camera.open();
//        Camera.Parameters p = camera.getParameters();
//        p.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
//        camera.setParameters(p);
//        camera.stopPreview();
    }
    final private static String RECORDED_DIR = Environment.getExternalStorageDirectory().getAbsolutePath();
    final private static String RECORDED_FILE = Environment.getExternalStorageDirectory().getAbsolutePath()+"/recorded.mp4";
    //추가 녹음
    public static void recordingVoice(){
        if(mRecorder!=null){
            mRecorder.stop();
            mRecorder.release();
            mRecorder = null;
        }
        boolean isMakeDir = createRecordDir(RECORDED_DIR);

        if(isMakeDir) {
            String currentDateandTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            Log.e("eleutheria", "currentTime : " + currentDateandTime);
            Constants.record = true;
            mRecorder = new MediaRecorder();
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
//        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
//        String mFileName = context.getExternalCacheDir().getAbsolutePath();
//        mFileName += "/audiorecordtest.3gp";
//        mRecorder.setOutputFile(mFileName);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
//        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mRecorder.setOutputFile(RECORDED_DIR + "/Record_" + currentDateandTime + ".mp4");
            //mRecorder.setOutputFile(RECORDED_FILE);

            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Toast.makeText(mActivity.getApplicationContext(), "녹음을 시작합니다.",
                                Toast.LENGTH_SHORT).show();
                        mRecorder.prepare();
                        Log.e(TAG, "start() failed");
                        mRecorder.start();
                    } catch (IOException e) {
                        Log.e(TAG, "prepare() failed");
                    }
                }
            });
        } else {
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(mActivity.getApplicationContext(), "폴더 생성에 실패하였습니다.",
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private static boolean createRecordDir(String recordedDir) {
        boolean ret = true;

        File file = new File(Environment.getExternalStorageDirectory(), recordedDir);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                Log.e("TravellerLog :: ", "Problem creating Image folder");
                ret = false;
            }
        }
        return ret;
    }

    public static void stopRecordingVoice(){
        Constants.record = false;
        if(mRecorder==null) return;
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(mActivity.getApplicationContext(),"녹음이 중지되었습니다.",
                        Toast.LENGTH_SHORT).show();
            }
        });

    }
    static MediaPlayer player;
    public static void playRecordingVoice(){
        if(player!=null){
            player.stop();
            player.release();
            player = null;
        }Toast.makeText(mActivity.getApplicationContext(),"녹음된 파일을 재생합니다.",
                Toast.LENGTH_SHORT).show();
        try{
            player = new MediaPlayer();
            player.setDataSource(RECORDED_DIR);
            player.prepare();
            player.start();
        }catch (Exception e){}
    }
    public static void playStopRecordingVoice(){
        if(player==null) return;
        Toast.makeText(mActivity.getApplicationContext(),"재생을 중지합니다.",
                Toast.LENGTH_SHORT).show();
        player.stop();
        player.release();
        player=null;
    }

    //음악 플레이어 시작
    public static void startMusicPlayer() {
        Intent musicPlayerStartIntent = new Intent(MediaStore.INTENT_ACTION_MUSIC_PLAYER);
        context.startActivity(musicPlayerStartIntent);
    }

    public static void playMusic() {
        if (isPlaying == false) {
            Intent musicPlayerIntent = new Intent("com.android.music.musicservicecommand");
            musicPlayerIntent.putExtra("command", "play");
            context.sendBroadcast(musicPlayerIntent);
            isPlaying = true;
        } else {
            Intent musicPlayerIntent = new Intent("com.android.music.musicservicecommand");
            musicPlayerIntent.putExtra("command", "pause");
            context.sendBroadcast(musicPlayerIntent);
            isPlaying = false;
        }
    }

    //다음 음악 재생
    public static void nextMusic() {
        /*Intent nextMusicIntent = new Intent("com.android.music.musicservicecommand");
        nextMusicIntent.putExtra("command", "next");
        sendBroadcast(nextMusicIntent);*/
//        Constants.selfishot = true;
        Intent nextMusicIntent = new Intent("com.android.music.musicservicecommand");
        nextMusicIntent.putExtra("command", "next");
        context.sendBroadcast(nextMusicIntent);
    }

    //이전 음악 재생
    public static void prevMusic() {
        /*Intent nextMusicIntent = new Intent("com.android.music.musicservicecommand");
        nextMusicIntent.putExtra("command", "previous");
        sendBroadcast(nextMusicIntent);*/
//        rejectCall();
        Intent nextMusicIntent = new Intent("com.android.music.musicservicecommand");
        nextMusicIntent.putExtra("command", "previous");
        context.sendBroadcast(nextMusicIntent);
    }

//    private Intent cameraIntent;
//    private Camera myCamera;
//    SurfaceView view;
//    private Camera.Parameters parameters;
//    private SurfaceHolder sHolder;
    //카메라 오픈
    public static void startCamera() {
        Intent cameraIntent = new Intent(context, CameraActivity.class);
//        Constants.cameraPage = true;
//        Constants.videoPage = false;
//        Constants.selfiePage = false;
        context.startActivity(cameraIntent);
    }
//    public static void selfieCamera(){
//        Intent cameraIntent = new Intent(context, SelfieCamera.class);
////        Constants.selfiePage = true;
////        Constants.cameraPage = false;
////        Constants.videoPage = false;
//        context.startActivity(cameraIntent);
//    }
//    public static void videoCamera(){
//        Intent cameraIntent = new Intent(context, VideoActivity.class);
////        Constants.videoPage = true;
////        Constants.cameraPage = false;
////        Constants.selfiePage = false;
//        context.startActivity(cameraIntent);
//    }
    public static void captureCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "My demo image");
        myPicture = Uri.fromFile(new File(Environment.getExternalStorageDirectory()+"/imageCapyureIntent.jpg"));
        Intent captureIntent = new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
        captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, myPicture);
        context.startActivity(captureIntent);
    }


    public static void sendSMS() {
//        Log.d(TAG, "Send SMS");
//        String phoneNumber = "01063985274";//사용자 휴대폰 번호
//        String message = "위도: " + latitude + ", 경도 :" + longitude + ", 고도" + altitude;
//
//        SmsManager smsManager = SmsManager.getDefault();
//        smsManager.sendTextMessage(phoneNumber, null, message, null, null);
//        Toast.makeText(context, "위도: " + latitude + ", 경도 :" + longitude + ", 고도" + altitude, Toast.LENGTH_LONG).show();

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


    public static void rejectCall() {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
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

    public static void findPhone() {
        if (!isAudioPlay) {
            try {
                mAudio = new MediaPlayer();
                Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
                mAudio.setDataSource(context, alert);
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
    public static void setSilentMode(){
        NotificationManager n = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if(n.isNotificationPolicyAccessGranted()) {
            mAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        }else{
            Toast.makeText(context,"방해금지모드를 해제해주세요",Toast.LENGTH_SHORT).show();
        }
    }
    public void onStartButtonClicked(View v){
        connectDevice();
    }
//    int a = 0;
//    public void onTestButtonClicked(View v){
////        if(Constants.record)
////            stopRecordingVoice();
////        else recordingVoice();
//        if(a%4==0) recordingVoice();
//        if(a%4==1) stopRecordingVoice();
//        if(a%4==2) playRecordingVoice();
//        if(a%4==3) playStopRecordingVoice();
//        a++;
//    }
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
