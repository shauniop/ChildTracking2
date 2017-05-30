package com.child.tracking.system;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class DisplayData extends AppCompatActivity {

    private SQLiteDatabase db;
    TextView t1;
    public static final String TABLE_NAME = "persons";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_data);
        t1=(TextView)findViewById(R.id.viewDatabase);
    }
    public void viewAll(View v){
        Log.d("Data1","hi");
        db=openOrCreateDatabase("PersonDB", Context.MODE_PRIVATE, null);
        Cursor res=db.rawQuery("select * from "+TABLE_NAME,null);
        Log.d("Data1",res.toString());
            StringBuffer buffer=new StringBuffer();
            while (res.moveToNext()){
                Log.d("Data2",res.getString(0));
                buffer.append(res.getString(1)+"\n");
                buffer.append(res.getString(2)+"\n");
                Log.d("Data1",buffer.toString());
            }
            t1.setText(buffer.toString());
    }
}
