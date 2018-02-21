package com.msfrc.msfcontent.notification;

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

/**
 * Created by kmuvcl_laptop_dell on 2016-07-20.
 */
public class NotificationAdapter extends BaseAdapter{

    private ArrayList<NotificationListData> notificationMember;
    private LayoutInflater inflater;

    public NotificationAdapter(LayoutInflater inflater, ArrayList<NotificationListData> notificationMember){
        this.inflater= inflater;
        this.notificationMember = notificationMember;
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

        checkBox.setChecked(Constants.notificationCheck[position]);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked )
                {
                    Constants.notificationCheck[position] = true;
                }else Constants.notificationCheck[position] = false;

            }
        });

        return convertView;
    }
}
