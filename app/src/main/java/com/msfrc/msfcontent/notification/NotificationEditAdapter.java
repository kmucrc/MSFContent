package com.msfrc.msfcontent.notification;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.msfrc.msfcontent.R;
import com.msfrc.msfcontent.base.Constants;

import java.util.ArrayList;

/**
 * Created by kmuvcl_laptop_dell on 2016-07-21.
 */
public class NotificationEditAdapter extends BaseAdapter{
    private ArrayList<NotificationEditData> notificationMember;
    private ArrayList<NotificationColorData> datas = new ArrayList<NotificationColorData>();
    private ArrayList<NotificationEditData> editDatas = new ArrayList<NotificationEditData>();
    private LayoutInflater inflater;
    private static final String TAG = "Notification Edit";
    public static boolean colorScene = false;
    public static String[] parcelArray = new String[5];
    public NotificationEditAdapter(LayoutInflater inflater, ArrayList<NotificationEditData> notificationMember){
        this.inflater= inflater;
        this.notificationMember = notificationMember;
    }
    @Override
    public int getCount() {
        return notificationMember.size();
    }

    @Override
    public Object getItem(int position) {
        return notificationMember.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView== null){
            convertView = inflater.inflate(R.layout.notification_edit_row, null);
        }
        ImageView mImageView = (ImageView)convertView.findViewById(R.id.image);
        final TextView mTextView = (TextView)convertView.findViewById(R.id.label);
        final ImageButton colorButton = (ImageButton)convertView.findViewById(R.id.imgbutton);
        final ImageButton vibeButton = (ImageButton)convertView.findViewById(R.id.vibebutton);
        final ImageButton redButton = (ImageButton)convertView.findViewById(R.id.redButton);
        final ImageButton blueButton = (ImageButton)convertView.findViewById(R.id.blueButton);
        final ImageButton yellowButton = (ImageButton)convertView.findViewById(R.id.yellowButton);
        final ImageButton whiteButton = (ImageButton)convertView.findViewById(R.id.whiteButton);
        final ImageButton greenButton = (ImageButton)convertView.findViewById(R.id.greenButton);
        final ImageButton firstVibeButton = (ImageButton)convertView.findViewById(R.id.onevibe);
        final ImageButton secondVibeButton = (ImageButton)convertView.findViewById(R.id.twovibe);
        final ImageButton thirdVibeButton = (ImageButton)convertView.findViewById(R.id.threevibe);
        final ImageButton fourthVibeButton = (ImageButton)convertView.findViewById(R.id.fourvibe);
        final ImageButton infiniteVibeButton = (ImageButton)convertView.findViewById(R.id.infinitevibe);
        final ImageButton noneVibeButton = (ImageButton)convertView.findViewById(R.id.nonevibe);
        mImageView.setImageResource(notificationMember.get(position).getImgId());
        mTextView.setText(notificationMember.get(position).getFunctionName());
        colorButton.setImageResource(notificationMember.get(position).getColorimgId());
        vibeButton.setImageResource(notificationMember.get(position).getVibeimgId());
        if(Constants.notificationSave)
        {
            vibeButton.setScaleType(ImageView.ScaleType.FIT_CENTER);
            vibeButton.setBackgroundColor(Color.rgb(255, 174, 201));
        }
        mTextView.setBackgroundColor(notificationMember.get(position).getBackgroundColor());
        mTextView.setTextColor(notificationMember.get(position).getTextxColor());
        mTextView.setTag(position);
        colorButton.setTag(position);
        vibeButton.setTag(position);
        redButton.setTag(position);
        blueButton.setTag(position);
        yellowButton.setTag(position);
        whiteButton.setTag(position);
        greenButton.setTag(position);
        colorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstVibeButton.setVisibility(View.INVISIBLE);
                secondVibeButton.setVisibility(View.INVISIBLE);
                thirdVibeButton.setVisibility(View.INVISIBLE);
                fourthVibeButton.setVisibility(View.INVISIBLE);
                infiniteVibeButton.setVisibility(View.INVISIBLE);
                noneVibeButton.setVisibility(View.INVISIBLE);
                mTextView.setVisibility(View.INVISIBLE);
                colorButton.setVisibility(View.INVISIBLE);
                redButton.setVisibility(View.VISIBLE);
                blueButton.setVisibility(View.VISIBLE);
                yellowButton.setVisibility(View.VISIBLE);
                whiteButton.setVisibility(View.VISIBLE);
                greenButton.setVisibility(View.VISIBLE);
                redButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        redButton.setVisibility(View.INVISIBLE);
                        blueButton.setVisibility(View.INVISIBLE);
                        yellowButton.setVisibility(View.INVISIBLE);
                        whiteButton.setVisibility(View.INVISIBLE);
                        greenButton.setVisibility(View.INVISIBLE);
                        mTextView.setVisibility(View.VISIBLE);
//                        mTextView.setTextColor(Color.rgb(220, 30, 230));
                        mTextView.setBackgroundColor(Color.rgb(220, 30, 230));
                        colorButton.setVisibility(View.VISIBLE);
                        Constants.redColor[position] = true;
                        Constants.blueColor[position] = false;
                        Constants.greenColor[position] = false;
                        Constants.yelloColor[position] = false;
                        Constants.whiteColor[position] = false;
                    }
                });
                blueButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        redButton.setVisibility(View.INVISIBLE);
                        blueButton.setVisibility(View.INVISIBLE);
                        yellowButton.setVisibility(View.INVISIBLE);
                        whiteButton.setVisibility(View.INVISIBLE);
                        greenButton.setVisibility(View.INVISIBLE);
                        mTextView.setVisibility(View.VISIBLE);
