package com.example.mapsdeneme.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.mapsdeneme.R
import com.example.mapsdeneme.kullaniciIslemleri.Kullanici
import com.example.mapsdeneme.kullaniciIslemleri.KullaniciDatabaseHelper
import kotlinx.android.synthetic.main.activity_admin_kullanici_ekleme.*
import kotlinx.android.synthetic.main.activity_login_page.kullaniciAdi

class AdminKullaniciEkleme : AppCompatActivity() {
    val context= this
    var db=KullaniciDatabaseHelper(context)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_kullanici_ekleme)
        setTitle("Kullanıcı Ekleme")

    }
    fun kullaniciEkle(view: View){
        var girilenKullaniciAdi=kullaniciAdi.text.toString()
        var girilenSifre=kullaniciEklesif.text.toString()
        if(girilenKullaniciAdi.isNotEmpty() && girilenSifre.isNotEmpty()){
            var kullanici= Kullanici(girilenKullaniciAdi,girilenSifre)
            db.insertData(kullanici)
            val intent= Intent(this, MainActivityAdmin::class.java)  //kullanıcı mapsine
            startActivity(intent)
        }else{
            Toast.makeText(context,"Boş alanları doldurun.", Toast.LENGTH_LONG).show()
        }


    }
}