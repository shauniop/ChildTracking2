package com.child.tracking.system;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
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
        t1.setText("");
        Log.d("Data1","hi");
        db=openOrCreateDatabase("PersonDB", Context.MODE_APPEND, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_NAME+"(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, latitude VARCHAR,longitude VARCHAR);");
        Cursor res=db.rawQuery("select * from "+TABLE_NAME,null);
        Log.d("Data1",res.toString());
            StringBuffer buffer=new StringBuffer();
            while (res.moveToNext()){
                Log.d("Data2",res.getString(0));
                buffer.append(res.getString(0)+"\n"+"Latitude "+res.getString(1)).append("\n");
                buffer.append("Longitude "+res.getString(2)).append("\n");
                Log.d("Data1",buffer.toString());
            }
        t1.setMovementMethod(new ScrollingMovementMethod());
        t1.setText(buffer.toString());
        if(t1.getText().toString().equals("")){
            t1.setText("No history recorded yet");
        }
    }
}