//                        mTextView.setTextColor(Color.rgb(9, 158, 253));
                        mTextView.setBackgroundColor(Color.rgb(9, 158, 253));
                        colorButton.setVisibility(View.VISIBLE);
                        Constants.redColor[position] = false;
                        Constants.blueColor[position] = true;
                        Constants.greenColor[position] = false;
                        Constants.yelloColor[position] = false;
                        Constants.whiteColor[position] = false;
                    }
                });
                whiteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        redButton.setVisibility(View.INVISIBLE);
                        blueButton.setVisibility(View.INVISIBLE);
                        yellowButton.setVisibility(View.INVISIBLE);
                        whiteButton.setVisibility(View.INVISIBLE);
                        greenButton.setVisibility(View.INVISIBLE);
                        mTextView.setVisibility(View.VISIBLE);
                        mTextView.setTextColor(Color.WHITE);
                        mTextView.setBackgroundColor(Color.WHITE);
                        colorButton.setVisibility(View.VISIBLE);
                        Constants.redColor[position] = false;
                        Constants.blueColor[position] = false;
                        Constants.greenColor[position] = false;
                        Constants.yelloColor[position] = false;
                        Constants.whiteColor[position] = true;
                    }
                });
                greenButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        redButton.setVisibility(View.INVISIBLE);
                        blueButton.setVisibility(View.INVISIBLE);
                        yellowButton.setVisibility(View.INVISIBLE);
                        whiteButton.setVisibility(View.INVISIBLE);
                        greenButton.setVisibility(View.INVISIBLE);
                        mTextView.setVisibility(View.VISIBLE);
//                        mTextView.setTextColor(Color.rgb(70, 230, 180));
                        mTextView.setBackgroundColor(Color.rgb(70, 230, 180));
                        colorButton.setVisibility(View.VISIBLE);
                        Constants.redColor[position] = false  ;
                        Constants.blueColor[position] = false;
                        Constants.greenColor[position] = true;
                        Constants.yelloColor[position] = false;
                        Constants.whiteColor[position] = false;
                    }
                });
                yellowButton.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        redButton.setVisibility(View.INVISIBLE);
                        blueButton.setVisibility(View.INVISIBLE);
                        yellowButton.setVisibility(View.INVISIBLE);
                        whiteButton.setVisibility(View.INVISIBLE);
                        greenButton.setVisibility(View.INVISIBLE);
                        mTextView.setVisibility(View.VISIBLE);
