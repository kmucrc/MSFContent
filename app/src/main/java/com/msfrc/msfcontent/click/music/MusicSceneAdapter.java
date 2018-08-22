package com.msfrc.msfcontent.click.music;

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
 * Created by kmuvcl_laptop_dell on 2016-07-21.
 */
public class MusicSceneAdapter extends BaseAdapter{
    private ArrayList<MusicSceneData> musicMember;
    private LayoutInflater inflater;
    public static RadioButton singleButton;
    public static RadioButton doubleButton;
    public static RadioButton holdButton;
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
                        Constants.musicPlay = Constants.CLICK_SINGLE;
                        break;
                    case 1:
                        Constants.musicForward = Constants.CLICK_SINGLE;
                        break;
                    case 2:
                        Constants.musicReverse = Constants.CLICK_SINGLE;
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
                        Constants.musicPlay = Constants.CLICK_DOUBLE;
                        break;
                    case 1:
                        Constants.musicForward = Constants.CLICK_DOUBLE;
                        break;
                    case 2:
                        Constants.musicReverse = Constants.CLICK_DOUBLE;
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
                        Constants.musicPlay = Constants.CLICK_HOLD;
                        break;
                    case 1:
                        Constants.musicForward = Constants.CLICK_HOLD;
                        break;
                    case 2:
                        Constants.musicReverse = Constants.CLICK_HOLD;
                        break;
                }
            }
        });
        return convertView;
    }
}
