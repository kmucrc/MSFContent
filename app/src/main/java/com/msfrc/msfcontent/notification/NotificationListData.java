package com.msfrc.msfcontent.notification;

/**
 * Created by kmuvcl_laptop_dell on 2016-07-20.
 */
public class NotificationListData {
    private int imgId;
    private String functionName;
    private Boolean check;

    public NotificationListData(int imgId, String functionName, boolean check){
        this.imgId= imgId;
        this.functionName = functionName;
        this.check = check;
    }

    public int getImgId() {
        return imgId;
    }

    public String getFunctionName() {
        return functionName;
    }

    public boolean getCheck(){ return check;}
}
