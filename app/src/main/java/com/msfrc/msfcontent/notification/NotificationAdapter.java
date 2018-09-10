package com.msfrc.msfcontent.notification;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.msfrc.msfcontent.R;
import com.msfrc.msfcontent.base.Constants;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by kmuvcl_laptop_dell on 2016-07-20.
 */
public class NotificationAdapter extends BaseAdapter{

    private ArrayList<NotificationListData> notificationMember;
    private LayoutInflater inflater;
    private Context mContext;
    private SharedPreferences settings;

    public NotificationAdapter(LayoutInflater inflater, ArrayList<NotificationListData> notificationMember, Context context){
        this.inflater= inflater;
        this.notificationMember = notificationMember;
        this.mContext = context;
    }
    @Override
    public int getCount() {
        return notificationMember.size();
    }

    @Override
    public Object getItem(int position) {
       return notificationMember.get(position);
    }

    @Override
    public long getItemId(int position) {
       return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView== null){
            convertView = inflater.inflate(R.layout.notification_row, null);
        }
        ImageView mImageView = (ImageView)convertView.findViewById(R.id.image);
        TextView mTextView = (TextView)convertView.findViewById(R.id.label);
        CheckBox checkBox = (CheckBox)convertView.findViewById(R.id.selected);

        mImageView.setImageResource(notificationMember.get(position).getImgId());
        mTextView.setText(notificationMember.get(position).getFunctionName());

//        checkBox.setChecked(Constants.notificationCheck[position]);
        switch (position) {
            case 0:
                checkBox.setChecked(Constants.notiCallCheck);
                break;
            case 1:
                checkBox.setChecked(Constants.notiSMSCheck);
                break;
            case 2:
                checkBox.setChecked(Constants.notiReminderCheck);
                break;
        }

        Activity act = (Activity) mContext;
        settings = act.getSharedPreferences("setupdData", mContext.MODE_PRIVATE);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                SharedPreferences.Editor editor = settings.edit();

                if ( isChecked ) {
                    switch (position) {
                        case 0:
                            Constants.notiCallCheck = true;
                            editor.putBoolean("notiCallCheck", Constants.notiCallCheck);
                            break;
                        case 1:
                            Constants.notiSMSCheck = true;
                            editor.putBoolean("notiSMSCheck", Constants.notiSMSCheck);
                            break;
                        case 2:
                            Constants.notiReminderCheck = true;
                            editor.putBoolean("notiReminderCheck", Constants.notiReminderCheck);
                            break;
                    }
                } else {
                    switch (position) {
                        case 0:
                            Constants.notiCallCheck = false;
                            editor.putBoolean("notiCallCheck", Constants.notiCallCheck);
                            break;
                        case 1:
                            Constants.notiSMSCheck = false;
                            editor.putBoolean("notiSMSCheck", Constants.notiSMSCheck);
                            break;
                        case 2:
                            Constants.notiReminderCheck = false;
                            editor.putBoolean("notiReminderCheck", Constants.notiReminderCheck);
                            break;
                    }
                }
                editor.commit();
            }
        });

        return convertView;
    }
}
