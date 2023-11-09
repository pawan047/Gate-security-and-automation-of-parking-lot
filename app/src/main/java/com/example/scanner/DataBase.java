package com.example.scanner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;



public class DataBase extends SQLiteOpenHelper {
    private static final String name="dbms";
    private static  final int version=1;
    private static final String TABLE_NAME = "users";

    // Define table columns
    public  DataBase( Context context){
        super(context,name,null,version);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){
        try{
        String qry1="CREATE TABLE IF NOT EXISTS users(username text,email text, password text)";
        sqLiteDatabase.execSQL(qry1);
        }
        catch (SQLException e){
            Log.e("DatabaseCreationError", "Error creating the database: " + e.getMessage());

        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1){

    }
    public void register(String username, String email, String password){
        try (SQLiteDatabase database = this.getWritableDatabase()) {
            ContentValues values= new ContentValues();
            values.put("username",username);
            values.put("email",email);
            values.put("password",password);
            database.insert(TABLE_NAME,null,values);
        } catch (Exception e) {
            Log.e("DatabaseCreationError", "Error creating the database: " + e.getMessage());
        }
    }

}
