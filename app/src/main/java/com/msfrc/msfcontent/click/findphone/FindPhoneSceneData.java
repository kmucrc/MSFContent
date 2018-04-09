package com.msfrc.msfcontent.click.findphone;

/**
 * Created by kmuvcl_laptop_dell on 2016-07-25.
 */
public class FindPhoneSceneData {
    private int imgId;
    private String funcName;
    public boolean clickCheck;
    public FindPhoneSceneData(int imgId, String funcName, boolean clickCheck){
        this.imgId = imgId;
        this.funcName = funcName;
        this.clickCheck = clickCheck;
    }

    public int getImgId() {
        return imgId;
    }

    public String getFuncName() {
        return funcName;
    }
}
