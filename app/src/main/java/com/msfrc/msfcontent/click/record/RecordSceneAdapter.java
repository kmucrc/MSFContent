package com.msfrc.msfcontent.click.record;

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
public class RecordSceneAdapter extends BaseAdapter{
    private ArrayList<RecordSceneData> recordMember;
    private LayoutInflater inflater;
    private RadioButton clickButton;
    public RecordSceneAdapter(LayoutInflater inflater, ArrayList<RecordSceneData> recordMember){
        this.inflater= inflater;
        this.recordMember = recordMember;
    }
    @Override
    public int getCount() {
        return recordMember.size();
    }

    @Override
    public Object getItem(int position) {
        return recordMember.get(position);
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

        mImageView.setImageResource(recordMember.get(position).getImgId());
        mTextView.setText(recordMember.get(position).getFuncName());
        clickButton = (RadioButton)convertView.findViewById(R.id.selected);
        clickButton.setChecked(recordMember.get(position).clickCheck);
        return convertView;
    }
}
