package com.msfrc.msfcontent.preference;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.msfrc.msfcontent.R;

import java.util.ArrayList;

/**
 * Created by kmuvcl_laptop_dell on 2016-07-20.
 */
public class PreferenceAdapter extends BaseAdapter implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private ArrayList<PreferenceListData> preferenceMember;
    private LayoutInflater inflater;
    private RadioButton checkButton;

    public PreferenceAdapter(LayoutInflater inflater, ArrayList<PreferenceListData> PreferenceMember){
        this.inflater= inflater;
        this.preferenceMember = PreferenceMember;
    }
    @Override
    public int getCount() {
        return preferenceMember.size();
    }

    @Override
    public Object getItem(int position) {
        return preferenceMember.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView== null){
            convertView = inflater.inflate(R.layout.perference_row, null);
        }
        ImageView mImageView = (ImageView)convertView.findViewById(R.id.preference_list_image);
        TextView mTextView = (TextView)convertView.findViewById(R.id.preference_text);
        mImageView.setImageResource(preferenceMember.get(position).getImgId());
        mTextView.setText(preferenceMember.get(position).getOptionName());
        return convertView;
    }

    @Override
    public void onClick(View v) {
        if(checkButton.isChecked()){
            checkButton.setChecked(false);
        }
        else{
            checkButton.setChecked(true);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }
}
