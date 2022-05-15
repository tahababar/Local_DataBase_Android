package com.depauw.repairshop.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.webkit.WebChromeClient;

import androidx.annotation.Nullable;

import com.depauw.repairshop.RepairWithVehicle;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static DBHelper myInstance;

    private static final String DB_NAME = "repairshop.db";
    private static final int DB_VERSION = 1;

    public static final String TABLE_VEHICLE = "Vehicle";
    public static final String COL_VEHICLE_ID = "vid";
    public static final String COL_VEHICLE_YEAR = "year";
    public static final String COL_VEHICLE_MAKE_AND_MODEL = "make_and_model";
    public static final String COL_VEHICLE_PURCHASE_PRICE = "purchase_price";
    public static final String COL_VEHICLE_IS_NEW = "is_new";

    public static final String TABLE_REPAIR = "Repair";
    public static final String COL_REPAIR_ID = "rid";
    public static final String COL_REPAIR_DATE = "date";
    public static final String COL_REPAIR_COST = "cost";
    public static final String COL_REPAIR_DESCRIPTION = "description";
    public static final String COL_REPAIR_VEHICLE_ID = "vehicle_id";

    private DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static DBHelper getInstance(Context context){
        if (myInstance == null){
            myInstance = new DBHelper(context);
        }
        return myInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE " + TABLE_VEHICLE + " (" +
                COL_VEHICLE_ID + " INTEGER," +
                COL_VEHICLE_YEAR + " TEXT NOT NULL," +
                COL_VEHICLE_MAKE_AND_MODEL + " TEXT NOT NULL," +
                COL_VEHICLE_PURCHASE_PRICE + " REAL NOT NULL," +
                COL_VEHICLE_IS_NEW + " INTEGER," +
                "PRIMARY KEY(" + COL_VEHICLE_ID + " AUTOINCREMENT)" +
                ")";
        sqLiteDatabase.execSQL(sql);

        sql = "CREATE TABLE " + TABLE_REPAIR + " (" +
                COL_REPAIR_ID + " INTEGER," +
                COL_REPAIR_DATE + " TEXT NOT NULL," +
                COL_REPAIR_COST + " REAL NOT NULL," +
                COL_REPAIR_DESCRIPTION + " TEXT NOT NULL," +
                COL_REPAIR_VEHICLE_ID + " INTEGER NOT NULL," +
                "PRIMARY KEY(" + COL_REPAIR_ID + " AUTOINCREMENT)" +
                ")";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {}

    public long insertVehicle(Vehicle v){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COL_VEHICLE_YEAR, v.getYear());
        cv.put(COL_VEHICLE_MAKE_AND_MODEL, v.getMakeAndModel());
        cv.put(COL_VEHICLE_PURCHASE_PRICE, v.getPurchasePrice());
        cv.put(COL_VEHICLE_IS_NEW, v.getIsNew());

        long result = db.insert(TABLE_VEHICLE, null, cv);
        db.close();
        return result;
    }

    public long insertRepair(Repair r){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COL_REPAIR_DATE, r.getDate());
        cv.put(COL_REPAIR_COST, r.getCost());
        cv.put(COL_REPAIR_DESCRIPTION, r.getDescription());
        cv.put(COL_REPAIR_VEHICLE_ID, r.getVehicleId());

        long result = db.insert(TABLE_REPAIR, null, cv);
        db.close();
        return result;
    }

    public List<RepairWithVehicle> getRepairsWithVehicle(String search){
        SQLiteDatabase db = getReadableDatabase();
        String sql = String.format("SELECT * FROM %s INNER JOIN %s ON %s = %s WHERE %s LIKE '%%%s%%'",
                TABLE_REPAIR, TABLE_VEHICLE, COL_REPAIR_VEHICLE_ID, COL_VEHICLE_ID, COL_REPAIR_DESCRIPTION, search);
        //String sql = "SELECT * FROM " + TABLE_REPAIR + " INNER JOIN " + TABLE_VEHICLE + " ON " + COL_REPAIR_VEHICLE_ID + " = " + COL_VEHICLE_ID + " WHERE " + COL_REPAIR_DESCRIPTION + " LIKE '%" + search + "%'";
        Cursor cursor = db.rawQuery(sql, null);

        int idx_v_id = cursor.getColumnIndex(COL_VEHICLE_ID);
        int idx_year = cursor.getColumnIndex(COL_VEHICLE_YEAR);
        int idx_make_and_model = cursor.getColumnIndex(COL_VEHICLE_MAKE_AND_MODEL);
        int idx_purchase_price = cursor.getColumnIndex(COL_VEHICLE_PURCHASE_PRICE);
        int idx_is_new = cursor.getColumnIndex(COL_VEHICLE_IS_NEW);

        int idx_r_id = cursor.getColumnIndex(COL_REPAIR_ID);
        int idx_date = cursor.getColumnIndex(COL_REPAIR_DATE);
        int idx_cost = cursor.getColumnIndex(COL_REPAIR_COST);
        int idx_description = cursor.getColumnIndex(COL_REPAIR_DESCRIPTION);
        int idx_vehicle_id = cursor.getColumnIndex(COL_REPAIR_VEHICLE_ID);

        List<RepairWithVehicle> repairWithVehicles = new ArrayList<>();

        if(cursor.moveToFirst()){
            do{

                int vId = cursor.getInt(idx_v_id);
                String year = cursor.getString(idx_year);
                String make_and_model = cursor.getString(idx_make_and_model);
                float purchase_price = cursor.getFloat(idx_purchase_price);
                int is_new = cursor.getInt(idx_is_new);

                Vehicle thisVehicle = new Vehicle(vId, year, make_and_model, purchase_price, is_new);

                int rId = cursor.getInt(idx_r_id);
                String date = cursor.getString(idx_date);
                float cost = cursor.getFloat(idx_cost);
                String description = cursor.getString(idx_description);
                int vehicleId = cursor.getInt(idx_vehicle_id);

                Repair thisReview = new Repair(rId, date, cost, description, vehicleId);

                RepairWithVehicle thisRepairWithVehicle = new RepairWithVehicle(thisReview, thisVehicle);
                repairWithVehicles.add(thisRepairWithVehicle);

            } while(cursor.moveToNext());
        }
        db.close();
        return repairWithVehicles;
    }

    public List<Vehicle> getAllVehicles(){
        SQLiteDatabase db = getReadableDatabase();

        String sql = "SELECT * FROM " + TABLE_VEHICLE;
        Cursor cursor = db.rawQuery(sql, null);

        int idx_id = cursor.getColumnIndex(COL_VEHICLE_ID);
        int idx_year = cursor.getColumnIndex(COL_VEHICLE_YEAR);
        int idx_make_and_model = cursor.getColumnIndex(COL_VEHICLE_MAKE_AND_MODEL);
        int idx_purchase_price = cursor.getColumnIndex(COL_VEHICLE_PURCHASE_PRICE);
        int idx_is_new = cursor.getColumnIndex(COL_VEHICLE_IS_NEW);

        List<Vehicle> vehicles = new ArrayList<>();

        if(cursor.moveToFirst()){
            do{

                int id = cursor.getInt(idx_id);
                String year = cursor.getString(idx_year);
                String make_and_model = cursor.getString(idx_make_and_model);
                float purchase_price = cursor.getFloat(idx_purchase_price);
                int is_new = cursor.getInt(idx_is_new);

                Vehicle v = new Vehicle(id, year, make_and_model, purchase_price, is_new);
                vehicles.add(v);

            } while(cursor.moveToNext());
        }

        db.close();
        return vehicles;
    }

    public int deleteRepairByRepairId(int repairId){
        SQLiteDatabase db = getWritableDatabase();
        String where = String.format("%s = %s", COL_REPAIR_ID, repairId);
        int numRows = db.delete(TABLE_REPAIR, where, null);
        db.close();
        return numRows;
    }

}
