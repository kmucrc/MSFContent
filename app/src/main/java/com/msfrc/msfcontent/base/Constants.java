package com.msfrc.msfcontent.base;

import java.util.ArrayList;

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
    public static final String REMINDER_ACTION_NAME = "android.intent.action.EVENT_REMINDER";

    // MAINMENU INDEX
    public static final int MAIN_MENU_CONNECTION = 0;
    public static final int MAIN_MENU_NOTIFICATIONS = 1;
//    public static final int MAIN_MENU_CONTACTS = 2;
    public static final int MAIN_MENU_CLICK = 2;
    public static final int MAIN_MENU_COLOR_PICKERS = 3;
    public static final int MAIN_MENU_PREFERENCES = 4;
    public static final int MAIN_MENU_HELP_REVIEW =5;

    // CLICK INDEX
    public static final int CLICK_MUSIC_PLAY = 0;
    public static final int CLICK_CAMERA = 1;
    public static final int CLICK_EMERGENCY = 2;
    public static final int CLICK_MANNER_MODE = 3;
    public static final int CLICK_FIND_PHONE = 4;
    public static final int CLICK_LIGHT_CONTROL = 5;
    public static final int CLICK_RECORD_VOICE =6;
    public static int clickIndex = CLICK_MUSIC_PLAY;

    public static final int CLICK_SINGLE = 0;
    public static final int CLICK_DOUBLE = 1;
    public static final int CLICK_HOLD = 2;

    public static int mannermodeSilent = CLICK_SINGLE;
    public static int mannermodeSound = CLICK_DOUBLE;
    public static int mannermodeRejectCall = CLICK_HOLD;
    public static int cameraCamera = CLICK_SINGLE;
    public static int findphoneFindPhone = CLICK_SINGLE;
    public static int lightLight = CLICK_SINGLE;
    public static int voicerecordRecord = CLICK_SINGLE;
    public static int musicPlay = CLICK_SINGLE;
    public static int musicForward = CLICK_DOUBLE;
    public static int musicReverse = CLICK_HOLD;
    public static int emergencySendLocation = CLICK_SINGLE;

    public static int COMMONDIALOG_ONEBUTTON = 1;
    public static int COMMONDIALOG_TWOBUTTON = 2;

    public static int EMERGENCY_CONTACT_LIMIT = 5;

    public static ArrayList<String> listPhoneNumber = new ArrayList<String>();

    // COLOR INDEX
    public static final int COLOR_WHITE = 0;
    public static final int COLOR_RED = 1;
    public static final int COLOR_GREEN = 2;
    public static final int COLOR_YELLOW = 3;
    public static final int COLOR_BLUE = 4;

    public static int notiCallColor = COLOR_WHITE;
    public static int notiSMSColor = COLOR_WHITE;
    public static int notiReminderColor = COLOR_WHITE;

    public static boolean notiCallCheck = false;
    public static boolean notiSMSCheck = false;
    public static boolean notiReminderCheck = false;



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

    public static boolean isEmergencySave = false;

    public static boolean[] redColor = {false, false, false, false, false};
    public static boolean[] blueColor = {false, false, false, false, false};
    public static boolean[] greenColor = {false, false, false, false, false};
    public static boolean[] whiteColor = {false, false, false, false, false};
    public static boolean[] yelloColor = {false, false, false, false, false};

    public static String[] parcel ={"onevibe", "twovibe", "threevibe", "fourvibe", "infinitevibe", "nonevibe"};
    public static boolean[] parcelPosition = {false, false, false, false, false, false};

    public static boolean[] notificationCheck = {false, false, false};//phone call, text message, reminders
    public static boolean[] clickCheck = {true, false, false, false, false, false, false};//music, camera, emergency, manner mode, find phone, light, record

//    public static int[] clickMusicValue = {0, 1, 2};
//    public static int[] clickCameraValue = {0, 1, 2};//click=0, doubleClick=1, hold=2
//    public static int[] clickMannerModeValue = {0, 1, 2};//music, camera, manner mode
    public static boolean[][] clickCameraValue={{true, false, false},{false, true, false}, {false, false, true}};
    public boolean isParcel = false;

    public static String basicColor = "#ff4BADAC";

    public static boolean notificationSave = false;
    public static boolean[][] isParecel = {{false, false, false, false, false, false}, {false, false, false, false, false, false}, {false, false, false, false, false, false}};
}
