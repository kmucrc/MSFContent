package com.msfrc.msfcontent.click;

/**
 * Created by kmuvcl_laptop_dell on 2016-07-20.
 */
public class ClickListData {
    private int imgId;
    private String functionName;
    public ClickListData(int imgId, String functionName){
        this.imgId= imgId;
        this.functionName = functionName;
    }

    public int getImgId() {
        return imgId;
    }

    public String getFunctionName() {
        return functionName;
    }
}
