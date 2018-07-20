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
//    public static boolean isFirstLineSingleChecked=true;
//    public static boolean isSecondLineSingleChecked=false;
//    public static boolean isThirdLineSingleChecked=false;
//    public static boolean isFirstLineDoubleChecked=false;
//    public static boolean isSecondLineDoubleChecked=true;
//    public static boolean isThirdLineDoubleChecked=false;
//    public static boolean isFirstLineHoldChecked=false;
//    public static boolean isSecondLineHoldChecked=false;
//    public static boolean isThirdLineHoldChecked=true;
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
//                        isFirstLineSingleChecked = true;
//                        isFirstLineDoubleChecked = false;
//                        isFirstLineHoldChecked = false;
                        Constants.clickCameraValue[0][0]=true;
                        Constants.clickCameraValue[0][1]=false;
                        Constants.clickCameraValue[0][2]=false;
                        break;
                    case 1:
//                        isSecondLineSingleChecked = true;
//                        isSecondLineDoubleChecked = false;
//                        isSecondLineHoldChecked = false;
                        Constants.clickCameraValue[1][0]=true;
                        Constants.clickCameraValue[1][1]=false;
                        Constants.clickCameraValue[1][2]=false;
                        break;
                    case 2:
//                        isThirdLineSingleChecked = true;
//                        isThirdLineDoubleChecked = false;
//                        isThirdLineHoldChecked = false;
                        Constants.clickCameraValue[2][0]=true;
                        Constants.clickCameraValue[2][1]=false;
                        Constants.clickCameraValue[2][2]=false;
                }
            }
        });
        doubleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int)v.getTag();
                switch(position) {
                    case 0:
//                        isFirstLineDoubleChecked = true;
//                        isFirstLineSingleChecked = false;
//                        isFirstLineHoldChecked = false;
                        Constants.clickCameraValue[0][0]=false;
                        Constants.clickCameraValue[0][1]=true;
                        Constants.clickCameraValue[0][2]=false;
                        break;
                    case 1:
//                        isSecondLineDoubleChecked = true;
//                        isSecondLineSingleChecked = false;
//                        isSecondLineHoldChecked = false;
                        Constants.clickCameraValue[1][0]=false;
                        Constants.clickCameraValue[1][1]=true;
                        Constants.clickCameraValue[1][2]=false;
                        break;
                    case 2:
//                        isThirdLineDoubleChecked = true;
//                        isThirdLineSingleChecked = false;
//                        isThirdLineHoldChecked = false;
                        Constants.clickCameraValue[2][0]=false;
                        Constants.clickCameraValue[2][1]=true;
                        Constants.clickCameraValue[2][2]=false;
                }
            }
        });
        holdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int)v.getTag();
                switch(position) {
                    case 0:
//                        isFirstLineHoldChecked = true;
//                        isFirstLineDoubleChecked = false;
//                        isFirstLineSingleChecked = false;
                        Constants.clickCameraValue[0][0]=false;
                        Constants.clickCameraValue[0][1]=false;
                        Constants.clickCameraValue[0][2]=true;
                        break;
                    case 1:
//                        isSecondLineHoldChecked = true;
//                        isSecondLineDoubleChecked = false;
//                        isSecondLineSingleChecked = false;
                        Constants.clickCameraValue[1][0]=false;
                        Constants.clickCameraValue[1][1]=false;
                        Constants.clickCameraValue[1][2]=true;
                        break;
                    case 2:
//                        isThirdLineHoldChecked = true;
//                        isThirdLineSingleChecked = false;
//                        isThirdLineDoubleChecked = false;
                        Constants.clickCameraValue[2][0]=false;
                        Constants.clickCameraValue[2][1]=false;
                        Constants.clickCameraValue[2][2]=true;
                }
            }
        });
        return convertView;
    }
}
