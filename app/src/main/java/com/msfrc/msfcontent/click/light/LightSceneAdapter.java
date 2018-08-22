package com.msfrc.msfcontent.click.light;

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
public class LightSceneAdapter extends BaseAdapter{
    private ArrayList<LightSceneData> lightMember;
    private LayoutInflater inflater;
    private RadioButton singleButton;
    private RadioButton doubleButton;
    private RadioButton holdButton;
    public LightSceneAdapter(LayoutInflater inflater, ArrayList<LightSceneData> lightMember){
        this.inflater= inflater;
        this.lightMember = lightMember;
    }
    @Override
    public int getCount() {
        return lightMember.size();
    }

    @Override
    public Object getItem(int position) {
        return lightMember.get(position);
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

        mImageView.setImageResource(lightMember.get(position).getImgId());
        mTextView.setText(lightMember.get(position).getFuncName());
        singleButton = (RadioButton)convertView.findViewById(R.id.click1check);
        doubleButton = (RadioButton)convertView.findViewById(R.id.click2check);
        holdButton = (RadioButton)convertView.findViewById(R.id.click3check);
        singleButton.setChecked(lightMember.get(position).singleClickCheck);
        doubleButton.setChecked(lightMember.get(position).doubleClickCheck);
        holdButton.setChecked(lightMember.get(position).holdCheck);
        singleButton.setTag(position);
        doubleButton.setTag(position);
        holdButton.setTag(position);
        singleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int)v.getTag();
                switch(position) {
                    case 0:
                        Constants.lightLight = Constants.CLICK_SINGLE;
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
                        Constants.lightLight = Constants.CLICK_DOUBLE;
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
                        Constants.lightLight = Constants.CLICK_HOLD;
                        break;
                }
            }
        });
        return convertView;
    }
}
