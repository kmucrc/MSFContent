package com.msfrc.msfcontent.click.camera;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.msfrc.msfcontent.R;
import com.msfrc.msfcontent.base.Constants;
import com.msfrc.msfcontent.click.music.MusicSceneData;

import java.util.ArrayList;

/**
 * Created by kmuvcl_laptop_dell on 2016-07-21.
 */
public class CameraSceneAdapter extends BaseAdapter{
    private ArrayList<CameraSceneData> cameraMember;
    private LayoutInflater inflater;
    private RadioButton singleButton;
    private RadioButton doubleButton;
    private RadioButton holdButton;
    public CameraSceneAdapter(LayoutInflater inflater, ArrayList<CameraSceneData> cameraMember){
        this.inflater= inflater;
        this.cameraMember = cameraMember;
    }
    @Override
    public int getCount() {
        return cameraMember.size();
    }

    @Override
    public Object getItem(int position) {
        return cameraMember.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView== null){
            convertView = inflater.inflate(R.layout.camera_row, null);
        }
        ImageView mImageView = (ImageView)convertView.findViewById(R.id.musicimage);
        TextView mTextView = (TextView)convertView.findViewById(R.id.musictext);

        mImageView.setImageResource(cameraMember.get(position).getImgId());
        mTextView.setText(cameraMember.get(position).getFuncName());
        singleButton = (RadioButton)convertView.findViewById(R.id.click1check);
        doubleButton = (RadioButton)convertView.findViewById(R.id.click2check);
        holdButton = (RadioButton)convertView.findViewById(R.id.click3check);
        singleButton.setChecked(cameraMember.get(position).singleClickCheck);
        doubleButton.setChecked(cameraMember.get(position).doubleClickCheck);
        holdButton.setChecked(cameraMember.get(position).holdCheck);
        singleButton.setTag(position);
        doubleButton.setTag(position);
        holdButton.setTag(position);
        singleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int)v.getTag();
                switch(position) {
                    case 0:
                        Constants.cameraCamera = Constants.CLICK_SINGLE;
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
                        Constants.cameraCamera = Constants.CLICK_DOUBLE;
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
                        Constants.cameraCamera = Constants.CLICK_HOLD;
                        break;
                }
            }
        });
        return convertView;
    }
}
