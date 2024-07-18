package com.example.scanner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import androidx.annotation.Nullable;


public class DataBase extends SQLiteOpenHelper {
    private static final String name="dbms";
    private static  final int version=1;
    private static final String TABLE_NAME = "users";
    private static final String TABLE_CARS = "cars";


    // Columns for cars table
    private static final String CARS_COL_CAR_NUMBER = "car_number";
    private static final String CARS_COL_PHONE_NUMBER = "phone_number";
    private static final String CARS_COL_OWNER_NAME = "owner_name";
    private static final String CARS_COL_ADDRESS = "address";

    public DataBase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DataBase(Context context) {
        super(context, name, null, version);
    }

    // Define table columns

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){

        String qry1="create table  users(username text,email text, password text)";
        sqLiteDatabase.execSQL(qry1);

        String createCarsTableQuery ="create table cars(car_number text,phone_number text,owner_name text,address text)";
        sqLiteDatabase.execSQL(createCarsTableQuery);

    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1){

    }
    public void register(String username, String email, String password){

            ContentValues values= new ContentValues();
            SQLiteDatabase database=getWritableDatabase();
            values.put("username",username);
            values.put("email",email);
            values.put("password",password);
            database.insert("users",null,values);
            database.close();

    }
    public void addCar(String carNumber, String phoneNumber, String ownerName, String address) {
        ContentValues values = new ContentValues();
        SQLiteDatabase database = getWritableDatabase();

        values.put("car_number", carNumber);
        values.put("phone_number", phoneNumber);
        values.put("owner_name", ownerName);
        values.put("address", address);

        database.insert("cars", null, values);
        database.close();
    }

    public boolean isDetailsSaved(String phoneNumber) {
        SQLiteDatabase db = this.getReadableDatabase();
        boolean saved = false;

        String[] projection = {"phone_number"};
        String selection = "phone_number = ?";
        String[] selectionArgs = {phoneNumber};

        Cursor cursor = db.query("cars", projection, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            saved = true;
        }

        if (cursor != null) {
            cursor.close();
        }

        db.close();
        return saved;
    }

    public int isCarPresent(String phoneNumber) {
        SQLiteDatabase db = this.getReadableDatabase();
        int result = 0;

        String[] projection = {"phone_number"};
        String selection = "phone_number = ?";
        String[] selectionArgs = {phoneNumber};

        Cursor cursor = db.query("cars", projection, selection, selectionArgs, null, null, null);

        if (cursor != null) {
            result = cursor.getCount();
            cursor.close();
        }

        db.close();
        return result;
    }

    public int login2(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        int result = 0;

        String[] projection = {"username"};
        String selection = "username = ? and password = ?";
        String[] selectionArgs = {username, password};

        Cursor cursor = db.query("users", projection, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            result = cursor.getCount();
            cursor.close();
        }

        db.close();
        return result;
    }


    // Method to fetch phone number based on car number
    public String getPhoneNumber(String carNumber) {
        SQLiteDatabase db = this.getReadableDatabase();
        String phoneNumber = null;

        String[] projection = {CARS_COL_PHONE_NUMBER};
        String selection = CARS_COL_CAR_NUMBER + " = ?";
        String[] selectionArgs = {carNumber};

        Cursor cursor = db.query(TABLE_CARS, projection, selection, selectionArgs, null, null, null);

        try {
            if (cursor != null && cursor.moveToFirst()) {
                int phoneNumberIndex = cursor.getColumnIndexOrThrow(CARS_COL_PHONE_NUMBER);
                phoneNumber = cursor.getString(phoneNumberIndex);
            }
        } catch (IllegalArgumentException e) {
            // Handle the exception (e.g., log, throw, or handle gracefully)
            Log.e("DATABASE", "Error retrieving phone number: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return phoneNumber;
    }
    //car number
    public String getCarNumber(String phoneNumber) {
        SQLiteDatabase db = this.getReadableDatabase();
        String carNumber = null;

        String[] projection = {"car_number"};
        String selection = "phone_number = ?";
        String[] selectionArgs = {phoneNumber};

        Cursor cursor = db.query("cars", projection, selection, selectionArgs, null, null, null);

        try {
            if (cursor != null && cursor.moveToFirst()) {
                int carNumberIndex = cursor.getColumnIndexOrThrow("car_number");
                carNumber = cursor.getString(carNumberIndex);
            }
        } catch (IllegalArgumentException e) {
            // Handle the exception (e.g., log, throw, or handle gracefully)
            Log.e("DATABASE", "Error retrieving car number: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return carNumber;
    }
    // Method to fetch owner name based on car number
    public String getOwnerName(String phoneNumber) {
        SQLiteDatabase db = this.getReadableDatabase();
        String ownerName = null;

        String[] projection = {"owner_name"};
        String selection = "phone_number= ?";
        String[] selectionArgs = {phoneNumber};

        Cursor cursor = db.query("cars", projection, selection, selectionArgs, null, null, null);

        try {
            if (cursor != null && cursor.moveToFirst()) {
                int ownerNameIndex = cursor.getColumnIndexOrThrow("owner_name");
                ownerName = cursor.getString(ownerNameIndex);
            }
        } catch (IllegalArgumentException e) {
            // Handle the exception (e.g., log, throw, or handle gracefully)
            Log.e("DATABASE", "Error retrieving owner name: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return ownerName;
    }

}
