package com.msfrc.msfcontent.base;

public class CommonUtil {

    public boolean getDuplicatesCheck(int nFirst, int nSecond, int nThird) {
        boolean bResult = false;

        if((nFirst != nSecond) && (nSecond != nThird) && (nThird != nFirst)) {
            bResult = true;
        }

        return bResult;
    }

}
