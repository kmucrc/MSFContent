package com.msfrc.msfcontent.contacts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.msfrc.msfcontent.R;

import java.util.ArrayList;

/**
 * Created by kmuvcl_laptop_dell on 2016-07-20.
 */
public class ContactAdapter extends BaseAdapter{

    private ArrayList<ContactListData> listMembers;
    private LayoutInflater inflater;
    public static ImageButton deleteButton;

    public ContactAdapter(LayoutInflater inflater, ArrayList<ContactListData> listMembers){
        this.inflater = inflater;
        this.listMembers = listMembers;
    }


    @Override
    public int getCount() {
        return listMembers.size();
    }

    @Override
    public Object getItem(int position) {
        return listMembers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = inflater.inflate(R.layout.contact_row, null);
        }
        ImageView img_flag = (ImageView)convertView.findViewById(R.id.img_flag);
        TextView text_name = (TextView)convertView.findViewById(R.id.label);
        deleteButton = (ImageButton)convertView.findViewById(R.id.selected);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listMembers.remove(position);
                notifyDataSetChanged();
            }
        });
        img_flag.setImageResource(listMembers.get(position).getImgId());
        text_name.setText(listMembers.get(position).getName());
        return convertView;
    }
}
