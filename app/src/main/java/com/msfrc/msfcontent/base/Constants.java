package com.msfrc.msfcontent.base;

/**
 * Created by kmuvcl_laptop_dell on 2016-07-05.
 */
public class Constants {
    public static long beforClickTime = 0;
    public static String beforSignal = "00";
    public static boolean light = false;
    public static boolean record = false;
    public static final int REQUEST_ENABLE_BT = 1;
    public static final int REQUEST_CONNECT_DEVICE = 2;

    public static final int MESSAGE_STATE_CHANGE = 3;
    public static final int MESSAGE_READ = 4;
    public static final int MESSAGE_WRITE = 5;
    public static final int MESSAGE_DEVICE_NAME =6;

    public static final int OPEN_PLAYER = 7;
    public static final int MUSIC_PLAY=8;
    public static final int NEXT_MUSIC=9;
    public static final int PREV_MUSIC=10;
    public static final int CAMERA_OPEN = 11;
    public static final int CAMERA_CAPTURE = 12;
    public static final String DEVICE_NAME = "device_name";

    public static final int REQUEST_IMAGE_CAPTURE = 13;
    public static final int REQUEST_CAPTURE = 14;

    public static final int REQUEST_CONTACT = 15;

    public static boolean musicPage = false;
    public static boolean cameraPage = false;
    public static boolean findPhonePage = false;
    public static boolean mannermodePage = false;
    public static boolean emergencyPage = false;
    public static boolean lightPage = false;
    public static boolean recordPage = false;

    public static boolean clickCameraPage = false;
    public static boolean cameraShot = false;
    public static boolean selfiePage = false;
    public static boolean selfishot = false;
    public static int count = 0;

    public static boolean videoShot = false;
    public static boolean videoPage = false;

    public static int index = 0;
    public static boolean isMusicSave = false;
    public static boolean isCameraSave = false;
    public static boolean isEmergencySave = false;
    public static boolean isMannermodeSave = false;

    public static boolean[] redColor = {false, false, false, false, false};
    public static boolean[] blueColor = {false, false, false, false, false};
    public static boolean[] greenColor = {false, false, false, false, false};
    public static boolean[] whiteColor = {false, false, false, false, false};
    public static boolean[] yelloColor = {false, false, false, false, false};

    public static String[] parcel ={"onevibe", "twovibe", "threevibe", "fourvibe", "infinitevibe", "nonevibe"};
    public static boolean[] parcelPosition = {false, false, false, false, false, false};

    public static boolean[] notificationCheck = {false, false, false};//phone call, text message, reminders
    public static boolean[] clickCheck = {true, false, false, false, false, false, false};//music, camera, emergency, manner mode, find phone, light, record

    public boolean isParcel = false;

    public static String basicColor = "#ff4BADAC";

    public static boolean notificationSave = false;
    public static boolean[][] isParecel = {{false, false, false, false, false, false}, {false, false, false, false, false, false}, {false, false, false, false, false, false}};
}
