package com.msfrc.msfcontent.base;

/**
 * Created by kmuvcl_laptop_dell on 2016-07-25.
 */
public class FirstRow {
    private int clickImgId;
    private String singleClick;
    private String doubleClick;
    private String hold;

    public FirstRow(int clickImgId, String hold){
        this.clickImgId = clickImgId;
        this.hold = hold;
    }
    public FirstRow(int clickImgId, String singleClick, String doubleClick, String hold){
        this.clickImgId = clickImgId;
        this.singleClick = singleClick;
        this.doubleClick = doubleClick;
        this.hold = hold;
    }

    public int getClickImgId() {
        return clickImgId;
    }

    public String getSingleClick() {
        return singleClick;
    }

    public String getDoubleClick() {
        return doubleClick;
    }

    public String getHold() {
        return hold;
    }
}
