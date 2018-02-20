package com.msfrc.msfcontent.contacts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.msfrc.msfcontent.R;

import java.util.ArrayList;

/**
 * Created by kmuvcl_laptop_dell on 2016-07-25.
 */
public class ContactTopLineAdapter extends BaseAdapter{

    private ArrayList<ContactTopLineData> listMembers;
    private LayoutInflater inflater;

    public ContactTopLineAdapter(LayoutInflater inflater, ArrayList<ContactTopLineData> listMembers){
        this.inflater = inflater;
        this.listMembers = listMembers;
    }
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
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = inflater.inflate(R.layout.top_list_row, null);
        }
        TextView text_name = (TextView)convertView.findViewById(R.id.allOrSelcet);

        text_name.setText(listMembers.get(position).getTextMessage());

        return convertView;
    }
}
