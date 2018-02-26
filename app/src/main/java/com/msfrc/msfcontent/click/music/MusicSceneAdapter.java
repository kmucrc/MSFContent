package com.msfrc.msfcontent.click.music;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.msfrc.msfcontent.R;

import java.util.ArrayList;

/**
 * Created by kmuvcl_laptop_dell on 2016-07-21.
 */
public class MusicSceneAdapter extends BaseAdapter{
    private ArrayList<MusicSceneData> musicMember;
    private LayoutInflater inflater;
    public static RadioButton singleButton;
    public static RadioButton doubleButton;
    public static RadioButton holdButton;
    public static boolean isFirstLineSingleChecked=true;
    public static boolean isSecondLineSingleChecked=false;
    public static boolean isThirdLineSingleChecked=false;
    public static boolean isFirstLineDoubleChecked=false;
    public static boolean isSecondLineDoubleChecked=true;
    public static boolean isThirdLineDoubleChecked=false;
    public static boolean isFirstLineHoldChecked=false;
    public static boolean isSecondLineHoldChecked=false;
    public static boolean isThirdLineHoldChecked=true;
    public MusicSceneAdapter(LayoutInflater inflater, ArrayList<MusicSceneData> musicMember){
        this.inflater= inflater;
        this.musicMember = musicMember;
    }
    @Override
    public int getCount() {
        return musicMember.size();
    }

    @Override
    public Object getItem(int position) {
        return musicMember.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.music_row, null);
        }
        ImageView mImageView = (ImageView) convertView.findViewById(R.id.musicimage);
        TextView mTextView = (TextView) convertView.findViewById(R.id.musictext);
        mImageView.setImageResource(musicMember.get(position).getImgId());
        mTextView.setText(musicMember.get(position).getFuncName());
        singleButton = (RadioButton)convertView.findViewById(R.id.click1check);
        doubleButton = (RadioButton)convertView.findViewById(R.id.click2check);
        holdButton = (RadioButton)convertView.findViewById(R.id.click3check);
        singleButton.setChecked(musicMember.get(position).singleClickCheck);
        doubleButton.setChecked(musicMember.get(position).doubleClickCheck);
        holdButton.setChecked(musicMember.get(position).holdCheck);
        singleButton.setTag(position);
        doubleButton.setTag(position);
        holdButton.setTag(position);
        singleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int)v.getTag();
                switch(position) {
                    case 0:
                        isFirstLineSingleChecked = true;
                        isFirstLineDoubleChecked = false;
                        isFirstLineHoldChecked = false;
                        break;
                    case 1:
                        isSecondLineSingleChecked = true;
                        isSecondLineDoubleChecked = false;
                        isSecondLineHoldChecked = false;
                        break;
                    case 2:
                        isThirdLineSingleChecked = true;
                        isThirdLineDoubleChecked = false;
                        isThirdLineHoldChecked = false;
                }
            }
        });
        doubleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int)v.getTag();
                switch(position) {
                    case 0:
                        isFirstLineDoubleChecked = true;
                        isFirstLineSingleChecked = false;
                        isFirstLineHoldChecked = false;
                        break;
                    case 1:
                        isSecondLineDoubleChecked = true;
                        isSecondLineSingleChecked = false;
                        isSecondLineHoldChecked = false;
                        break;
                    case 2:
                        isThirdLineDoubleChecked = true;
                        isThirdLineSingleChecked = false;
                        isThirdLineHoldChecked = false;
                }
            }
        });
        holdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int)v.getTag();
                switch(position) {
                    case 0:
                        isFirstLineHoldChecked = true;
                        isFirstLineDoubleChecked = false;
                        isFirstLineSingleChecked = false;
                        break;
                    case 1:
                        isSecondLineHoldChecked = true;
                        isSecondLineDoubleChecked = false;
                        isSecondLineSingleChecked = false;
                        break;
                    case 2:
                        isThirdLineHoldChecked = true;
                        isThirdLineSingleChecked = false;
                        isThirdLineDoubleChecked = false;
                }
            }
        });
        return convertView;
    }
}
