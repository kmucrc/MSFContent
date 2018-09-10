package com.msfrc.msfcontent.click;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
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

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by kmuvcl_laptop_dell on 2016-07-20.
 */
public class ClickAdapter extends BaseAdapter{
    private ArrayList<ClickListData> clickMember;
    private LayoutInflater inflater;
    private Context mContext;
    private static final String TAG = "ClickAdapter";
    private SharedPreferences settings;

    public ClickAdapter(Context context, LayoutInflater inflater, ArrayList<ClickListData> ClickMember){
        this.inflater= inflater;
        this.clickMember = ClickMember;
        this.mContext = context;
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

        Activity act = (Activity) mContext;
        settings = act.getSharedPreferences("setupdData", mContext.MODE_PRIVATE);

        if(convertView== null){
            convertView = inflater.inflate(R.layout.notification_row, null);
        }
        ImageView mImageView = (ImageView)convertView.findViewById(R.id.image);
        TextView mTextView = (TextView)convertView.findViewById(R.id.label);
        mCheckBox = (CheckBox)convertView.findViewById(R.id.selected);
        mImageView.setImageResource(clickMember.get(position).getImgId());
        mTextView.setText(clickMember.get(position).getFunctionName());
        if(position == Constants.clickIndex) {
            mCheckBox.setChecked(true);
        } else {
            mCheckBox.setChecked(false);
        }

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

                    switch(position){
                        case 0:
                            Constants.clickIndex = Constants.CLICK_MUSIC_PLAY;
                            break;
                        case 1:
                            Constants.clickIndex = Constants.CLICK_CAMERA;
                            break;
                        case 2:
                            Constants.clickIndex = Constants.CLICK_EMERGENCY;
                            break;
                        case 3:
                            Constants.clickIndex = Constants.CLICK_MANNER_MODE;
                            break;
                        case 4:
                            Constants.clickIndex = Constants.CLICK_FIND_PHONE;
                            break;
                        case 5:
                            Constants.clickIndex = Constants.CLICK_LIGHT_CONTROL;
                            break;
                        case 6:
                            Constants.clickIndex = Constants.CLICK_RECORD_VOICE;
                            break;
                    }
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putInt("clickIndex", Constants.clickIndex);
                    editor.commit();
                }else{
                    Constants.clickCheck[position] = false;
                    clickMember.get(position).setChecked(false);
                }
            }
        });
        return convertView;
    }
}
