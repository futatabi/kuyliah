package com.biastomo.crew.kuyliah;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by andy on 26/11/2017.
 */

public class DataHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "kuyliahdb.db";
    private static final int DATABASE_VERSION = 1;
    public DataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        String sql = "create table tugas(namatugas text PRIMARY KEY, tgl text, waktu text, deskripsi text null); INSERT INTO tugas (namatugas, tgl, waktu, deskripsi) VALUES ('Cari Jurnal', '28-11-2003', '14:33','jurnal tentang penelitian untuk berskripsi');";
        String sql2 = "create table jadwal(namamk text PRIMARY KEY, tgl text, waktu text, ruang text null, dosen text null, sks text null); INSERT INTO tugas (namamk, tgl, waktu, ruang, dosen, sks) VALUES ('Artificial Intelligence', '28-11-2003', '14:33','LabKom','Wibowo','2');";
        Log.d("Data", "onCreate: " + sql+ "&"+sql2);
        db.execSQL(sql);
        db.execSQL(sql2);
    }


    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("drop table tugas; drop table jadwal;");
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
