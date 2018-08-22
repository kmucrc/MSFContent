package com.msfrc.msfcontent.click.findphone;

import com.msfrc.msfcontent.base.Constants;

/**
 * Created by kmuvcl_laptop_dell on 2016-07-25.
 */
public class FindPhoneSceneData {
    private int imgId;
    private String funcName;
    public boolean singleClickCheck;
    public boolean doubleClickCheck;
    public boolean holdCheck;
    public FindPhoneSceneData(int imgId, String funcName, int checked){
        this.imgId = imgId;
        this.funcName = funcName;
        this.singleClickCheck = false;
        this.doubleClickCheck = false;
        this.holdCheck = false;

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
