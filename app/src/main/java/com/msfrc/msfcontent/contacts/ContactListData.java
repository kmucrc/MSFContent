package com.msfrc.msfcontent.contacts;

/**
 * Created by kmuvcl_laptop_dell on 2016-07-20.
 */
public class ContactListData {
    private String name;
    private int imgId;

    public ContactListData(String name, int imgId){
        this.name = name;
        this.imgId = imgId;
    }

    public int getImgId() {
        return imgId;
    }

    public String getName() {
        return name;
    }
}
