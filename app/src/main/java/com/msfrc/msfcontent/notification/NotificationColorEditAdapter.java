package com.msfrc.msfcontent.notification;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.msfrc.msfcontent.R;

import java.util.ArrayList;

/**
 * Created by kmuvcl_laptop_dell on 2016-08-31.
 */
public class NotificationColorEditAdapter extends BaseAdapter{

    private ArrayList<NotificationColorData> notificationMember;
    private LayoutInflater inflater;

    public NotificationColorEditAdapter(LayoutInflater inflater, ArrayList<NotificationColorData> notificationMember){
        this.inflater= inflater;
        this.notificationMember = notificationMember;
    }
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = inflater.inflate(R.layout.notification_edit_color, null);
        }
        ImageView mImageView = (ImageView)convertView.findViewById(R.id.image);
        ImageButton redButton = (ImageButton)convertView.findViewById(R.id.redButton);
        ImageButton yellowButton = (ImageButton)convertView.findViewById(R.id.yellowButton);
        ImageButton blueButton = (ImageButton)convertView.findViewById(R.id.blueButton);
        ImageButton whiteButton = (ImageButton)convertView.findViewById(R.id.whiteButton);
        ImageButton greenButton = (ImageButton)convertView.findViewById(R.id.greenButton);
        ImageButton vibeButton = (ImageButton)convertView.findViewById(R.id.vibebutton);

        mImageView.setImageResource(notificationMember.get(position).getImgId());
        vibeButton.setImageResource(notificationMember.get(position).getVibeimgId());
        return convertView;
    }
}
