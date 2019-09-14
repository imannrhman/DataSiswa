package com.belajar.datasiswa;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "DataSiswa";
    private static final String TABLE_NAME = "tbl_user";
    private static final String KEY_NOMOR = "nomor",KEY_NAMA = "nama",KEY_DATE = "tanggallahir",KEY_JENDER="jeniskelamin",KEY_ADDRES ="alamat";


    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createUserTable = "CREATE TABLE "+ TABLE_NAME
                + "("+KEY_NOMOR+" INTEGER PRIMARY KEY, "
                + KEY_NAMA + " TEXT, "
                + KEY_DATE + " TEXT, "
                + KEY_JENDER + " TEXT, "
                + KEY_ADDRES + " TEXT" + ")";
        sqLiteDatabase.execSQL(createUserTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        String sql = ("drop table if exists " + TABLE_NAME);
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }

    public void insert (Siswa siswa){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NOMOR,siswa.getNomor());
        values.put(KEY_NAMA,siswa.getNama());
        values.put(KEY_DATE,siswa.getTanggalLahir());
        values.put(KEY_JENDER,siswa.getJenisKelamin());
        values.put(KEY_ADDRES,siswa.getAlamat());
        db.insert(TABLE_NAME,null,values);
    }

    public List<Siswa> selectUserData(){
        ArrayList<Siswa> userList = new ArrayList<Siswa>();

        SQLiteDatabase db = getReadableDatabase();

        String[] columns = {KEY_NOMOR,KEY_NAMA,KEY_DATE,KEY_JENDER,KEY_ADDRES};

        Cursor c = db.query(TABLE_NAME,columns,null,null,null,null,null);

        while (c.moveToNext()){
            int nomor = c.getInt(0);
            String nama = c.getString(1);
            String tglLahir = c.getString(2);
            String jenisKelamin = c.getString(3);
            String alamat = c.getString(4);

            Siswa siswa = new Siswa();
            siswa.setNomor(nomor);
            siswa.setNama(nama);
            siswa.setTanggalLahir(tglLahir);
            siswa.setJenisKelamin(jenisKelamin);
            siswa.setAlamat(alamat);
            userList.add(siswa);
        }

        return userList;
    }
    public  void delete(int nomor){
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = KEY_NOMOR+"='"+nomor+"'";
        db.delete(TABLE_NAME,whereClause,null); }

    public  void update(Siswa siswa){
        SQLiteDatabase db = getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAMA,siswa.getNama());
        values.put(KEY_DATE,siswa.getTanggalLahir());
        values.put(KEY_JENDER,siswa.getJenisKelamin());
        values.put(KEY_ADDRES,siswa.getAlamat());
        String whereClause = KEY_NOMOR+"='"+siswa.getNomor()+"'";
        db.update(TABLE_NAME,values,whereClause,null);
    }
}
