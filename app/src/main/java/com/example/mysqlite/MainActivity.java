package com.example.mysqlite;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mysqlite.dao.MySqliteOpenHelper;
import com.example.mysqlite.model.User;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    MySqliteOpenHelper mySqliteOpenHelper;
    @BindView(R.id.text)
    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        preferences = getSharedPreferences("myprefs",MODE_PRIVATE);
        editor = preferences.edit();
        mySqliteOpenHelper = new MySqliteOpenHelper(this);
        String name = preferences.getString("name","No Name Found");
        Toast.makeText(this, "welcome "+name, Toast.LENGTH_SHORT).show();
        text.setText("Welcome "+name);
    }
    @OnClick(R.id.logout)
    public void logout(View view){
        editor.putBoolean("auth",false).commit();
        startActivity(new Intent(MainActivity.this,LoginActivity.class));
        finish();
    }


    public void fetch(View view){
        List<User> list = mySqliteOpenHelper.readAllUser();
        StringBuffer buffer = new StringBuffer();
        for(User u : list){
            buffer.append(u.toString()+"\n");
        }
        buffer.append("list size : "+list.size());
        text.setText(buffer.toString());


    }
    @OnClick(R.id.fetch)
    public void useContentProvider(View view){
        String URL = "content://com.example.mysqlite.MyProvider";
        Uri students = Uri.parse(URL);
        Cursor cursor = managedQuery(students, null, null, null, "name");
        if (cursor.moveToFirst()){
            do {
                User myUser = new User();
                myUser.setuId(cursor.getInt(0));
                myUser.setName(cursor.getString(1));
                myUser.setEmail(cursor.getString(2));
                myUser.setPassword(cursor.getString(3));
                Log.d("USERS",myUser.toString());
            }while (cursor.moveToNext());
        }
    }

}
