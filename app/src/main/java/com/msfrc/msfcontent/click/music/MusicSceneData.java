package com.msfrc.msfcontent.click.music;

import com.msfrc.msfcontent.base.Constants;

/**
 * Created by kmuvcl_laptop_dell on 2016-07-21.
 */
public class MusicSceneData {
    private int imgId;
    private String funcName;
    public static boolean isFirst = true;
    public boolean singleClickCheck;
    public boolean doubleClickCheck;
    public boolean holdCheck;

    public MusicSceneData(int imgId, String funcName, int checked){
        this.imgId = imgId;
        this.funcName = funcName;
        this.singleClickCheck = false;
        this.doubleClickCheck = false;
        this.holdCheck = false;
        isFirst = false;

        switch (checked) {
            case Constants.CLICK_SINGLE : {
                this.singleClickCheck = true;
                break;
            }
            case Constants.CLICK_DOUBLE: {
                this.doubleClickCheck = true;
                break;
            }
            case Constants.CLICK_HOLD: {
                this.holdCheck = true;
                break;
            }
            default:
                this.singleClickCheck = true;
                break;

        }
    }

    public int getImgId() {
        return imgId;
    }

    public String getFuncName() {
        return funcName;
    }
}
