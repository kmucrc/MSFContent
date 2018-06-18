package com.msfrc.msfcontent.click.light;

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
 * Created by kmuvcl_laptop_dell on 2016-07-25.
 */
public class LightSceneAdapter extends BaseAdapter{
    private ArrayList<LightSceneData> lightMember;
    private LayoutInflater inflater;
    private RadioButton clickButton;
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
            convertView = inflater.inflate(R.layout.findphone_row, null);
        }
        ImageView mImageView = (ImageView)convertView.findViewById(R.id.image);
        TextView mTextView = (TextView)convertView.findViewById(R.id.label);

        mImageView.setImageResource(lightMember.get(position).getImgId());
        mTextView.setText(lightMember.get(position).getFuncName());
        clickButton = (RadioButton)convertView.findViewById(R.id.selected);
        clickButton.setChecked(lightMember.get(position).clickCheck);
        return convertView;
    }
}
