package com.example.chatting;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class othersReviews extends AppCompatActivity {
    ListView lv;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    Activity activity;
    ArrayList<review2> rev=new ArrayList<review2>();
    String str,str1,str2,ss;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_others_reviews);
        Intent intent=getIntent();
        str1=intent.getStringExtra("val");
        ss=intent.getStringExtra("val2");
        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getReadableDatabase();
        tv=(TextView)findViewById(R.id.txt) ;
        tv.setText( " Reviews For "+ss);
        activity = this;
        lv = (ListView) findViewById(R.id.listview);
        String[] colm={DatabaseContract.Review.COL_PLACED_BY,DatabaseContract.Review.COL_REVIEW};
        Cursor cr=db.query(DatabaseContract.Review.TABLE_NAME,colm," PlacedTo = ?", new String[] {str1}
                , null, null, null, null);
        if (cr.getCount()==0) {
            Toast.makeText(getApplicationContext(),"No Record exist",Toast.LENGTH_LONG).show();
        }
        else
        {
            //rev.clear();
            while(cr.moveToNext())
            {
                str=String.valueOf(cr.getLong(0));
                String[] column = {DatabaseContract.Customers.COL_NAME};
                Cursor crc = db.query(DatabaseContract.Customers.TABLE_NAME, column, DatabaseContract.Customers._ID + "=?", new String[]{str}, null, null, null, null);
                if (crc.getCount() == 0) {
                    str1 = "first Milk man";
                } else {

                    crc.moveToFirst();
                    str=crc.getString(0);
                }

                str2=cr.getString(1);
                review2 mObj = new review2(str2,str);
                rev.add(mObj);
            }

            review2Holder rList = new review2Holder(activity, rev);


            lv.setAdapter(rList);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                    Toast.makeText(getApplicationContext(), "You Selected " + rev.get(position).getName() + " as Country", Toast.LENGTH_LONG).show();
                }
            });
        }
        db.close();


    }
}