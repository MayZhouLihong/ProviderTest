package com.may.providertest;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String newId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_add).setOnClickListener(this);
        findViewById(R.id.btn_delete).setOnClickListener(this);
        findViewById(R.id.btn_update).setOnClickListener(this);
        findViewById(R.id.btn_query).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_add:
                addData();
                break;
            case R.id.btn_delete:
                deleteData();
                break;
            case R.id.btn_update:
                updateData();
                break;
            case R.id.btn_query:
                queryData();
                break;
        }
    }

    @SuppressLint("Range")
    private void queryData() {
        Uri uri = Uri.parse("content://com.may.databasetest.provider/book");
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor!=null){
            while (cursor.moveToNext()){
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String author = cursor.getString(cursor.getColumnIndex("author"));
                int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                double price = cursor.getDouble(cursor.getColumnIndex("price"));
                Log.d("MainActivity", "book name is "+name);
                Log.d("MainActivity", "book author is "+author);
                Log.d("MainActivity", "book pages is "+pages);
                Log.d("MainActivity", "book price is "+price);
            }
            cursor.close();
        }
    }

    private void updateData() {
        Uri uri = Uri.parse("content://com.may.databasetest.provider/book/"+newId);
        ContentValues values=new ContentValues();
        values.put("author","三毛");
        getContentResolver().update(uri,values,null,null);
    }

    private void deleteData() {
        Uri uri = Uri.parse("content://com.may.databasetest.provider/book/"+newId);
        getContentResolver().delete(uri,null,null);
    }

    private void addData() {
        Uri uri = Uri.parse("content://com.may.databasetest.provider/book");
        ContentValues values=new ContentValues();
        values.put("name","半生缘");
        values.put("author","张爱玲");
        values.put("pages","1020");
        values.put("price","80");
        Uri newUri = getContentResolver().insert(uri, values);
        newId = newUri.getPathSegments().get(1);
    }
}