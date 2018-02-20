package com.msfrc.msfcontent.preference;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.msfrc.msfcontent.R;

import java.util.ArrayList;

/**
 * Created by kmuvcl_laptop_dell on 2016-07-20.
 */
public class PreferenceScene extends AppCompatActivity {
    private ArrayList<PreferenceListData> datas = new ArrayList<PreferenceListData>();
    private ImageView mImageView;
    private ListView listView;
    private TextView mTextView;
    TextView titleText;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perference);
        Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pip);
        mImageView =(ImageView)findViewById(R.id.perference_image);
        mImageView.setImageBitmap(getCircleBitmap(mBitmap));

        mTextView = (TextView)findViewById(R.id.account);
        mTextView.setText("shell62@kookmin.ac.kr");

        datas.add(new PreferenceListData(R.drawable.contacts, "CONTACTS TAPS"));
        datas.add(new PreferenceListData(R.drawable.connection, "OUT OF RANGE"));
        datas.add(new PreferenceListData(R.drawable.charged, "LOW BATTERY"));
        datas.add(new PreferenceListData(R.drawable.silentmode, "SLEEP MODE"));

        listView = (ListView)findViewById(R.id.perference_list);

        PreferenceAdapter mAdapter = new PreferenceAdapter(getLayoutInflater(), datas);
        listView.setAdapter(mAdapter);
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
        titleText.setText("PREFERENCE");
        titleText.setTextColor(Color.WHITE);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.click_option_menu, menu);
        return true;
    }
    private Bitmap getCircleBitmap(Bitmap bitmap) {
        final Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(output);
        final int color = Color.RED;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawOval(rectF, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        bitmap.recycle();
        return output;
    }
}
