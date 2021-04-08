package com.example.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseOpenHelper extends SQLiteOpenHelper {

    public static final String CUSTOMER = "CUSTOMER";
    public static final String CUSTOMER_NAME = "CUSTOMER_NAME";
    public static final String ID = "ID";
    public static final String CUSTOMER_AGE = "CUSTOMER_AGE";
    public static final String ACTIVE_CUSTOMER = "ACTIVE_CUSTOMER";

    public DataBaseOpenHelper(@Nullable Context context) {
        super(context, "MyDatabase", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String  queryToCreateTable = "CREATE TABLE " + CUSTOMER + " (" + ID + " INTEGER PRIMARY KEY  AUTOINCREMENT, " + CUSTOMER_NAME + " TEXT, " + CUSTOMER_AGE + " INT, " + ACTIVE_CUSTOMER + " BOOL )";
        db.execSQL(queryToCreateTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public  boolean addOne(Customer customer){

        SQLiteDatabase db  = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(CUSTOMER_NAME,customer.getName());
        cv.put(CUSTOMER_AGE,customer.getAge());
        cv.put(ACTIVE_CUSTOMER,customer.isCheck());

        long insert = db.insert(CUSTOMER, null, cv);

        if(insert == -1)
            return  false;

        return true;
    }

    public List<Customer> getAll(){

        List<Customer> list = new ArrayList<>();

        String getTableData =  "SELECT * FROM " + CUSTOMER;

        SQLiteDatabase readData = this.getReadableDatabase();

        Cursor cursor =  readData.rawQuery(getTableData, null);

        if (cursor.moveToFirst()){
            do {
                int customerId  = cursor.getInt(0);
                String customerName  = cursor.getString(1);
                int customerAge  = cursor.getInt(2);
                boolean active  =  cursor.getInt(3) == 1 ? true : false;
                list.add(new Customer(customerId,customerName,customerAge,active));
            }
            while (cursor.moveToNext());
        }

        cursor.close();
        readData.close();
        return list;
    }

    public boolean deleteEntry(Customer customer){
        SQLiteDatabase db  =  this.getWritableDatabase();

        String queryForDel =  "DELETE FROM " + CUSTOMER + " WHERE " +  ID + " = " + customer.getId();

        Cursor cursor = db.rawQuery(queryForDel, null);

        if(cursor.moveToFirst())
            return true;

        return false;
    }


}
