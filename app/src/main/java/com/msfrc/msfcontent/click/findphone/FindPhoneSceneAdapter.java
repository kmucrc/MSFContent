package com.msfrc.msfcontent.click.findphone;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.msfrc.msfcontent.R;
import com.msfrc.msfcontent.base.Constants;

import java.util.ArrayList;

/**
 * Created by kmuvcl_laptop_dell on 2016-07-25.
 */
public class FindPhoneSceneAdapter extends BaseAdapter{
    private ArrayList<FindPhoneSceneData> findPhoneMember;
    private LayoutInflater inflater;
    private RadioButton singleButton;
    private RadioButton doubleButton;
    private RadioButton holdButton;
    public FindPhoneSceneAdapter(LayoutInflater inflater, ArrayList<FindPhoneSceneData> findPhoneMember){
        this.inflater= inflater;
        this.findPhoneMember = findPhoneMember;
    }
    @Override
    public int getCount() {
        return findPhoneMember.size();
    }

    @Override
    public Object getItem(int position) {
        return findPhoneMember.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView== null){
            convertView = inflater.inflate(R.layout.music_row, null);
        }
        ImageView mImageView = (ImageView)convertView.findViewById(R.id.musicimage);
        TextView mTextView = (TextView)convertView.findViewById(R.id.musictext);

        mImageView.setImageResource(findPhoneMember.get(position).getImgId());
        mTextView.setText(findPhoneMember.get(position).getFuncName());
        singleButton = (RadioButton)convertView.findViewById(R.id.click1check);
        doubleButton = (RadioButton)convertView.findViewById(R.id.click2check);
        holdButton = (RadioButton)convertView.findViewById(R.id.click3check);
        singleButton.setChecked(findPhoneMember.get(position).singleClickCheck);
        doubleButton.setChecked(findPhoneMember.get(position).doubleClickCheck);
        holdButton.setChecked(findPhoneMember.get(position).holdCheck);
        singleButton.setTag(position);
        doubleButton.setTag(position);
        holdButton.setTag(position);
        singleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int)v.getTag();
                switch(position) {
                    case 0:
                        Constants.findphoneFindPhone = Constants.CLICK_SINGLE;
                        break;
                }
            }
        });
        doubleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int)v.getTag();
                switch(position) {
                    case 0:
                        Constants.findphoneFindPhone = Constants.CLICK_DOUBLE;
                        break;
                }
            }
        });
        holdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int)v.getTag();
                switch(position) {
                    case 0:
                        Constants.findphoneFindPhone = Constants.CLICK_HOLD;
                        break;
                }
            }
        });
        return convertView;
    }
}
