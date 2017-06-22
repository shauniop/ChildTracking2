package com.child.tracking.system;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class LoginActivity extends AppCompatActivity {

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Id = "stdIdKey";
    public static final String Id2 = "name";


    SharedPreferences sharedpreferences;
    EditText ed1,ed2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        if(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ed1=(EditText)findViewById(R.id.email);
        ed2=(EditText)findViewById(R.id.password);

    }
    public void actualEntry(View v){
        final String id = ed1.getText().toString();
        final String password = ed2.getText().toString();

        DatabaseReference root = FirebaseDatabase.getInstance().getReference().child("students").child(id).child("password");
        root.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String pswd = dataSnapshot.getValue().toString();
                Log.d("StudentPassword", pswd);
                if (pswd.equals(password)) {
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    Log.d("DataSnapshot: ",dataSnapshot.toString());
                    Log.d("user", id);
                    editor.putString(Id, id);
                    editor.apply();

                    Intent intent = new Intent(LoginActivity.this, DisplayData.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
//        DatabaseReference s=root.child("students");
//        s.addListenerForSingleValueEvent(new ValueEventListener() {
//            HashMap<String, Object> args = new HashMap<String, Object>();
//            String json;
//            @Override
//            public void onDataChange(DataSnapshot snapshot) {
//                for (DataSnapshot child : snapshot.getChildren()) {
//                    Map<String, Object> newPost = (Map<String, Object>) child.getValue();
//                    String childname = newPost.get("name").toString();
//                    String password1 = newPost.get("password").toString();
//                    String data1= String.valueOf(child.getKey());
//                    if(ed1.getText().toString().equals(childname)&&ed2.getText().toString().equals(password1)){
//                        Toast.makeText(getApplicationContext(),"Login Successfully", Toast.LENGTH_LONG).show();
//                        SharedPreferences.Editor editor = sharedpreferences.edit();
//                        Log.d("Data1",data1);
//                        editor.putString(Id,data1);
//                        editor.commit();
//                        Intent intent = new Intent(LoginActivity.this, DisplayData.class);
//                        startActivity(intent);
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//            }
//
//        });
        //§Log.d("Students value", "");

    }

}
