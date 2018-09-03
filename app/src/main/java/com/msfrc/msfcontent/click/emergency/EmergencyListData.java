package com.msfrc.msfcontent.click.emergency;

public class EmergencyListData {
    private String strName;
    private String strNumber;

    public EmergencyListData(String name, String number) {
        this.strName = name;
        this.strNumber = number;
    }

    public String getName() {
        return this.strName;
    }

    public String getNumber() {
        return this.strNumber;
    }
}
