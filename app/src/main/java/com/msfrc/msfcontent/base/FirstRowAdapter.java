package com.msfrc.msfcontent.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.msfrc.msfcontent.R;
//import com.msfrc.msfcontent.click.camera.CameraSceneData;

import java.util.ArrayList;

/**
 * Created by kmuvcl_laptop_dell on 2016-07-25.
 */
public class FirstRowAdapter extends BaseAdapter{
    private ArrayList<FirstRow> firstMember;
    private LayoutInflater inflater;
    public FirstRowAdapter(LayoutInflater inflater, ArrayList<FirstRow> firstMember){
        this.inflater= inflater;
        this.firstMember = firstMember;
    }
    @Override
    public int getCount() {
        return firstMember.size();
    }

    @Override
    public Object getItem(int position) {
        return firstMember.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView== null){
            convertView = inflater.inflate(R.layout.first_row, null);
        }
        ImageView mImageView = (ImageView)convertView.findViewById(R.id.clickImage);
        TextView mTextView = (TextView)convertView.findViewById(R.id.oneClick);
        TextView doubleClick = (TextView)convertView.findViewById(R.id.doubleClick);
        TextView hold = (TextView)convertView.findViewById(R.id.hold);
        mImageView.setImageResource(firstMember.get(position).getClickImgId());
        mTextView.setText(firstMember.get(position).getSingleClick());
        doubleClick.setText(firstMember.get(position).getDoubleClick());
        hold.setText(firstMember.get(position).getHold());
        return convertView;
    }
}
