package com.msfrc.msfcontent.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

//import com.example.kmuvcl_laptop_dell.msfcontent.R;
//import com.example.kmuvcl_laptop_dell.msfcontent.click.ClickListData;

import com.msfrc.msfcontent.R;

import java.util.ArrayList;

/**
 * Created by kmuvcl_laptop_dell on 2016-07-25.
 */
public class UiSceneAdapter extends BaseAdapter{
    private ArrayList<UiSceneData> uiMember;
    private LayoutInflater inflater;

    public UiSceneAdapter(LayoutInflater inflater, ArrayList<UiSceneData> uiMember){
        this.inflater= inflater;
        this.uiMember = uiMember;
    }
    @Override
    public int getCount() {
        return uiMember.size();
    }

    @Override
    public Object getItem(int position) {
        return uiMember.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView== null){
            convertView = inflater.inflate(R.layout.ui_row, null);
        }
        ImageView mImageView = (ImageView)convertView.findViewById(R.id.ui_image);
        TextView mTextView = (TextView)convertView.findViewById(R.id.ui_text);

        mImageView.setImageResource(uiMember.get(position).getImgId());
        mTextView.setText(uiMember.get(position).getFunctionName());

        return convertView;
    }
}
