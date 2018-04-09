package com.msfrc.msfcontent.click.findphone;

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
public class FindPhoneSceneAdapter extends BaseAdapter{
    private ArrayList<FindPhoneSceneData> findPhoneMember;
    private LayoutInflater inflater;
    private RadioButton clickButton;
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
            convertView = inflater.inflate(R.layout.findphone_row, null);
        }
        ImageView mImageView = (ImageView)convertView.findViewById(R.id.image);
        TextView mTextView = (TextView)convertView.findViewById(R.id.label);

        mImageView.setImageResource(findPhoneMember.get(position).getImgId());
        mTextView.setText(findPhoneMember.get(position).getFuncName());
        clickButton = (RadioButton)convertView.findViewById(R.id.selected);
        clickButton.setChecked(findPhoneMember.get(position).clickCheck);
        return convertView;
    }
}
