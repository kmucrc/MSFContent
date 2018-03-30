package com.msfrc.msfcontent.click.emergency;

/**
 * Created by kmuvcl_laptop_dell on 2016-07-25.
 */
public class EmergencySceneData {
    private int imgId;
    private String funcName;
    public static boolean isFirst = true;
    public boolean singleClickCheck;
    public boolean doubleClickCheck;
    public boolean holdCheck;

    public EmergencySceneData(int imgId, String funcName, boolean singleClickCheck, boolean doubleClickCheck, boolean holdCheck){
        this.imgId = imgId;
        this.funcName = funcName;
        this.singleClickCheck = singleClickCheck;
        this.doubleClickCheck = doubleClickCheck;
        this.holdCheck = holdCheck;
    }

    public int getImgId() {
        return imgId;
    }

    public String getFuncName() {
        return funcName;
    }
}
