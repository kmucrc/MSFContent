package com.msfrc.msfcontent.click.emergency;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.msfrc.msfcontent.R;
import com.msfrc.msfcontent.base.CommonDialog;
import com.msfrc.msfcontent.base.Constants;
import com.msfrc.msfcontent.base.FirstRow;
import com.msfrc.msfcontent.base.FirstRowAdapter;
import com.msfrc.msfcontent.contacts.ContactAdapter;
import com.msfrc.msfcontent.contacts.ContactListData;

import java.util.ArrayList;
import com.google.gson.Gson;

import static com.msfrc.msfcontent.connection.ConnectionScene.context;

/**
 * Created by kmuvcl_laptop_dell on 2016-07-25.
 */
public class EmergencyScene extends AppCompatActivity implements MenuItem.OnMenuItemClickListener, ContactAdapter.ListBtnClickListener {

    private ArrayList<FirstRow> firstRow = new ArrayList<FirstRow>();
    private TextView titleText;
    private SharedPreferences settings;
    private ArrayList<EmergencySceneData> emergencyListData = new ArrayList<EmergencySceneData>();
    private ArrayList<ContactListData> listMembers = new ArrayList<ContactListData>();
    private ArrayList<EmergencyListData> emergencyContactData = new ArrayList<EmergencyListData>();
    private ContactAdapter listAdapter;
    ListView lvContact;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emergency_main);
        ListView emergencyView;
        ListView firstRowView;
        TextView tvEditPhoneNumber;
        settings = getPreferences(MODE_PRIVATE);
        int emergencyChecked = settings.getInt("emergencySendLocation", Constants.emergencySendLocation);

        firstRow.add(new FirstRow(R.drawable.click, "Click1", "Click2", "Hold"));
        emergencyListData.add(new EmergencySceneData(R.drawable.location2, "SEND YOUR LOCATION", emergencyChecked));
        firstRowView = (ListView)findViewById(R.id.li_mainList);
        emergencyView = (ListView)findViewById(R.id.li_settingList);
        FirstRowAdapter firstAdapter = new FirstRowAdapter(getLayoutInflater(), firstRow);
        EmergencySceneAdapter emergencyAdapter = new EmergencySceneAdapter(getLayoutInflater(), emergencyListData);
        firstRowView.setAdapter(firstAdapter);
        emergencyView.setAdapter(emergencyAdapter);

        tvEditPhoneNumber = (TextView)findViewById(R.id.tv_editPhoneNumber);
        tvEditPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent contactIntent = new Intent(Intent.ACTION_PICK);
                contactIntent.setData(ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(contactIntent, 0);
            }
        });

        lvContact = (ListView)findViewById(R.id.li_contactList);
        lvContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        loadContactsPreferences();
        setContactList();
        setCustomerActionBar();
    }

    private void setCustomerActionBar(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.rgb(51, 51, 51)));
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        View mCustomView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.actionbar, null);
        actionBar.setCustomView(mCustomView);
        titleText = (TextView)findViewById(R.id.title);
        titleText.setText("EMERGENCY");
        titleText.setTextColor(Color.WHITE);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.home:
                Constants.emergencyPage = false;
                NavUtils.navigateUpFromSameTask(this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.click_option_menu, menu);
        MenuItem saveItem = menu.findItem(R.id.EDIT);
        saveItem.setOnMenuItemClickListener(this);
        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        final CommonDialog alertDialog = new CommonDialog(this, Constants.COMMONDIALOG_TWOBUTTON);
        alertDialog.setTitle(getString(R.string.str_commondialog_title_save_settings));
        alertDialog.setMessage(getString(R.string.str_commondialog_message_save_settings));
        alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
        alertDialog.setPositiveButton(getString(R.string.str_commondialog_ok), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                SharedPreferences.Editor editor = settings.edit();
                editor.putInt("emergencySendLocation", Constants.emergencySendLocation);
                editor.commit();
                Toast.makeText(getApplicationContext(),getString(R.string.str_commondialog_message_settings_have_been_saved),
                        Toast.LENGTH_SHORT).show();
            }
        });

        alertDialog.setNegativeButton(getString(R.string.str_commondialog_no), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

            }
        });
        alertDialog.show();
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        requestCode = RESULT_CANCELED;
        if(resultCode == RESULT_OK)
        {
            if(listMembers.size() < Constants.EMERGENCY_CONTACT_LIMIT) {
                Cursor cursor = getContentResolver().query(data.getData(),
                        new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                                ContactsContract.CommonDataKinds.Phone.NUMBER}, null, null, null);
                cursor.moveToFirst();
                String name = cursor.getString(0);        //이름 얻어오기
                String number = cursor.getString(1);     //번호 얻어오기
                boolean bIsDuplicate = false;


                for (EmergencyListData checkData : emergencyContactData) {
                    if(checkData.getNumber().equals(number)) {
                        bIsDuplicate = true;
                        break;
                    }
                }
                if(!bIsDuplicate) {
                    listMembers.add(new ContactListData(name, R.drawable.contactsonly));
                    listAdapter = new ContactAdapter(getLayoutInflater(), listMembers, this);
                    lvContact.setAdapter(listAdapter);
                    cursor.close();

                    emergencyContactData.add(new EmergencyListData(name, number));
                    Constants.listPhoneNumber.add(number);

                    Log.e("eleutheria", "ContactData size : " + emergencyContactData.size());
                    saveContactsreferences(emergencyContactData);
                } else {
                    Toast.makeText(getApplicationContext(),getString(R.string.str_emergency_duplicate_number),
                            Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(),getString(R.string.str_emergency_limit_warning),
                        Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(getApplicationContext(),getString(R.string.str_commondialog_message_settings_have_been_saved),
                    Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void saveContactsreferences(ArrayList<EmergencyListData> emergencyContactData) {
        SharedPreferences.Editor editor = settings.edit();
        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(emergencyContactData);
        editor.putString("emergencyContacts", jsonFavorites);
        editor.commit();
    }

    private void loadContactsPreferences() {
        String jsonContacts = settings.getString("emergencyContacts", null);
        Gson gson = new Gson();
        EmergencyListData[] ContactsItems = gson.fromJson(jsonContacts,EmergencyListData[].class);

        if(ContactsItems != null) {
            for (EmergencyListData listData : ContactsItems) {
                emergencyContactData.add(new EmergencyListData(listData.getName(), listData.getNumber()));
                Constants.listPhoneNumber.add(listData.getNumber());
            }
        }
    }

    private void setContactList() {
        if(emergencyContactData.size() > 0) {
            for(EmergencyListData listData : emergencyContactData) {
                listMembers.add(new ContactListData(listData.getName(), R.drawable.contactsonly));
            }
            listAdapter = new ContactAdapter(getLayoutInflater(), listMembers, this);
            lvContact.setAdapter(listAdapter);
        }
    }

    @Override
    public void onListBtnClick(int position) {
        Log.e("eleutheria", "activity click : " + position + "button");
        emergencyContactData.remove(position);
        Constants.listPhoneNumber.remove(position);
        saveContactsreferences(emergencyContactData);
    }
}
