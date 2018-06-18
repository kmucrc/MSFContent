package com.msfrc.msfcontent.click;

import android.content.Context;
import android.util.Log;
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
public class ClickAdapter extends BaseAdapter{
    private ArrayList<ClickListData> clickMember;
    private LayoutInflater inflater;
    private static final String TAG = "ClickAdapter";
    public ClickAdapter(LayoutInflater inflater, ArrayList<ClickListData> ClickMember){
        this.inflater= inflater;
        this.clickMember = ClickMember;
    }
    @Override
    public int getCount() {
        return clickMember.size();
    }

    @Override
    public Object getItem(int position) {
        return clickMember.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private CheckBox mCheckBox;
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();
        if(convertView== null){
            convertView = inflater.inflate(R.layout.notification_row, null);
        }
        ImageView mImageView = (ImageView)convertView.findViewById(R.id.image);
        TextView mTextView = (TextView)convertView.findViewById(R.id.label);
        mCheckBox = (CheckBox)convertView.findViewById(R.id.selected);
        mImageView.setImageResource(clickMember.get(position).getImgId());
        mTextView.setText(clickMember.get(position).getFunctionName());
        mCheckBox.setChecked(Constants.clickCheck[position]);
        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked )
                {
                    for (int i = 0;i<7;i++){
                        Constants.clickCheck[i] = false;
                        clickMember.get(i).setChecked(false);
                    }
                    Constants.clickCheck[position] = true;
                    clickMember.get(position).setChecked(true);
                    notifyDataSetChanged();
                }else{
                    Constants.clickCheck[position] = false;
                    clickMember.get(position).setChecked(false);
                }
                Log.d("check", "position:"+position+", isChecked:"+isChecked);
                switch(position){
                    case 0:
                        if(Constants.clickCheck[position]) {
                            Constants.musicPage = true;
                            Log.d(TAG, ""+Constants.musicPage);
                        }
                        else{
                            Constants.musicPage = false;
                            Log.d(TAG, ""+Constants.musicPage);
                        }
                        break;
                    case 1:
                        if(Constants.clickCheck[position]) {
                            Constants.clickCameraPage = true;
                        }
                        else{
                            Constants.clickCameraPage = false;
                        }
                        break;
                    case 2:
                        if(Constants.clickCheck[position]) {
                            Constants.emergencyPage = true;
                        }
                        else{
                            Constants.emergencyPage = false;
                        }
                        break;
                    case 3:
                        if(Constants.clickCheck[position]) {
                            Constants.mannermodePage = true;
                        }
                        else{
                            Constants.mannermodePage = false;
                        }
                        break;
                    case 4:
                        if(Constants.clickCheck[position]) {
                            Constants.findPhonePage = true;
                        }
                        else{
                            Constants.findPhonePage = false;
                        }
                        break;
                    case 5:
                        if(Constants.clickCheck[position]) {
                            Constants.lightPage = true;
                        }
                        else{
                            Constants.lightPage = false;
                        }
                        break;
                    case 6:
                        if(Constants.clickCheck[position]) {
                            Constants.recordPage = true;
                        }
                        else{
                            Constants.recordPage = false;
                        }
                        break;
                }
            }
        });
        return convertView;
    }
}
