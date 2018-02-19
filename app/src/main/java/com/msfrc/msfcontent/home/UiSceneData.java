package com.msfrc.msfcontent.home;

/**
 * Created by kmuvcl_laptop_dell on 2016-07-25.
 */
public class UiSceneData {
    private int imgId;
    private String functionName;

    public UiSceneData(int imgId, String functionName){
        this.imgId = imgId;
        this.functionName = functionName;
    }

    public int getImgId() {
        return imgId;
    }

    public String getFunctionName() {
        return functionName;
    }
}
