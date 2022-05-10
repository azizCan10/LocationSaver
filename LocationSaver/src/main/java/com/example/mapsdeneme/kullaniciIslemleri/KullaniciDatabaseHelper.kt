package com.example.mapsdeneme.kullaniciIslemleri

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

val database_name="KullaniciVeritabani"
val table_name="Kullanici"
val col_kullaniciadi="kullaniciAdi"
val col_sifre ="sifre"
val col_id="id"

class KullaniciDatabaseHelper (var context: Context):SQLiteOpenHelper(context, database_name,null,1) {
    override fun onCreate(db: SQLiteDatabase?) {

        var createTable = " CREATE TABLE "+ table_name+"("+
                col_id+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                col_kullaniciadi+" VARCHAR(256),"+
                col_sifre+" VARCHAR(256))"

        db?.execSQL(createTable)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }

    fun insertData(kullanici: Kullanici){
        val db =this.writableDatabase
        val cv = ContentValues()

        cv.put(col_kullaniciadi,kullanici.kullaniciAdi)
        cv.put(col_sifre,kullanici.sifre)
        var sonuc=db.insert(table_name,null,cv)
        if (sonuc==(-1).toLong()){
            Toast.makeText(context,"İşlem Başarısız.",Toast.LENGTH_LONG).show()
        }else
        {
            Toast.makeText(context,"Kullanıcı Eklendi.",Toast.LENGTH_LONG).show()
        }
    }
    fun readData(): MutableList<Kullanici> {
        var liste:MutableList<Kullanici> = ArrayList()
        val db = this.readableDatabase
        var sorgu= "Select * from "+ table_name
        var sonuc= db.rawQuery(sorgu, null)
        if(sonuc.moveToFirst()){
            do {
                var kullanici = Kullanici()
                kullanici.id = sonuc.getString(sonuc.getColumnIndex(col_id)).toInt()
                kullanici.kullaniciAdi = sonuc.getString(sonuc.getColumnIndex(col_kullaniciadi))
                kullanici.sifre = sonuc.getString(sonuc.getColumnIndex(col_sifre))

            liste.add(kullanici)

            }while (sonuc.moveToNext())
        }
            sonuc.close()
            db.close()
        return liste


    }

}