package com.msfrc.msfcontent.notification;

/**
 * Created by kmuvcl_laptop_dell on 2016-07-21.
 */
public class NotificationEditData {
    private int imgId;
    private String functionName;
    private int colorimgId;
    private int vibeimgId;
    private int textColor;
    private int backgroundColor;
    public NotificationEditData(int imgId, String functionName, int colorimgId, int vibeimgId, int textColor, int backgroundColor){
        this.imgId= imgId;
        this.functionName = functionName;
        this.colorimgId = colorimgId;
        this.vibeimgId = vibeimgId;
        this.textColor = textColor;
        this.backgroundColor = backgroundColor;
    }

    public int getImgId() {
        return imgId;
    }

    public String getFunctionName() {
        return functionName;
    }

    public int getColorimgId() {
        return colorimgId;
    }

    public int getVibeimgId() {
        return vibeimgId;
    }

    public int getTextxColor() { return textColor; }

    public int getBackgroundColor() { return backgroundColor;}
}
