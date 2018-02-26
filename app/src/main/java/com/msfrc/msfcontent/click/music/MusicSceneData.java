package com.msfrc.msfcontent.click.music;

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

    public MusicSceneData(int imgId, String funcName, boolean singleClickCheck, boolean doubleClickCheck, boolean holdCheck){
        this.imgId = imgId;
        this.funcName = funcName;
        this.singleClickCheck = singleClickCheck;
        this.doubleClickCheck = doubleClickCheck;
        this.holdCheck = holdCheck;
        isFirst = false;
    }

    public int getImgId() {
        return imgId;
    }

    public String getFuncName() {
        return funcName;
    }
}
