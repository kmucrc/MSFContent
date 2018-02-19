package com.msfrc.msfcontent.notification;

/**
 * Created by kmuvcl_laptop_dell on 2016-08-31.
 */
public class NotificationColorData {
    private int imgId;
    private int vibeimgId;

    public NotificationColorData(int imgId, int vibeimgId){
        this.imgId= imgId;
        this.vibeimgId = vibeimgId;
    }

    public int getImgId() {
        return imgId;
    }

    public int getVibeimgId() {
        return vibeimgId;
    }
}
