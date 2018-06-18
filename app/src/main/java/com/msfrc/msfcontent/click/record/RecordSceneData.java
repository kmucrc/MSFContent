package com.msfrc.msfcontent.click.record;

/**
 * Created by kmuvcl_laptop_dell on 2016-07-25.
 */
public class RecordSceneData {
    private int imgId;
    private String funcName;
    public boolean clickCheck;
    public RecordSceneData(int imgId, String funcName, boolean clickCheck){
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