//                        mTextView.setTextColor(Color.rgb(255, 217, 102));
                        mTextView.setBackgroundColor(Color.rgb(255, 217, 102));
                        colorButton.setVisibility(View.VISIBLE);
                        Constants.redColor[position] = false;
                        Constants.blueColor[position] = false;
                        Constants.greenColor[position] = false;
                        Constants.yelloColor[position] = true;
                        Constants.whiteColor[position] = false;
                    }
                });
            }
        });
        vibeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redButton.setVisibility(View.INVISIBLE);
                blueButton.setVisibility(View.INVISIBLE);
                yellowButton.setVisibility(View.INVISIBLE);
                whiteButton.setVisibility(View.INVISIBLE);
                greenButton.setVisibility(View.INVISIBLE);
                firstVibeButton.setVisibility(View.VISIBLE);
                secondVibeButton.setVisibility(View.VISIBLE);
                thirdVibeButton.setVisibility(View.VISIBLE);
                fourthVibeButton.setVisibility(View.VISIBLE);
                infiniteVibeButton.setVisibility(View.VISIBLE);
                noneVibeButton.setVisibility(View.VISIBLE);
                mTextView.setVisibility(View.INVISIBLE);
                colorButton.setVisibility(View.INVISIBLE);
                vibeButton.setVisibility(View.INVISIBLE);
                firstVibeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        firstVibeButton.setVisibility(View.INVISIBLE);
                        secondVibeButton.setVisibility(View.INVISIBLE);
                        thirdVibeButton.setVisibility(View.INVISIBLE);
                        fourthVibeButton.setVisibility(View.INVISIBLE);
                        infiniteVibeButton.setVisibility(View.INVISIBLE);
                        noneVibeButton.setVisibility(View.INVISIBLE);
                        vibeButton.setVisibility(View.VISIBLE);
                        vibeButton.setImageResource(R.drawable.vibeone);
                        vibeButton.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        vibeButton.setBackgroundColor(Color.rgb(255, 174, 201));
                        colorButton.setVisibility(View.VISIBLE);
                        mTextView.setVisibility(View.VISIBLE);
                        parcelArray[position] = Constants.parcel[0];
                        Constants.isParecel[position][0] = true;
                        Constants.isParecel[position][1] = false;
                        Constants.isParecel[position][2] = false;
                        Constants.isParecel[position][3] = false;
                        Constants.isParecel[position][4] = false;
                        Constants.isParecel[position][5] = false;
                    }
                });
                secondVibeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        firstVibeButton.setVisibility(View.INVISIBLE);
                        secondVibeButton.setVisibility(View.INVISIBLE);
                        thirdVibeButton.setVisibility(View.INVISIBLE);
                        fourthVibeButton.setVisibility(View.INVISIBLE);
                        infiniteVibeButton.setVisibility(View.INVISIBLE);
                        noneVibeButton.setVisibility(View.INVISIBLE);
                        vibeButton.setVisibility(View.VISIBLE);
                        vibeButton.setImageResource(R.drawable.vibetwo);
                        vibeButton.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        vibeButton.setBackgroundColor(Color.rgb(255, 174, 201));
                        colorButton.setVisibility(View.VISIBLE);
                        mTextView.setVisibility(View.VISIBLE);
                        parcelArray[position] = Constants.parcel[1];
                        Constants.isParecel[position][0] = false;
                        Constants.isParecel[position][1] = true;
                        Constants.isParecel[position][2] = false;
                        Constants.isParecel[position][3] = false;
                        Constants.isParecel[position][4] = false;
                        Constants.isParecel[position][5] = false;
                    }
                });
                thirdVibeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        firstVibeButton.setVisibility(View.INVISIBLE);
                        secondVibeButton.setVisibility(View.INVISIBLE);
                        thirdVibeButton.setVisibility(View.INVISIBLE);
                        fourthVibeButton.setVisibility(View.INVISIBLE);
                        infiniteVibeButton.setVisibility(View.INVISIBLE);
                        noneVibeButton.setVisibility(View.INVISIBLE);
                        vibeButton.setVisibility(View.VISIBLE);
                        vibeButton.setImageResource(R.drawable.vibethree);
                        vibeButton.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        vibeButton.setBackgroundColor(Color.rgb(255, 174, 201));
                        colorButton.setVisibility(View.VISIBLE);
                        mTextView.setVisibility(View.VISIBLE);
                        parcelArray[position] = Constants.parcel[2];
                        Constants.isParecel[position][0] = false;
                        Constants.isParecel[position][1] = false;
                        Constants.isParecel[position][2] = true;
                        Constants.isParecel[position][3] = false;
                        Constants.isParecel[position][4] = false;
                        Constants.isParecel[position][5] = false;

                    }
                });
                fourthVibeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        firstVibeButton.setVisibility(View.INVISIBLE);
                        secondVibeButton.setVisibility(View.INVISIBLE);
                        thirdVibeButton.setVisibility(View.INVISIBLE);
                        fourthVibeButton.setVisibility(View.INVISIBLE);
                        infiniteVibeButton.setVisibility(View.INVISIBLE);
                        noneVibeButton.setVisibility(View.INVISIBLE);
                        vibeButton.setVisibility(View.VISIBLE);
                        vibeButton.setImageResource(R.drawable.vibefour);
                        vibeButton.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        vibeButton.setBackgroundColor(Color.rgb(255, 174, 201));
                        colorButton.setVisibility(View.VISIBLE);
                        mTextView.setVisibility(View.VISIBLE);
                        parcelArray[position] = Constants.parcel[3];
                        Constants.isParecel[position][0] = false;
                        Constants.isParecel[position][1] = false;
                        Constants.isParecel[position][2] = false;
                        Constants.isParecel[position][3] = true;
                        Constants.isParecel[position][4] = false;
                        Constants.isParecel[position][5] = false;
                    }
                });
                infiniteVibeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        firstVibeButton.setVisibility(View.INVISIBLE);
                        secondVibeButton.setVisibility(View.INVISIBLE);
                        thirdVibeButton.setVisibility(View.INVISIBLE);
                        fourthVibeButton.setVisibility(View.INVISIBLE);
                        infiniteVibeButton.setVisibility(View.INVISIBLE);
                        noneVibeButton.setVisibility(View.INVISIBLE);
                        vibeButton.setVisibility(View.VISIBLE);
                        vibeButton.setImageResource(R.drawable.infinity);
                        vibeButton.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        vibeButton.setBackgroundColor(Color.rgb(255, 174, 201));
                        colorButton.setVisibility(View.VISIBLE);
                        mTextView.setVisibility(View.VISIBLE);
                        parcelArray[position] = Constants.parcel[4];
                        Constants.isParecel[position][0] = false;
                        Constants.isParecel[position][1] = false;
                        Constants.isParecel[position][2] = false;
                        Constants.isParecel[position][3] = false;
                        Constants.isParecel[position][4] = true;
                        Constants.isParecel[position][5] = false;
                    }
                });
                noneVibeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        firstVibeButton.setVisibility(View.INVISIBLE);
                        secondVibeButton.setVisibility(View.INVISIBLE);
                        thirdVibeButton.setVisibility(View.INVISIBLE);
                        fourthVibeButton.setVisibility(View.INVISIBLE);
                        infiniteVibeButton.setVisibility(View.INVISIBLE);
                        noneVibeButton.setVisibility(View.INVISIBLE);
                        vibeButton.setVisibility(View.VISIBLE);
                        vibeButton.setImageResource(R.drawable.none);
                        vibeButton.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        vibeButton.setBackgroundColor(Color.rgb(255, 174, 201));
                        colorButton.setVisibility(View.VISIBLE);
                        mTextView.setVisibility(View.VISIBLE);
                        parcelArray[position] = Constants.parcel[5];
                        Constants.isParecel[position][0] = false;
                        Constants.isParecel[position][1] = false;
                        Constants.isParecel[position][2] = false;
                        Constants.isParecel[position][3] = false;
                        Constants.isParecel[position][4] = false;
                        Constants.isParecel[position][5] = true;
                    }
                });
            }
        });
        return convertView;
    }

}
