package com.msfrc.msfcontent.contacts;

import android.content.Intent;
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
import android.widget.ListView;
import android.widget.TextView;

import com.msfrc.msfcontent.R;

import java.util.ArrayList;

/**
 * Created by kmuvcl_laptop_dell on 2016-07-20.
 */
public class ContactScene extends AppCompatActivity implements ContactAdapter.ListBtnClickListener {
    private final String TAG = "Contact Scene";
    private ArrayList<ContactListData> listMembers = new ArrayList<ContactListData>();
    private ArrayList<ContactTopLineData> topMembers = new ArrayList<ContactTopLineData>();
    private TextView mTextView;
    private ContactAdapter listAdapter;
    ListView listView;
    ListView topView;
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact);
        topMembers.add(new ContactTopLineData("Receive all phone and text messages"));
        topMembers.add(new ContactTopLineData("Only Receive phone and text messages from the below contacts"));
        listView = (ListView)findViewById(R.id.list);
        topView  = (ListView)findViewById(R.id.top_list);
        ContactTopLineAdapter mAdapter = new ContactTopLineAdapter(getLayoutInflater(), topMembers);
        topView.setAdapter(mAdapter);
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
        mTextView = (TextView)findViewById(R.id.title);
        mTextView.setText("CONTACTS");
        mTextView.setTextColor(Color.WHITE);
    }
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.contacts_menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                break;
            case R.id.EDIT:
                Intent contactIntent = new Intent(Intent.ACTION_PICK);
                contactIntent.setData(ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(contactIntent, 0);
        }
        return super.onOptionsItemSelected(item);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK)
        {
            Cursor cursor = getContentResolver().query(data.getData(),
                    new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                            ContactsContract.CommonDataKinds.Phone.NUMBER}, null, null, null);
            cursor.moveToFirst();
            String name = cursor.getString(0);        //이름 얻어오기
            Log.d(TAG, name);
            //mNumber.setText(cursor.getString(1));     //번호 얻어오기
            listMembers.add(new ContactListData(name, R.drawable.contactsonly));
            listAdapter= new ContactAdapter(getLayoutInflater(), listMembers, this);
            listView.setAdapter(listAdapter);
            cursor.close();
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onListBtnClick(int position) {

    }
}
