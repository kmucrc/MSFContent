package com.msfrc.msfcontent.base;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.msfrc.msfcontent.R;


public class CommonDialog extends Dialog {

    private String strMessage;
    private String strTitle;
    private String strOk;
    private String strNo;
    private int nIcon = 0;
    private int nType = Constants.COMMONDIALOG_ONEBUTTON;
    private View.OnClickListener okListener = null;
    private View.OnClickListener noListener = null;

    public CommonDialog(@NonNull Context context, int type) {
        super(context);
        this.nType = type;

    }

    public CommonDialog(Context context, int type, int themeResId) {
        super(context, themeResId);
    }

    public CommonDialog(Context context, int type, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_common);
        TextView tvTitle = (TextView) findViewById(R.id.tv_alertTitle);
        tvTitle.setCompoundDrawablesWithIntrinsicBounds(nIcon, 0, 0, 0);
        tvTitle.setText(getTitle());
        TextView tvMessage = (TextView) findViewById(R.id.tv_alertMessage);
        tvMessage.setText(getMessage());
        Button btOk = (Button) findViewById(R.id.bt_ok);
        btOk.setText(strOk);
        btOk.setOnClickListener(okListener);
        Button btNo = (Button) findViewById(R.id.bt_no);

        if(nType == Constants.COMMONDIALOG_TWOBUTTON) {
            btNo.setText(strNo);
            btNo.setOnClickListener(noListener);
        } else {
            btNo.setVisibility(View.GONE);
        }
    }

    public String getTitle() {
        return strTitle;
    }

    public void setTitle(String title) {
        this.strTitle = title;
    }

    public String getMessage() {
        return strMessage;
    }

    public void setMessage(String message) {
        this.strMessage = message;
    }

    public int getIcon() {
        return nIcon;
    }

    public void setIcon(int icon) {
        this.nIcon = icon;
    }

    public void setPositiveButton(String ok, View.OnClickListener onClickListener) {
        dismiss();
        this.strOk = ok;
        this.okListener = onClickListener;
    }

    public void setNegativeButton(String no, View.OnClickListener onClickListener) {
        dismiss();
        this.strNo = no;
        this.noListener = onClickListener;
    }
}
