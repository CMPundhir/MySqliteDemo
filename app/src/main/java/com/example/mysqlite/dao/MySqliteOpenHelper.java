package com.example.mysqlite.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mysqlite.model.User;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

public class MySqliteOpenHelper extends SQLiteOpenHelper {

    private static final String TABLE_NAME="user";
    private static final String COLUMN_ID="id";
    private static final String COLUMN_NAME="name";
    private static final String COLUMN_EMAIL="email";
    private static final String COLUMN_PASSWORD="password";

    public MySqliteOpenHelper( Context context) {
        super(context,"mydb",null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NAME + " TEXT,"
                + COLUMN_EMAIL + " TEXT UNIQUE," + COLUMN_PASSWORD + " TEXT " + ")";
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues userValues = new ContentValues();
        userValues.put(COLUMN_NAME,user.getName());
        userValues.put(COLUMN_EMAIL,user.getEmail());
        userValues.put(COLUMN_PASSWORD,user.getPassword());

        db.insert(TABLE_NAME,null,userValues);

        db.close();
    }

    public List<User> readAllUser(){

        List<User> userList = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);

        if (cursor.moveToFirst()){
            do {
                User myUser = new User();
                myUser.setuId(cursor.getInt(0));
                myUser.setName(cursor.getString(1));
                myUser.setEmail(cursor.getString(2));
                myUser.setPassword(cursor.getString(3));

                userList.add(myUser);
            }while (cursor.moveToNext());
        }
        return userList;
    }

    public List<User> login(String email,String password){
        List<User> userList = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_NAME  + " WHERE "+COLUMN_EMAIL+"="+ email+" AND "+COLUMN_PASSWORD+"="+password;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,new String[]{COLUMN_ID,COLUMN_NAME,COLUMN_EMAIL,COLUMN_PASSWORD},COLUMN_EMAIL+"=? and "+COLUMN_PASSWORD+"=?",new String[]{email,password},null,null,null);

        if (cursor.moveToFirst()){
            do {
                User myUser = new User();
                myUser.setuId(cursor.getInt(0));
                myUser.setName(cursor.getString(1));
                myUser.setEmail(cursor.getString(2));
                myUser.setPassword(cursor.getString(3));

                userList.add(myUser);
            }while (cursor.moveToNext());
        }
        return userList;
    }
}
