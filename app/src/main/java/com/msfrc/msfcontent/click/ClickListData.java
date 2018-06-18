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
    private boolean checked = false;
    public void setChecked(boolean checked){
        this.checked = checked;
    }
    public boolean isChecked(){
        return checked;
    }

    public int getImgId() {
        return imgId;
    }

    public String getFunctionName() {
        return functionName;
    }
}
