package com.child.tracking.system;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class DisplayData extends AppCompatActivity {

    private SQLiteDatabase db;
    TextView t1;
    public static final String TABLE_NAME = "persons";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_data);
        TableLayout tableLayout=(TableLayout) findViewById(R.id.tl);
        tableLayout.setStretchAllColumns(true);
        tableLayout.bringToFront();
        TextView q = new TextView(this);
        TextView q2 = new TextView(this);
        TextView q3 = new TextView(this);
        q.setTextColor(Color.YELLOW);
        q2.setTextColor(Color.YELLOW);
        q3.setTextColor(Color.YELLOW);

        q.setText("No.");
        q2.setText("Latitude");
        q3.setText("Longitude");
        TableRow tr1 = new TableRow(this);
        tr1.setLayoutParams(new ActionBar.LayoutParams( ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT));
        if(q.getParent()!=null)
            ((ViewGroup)q.getParent()).removeView(q); // <- fix
        tr1.addView(q);
        tr1.addView(q2);
        tr1.addView(q3);
        tableLayout.addView(tr1);

//textview.getTextColors(R.color.)

//        t1=(TextView)findViewById(R.id.viewDatabase);
//        t1.setText("");
        Log.d("Data1","hi");
        db=openOrCreateDatabase("PersonDB", Context.MODE_APPEND, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_NAME+"(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, latitude VARCHAR,longitude VARCHAR);");
        Cursor res=db.rawQuery("select * from "+TABLE_NAME,null);
        Log.d("Data1",res.toString());
            while (res.moveToNext()){
//                buffer.append(res.getString(0)+"\n"+"Latitude "+res.getString(1)).append("\n");
//                buffer.append("Longitude "+res.getString(2)).append("\n");
                TextView textview = new TextView(this);
                TextView textview2 = new TextView(this);
                TextView textview3 = new TextView(this);

                textview.setText(res.getString(0));
                textview2.setText(res.getString(1));
                textview3.setText(res.getString(2));
                textview.setTextColor(Color.YELLOW);
                textview2.setTextColor(Color.YELLOW);
                textview3.setTextColor(Color.YELLOW);

                TableRow tr2 = new TableRow(this);
                tr2.setLayoutParams(new ActionBar.LayoutParams( ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT));
                if(textview.getParent()!=null)
                    ((ViewGroup)textview.getParent()).removeView(textview); // <- fix
                tr2.addView(textview);
                tr2.addView(textview2);
                tr2.addView(textview3);
                tr2.setBackgroundResource(R.drawable.border);
                tableLayout.addView(tr2);
            }
//        t1.setMovementMethod(new ScrollingMovementMethod());
//        t1.setText(buffer.toString());
//        if(t1.getText().toString().equals("")){
//            t1.setText("No history recorded yet");
//        }
    }
}
