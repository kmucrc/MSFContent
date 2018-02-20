package com.msfrc.msfcontent.preference;

/**
 * Created by kmuvcl_laptop_dell on 2016-07-20.
 */
public class PreferenceListData {
    private int imgId;
    private String optionName;

    public PreferenceListData(int imgId, String optionName){
        this.imgId = imgId;
        this.optionName = optionName;
    }
    public int getImgId() {
        return imgId;
    }
    public String getOptionName() {
        return optionName;
    }
}
