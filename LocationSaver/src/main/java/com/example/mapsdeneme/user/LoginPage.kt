package com.example.mapsdeneme.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.mapsdeneme.R
import com.example.mapsdeneme.view.adminLogin
import com.example.mapsdeneme.kullaniciIslemleri.KullaniciDatabaseHelper
import kotlinx.android.synthetic.main.activity_login_page.*

class LoginPage : AppCompatActivity() {
    val context= this
    var db=KullaniciDatabaseHelper(context)
    var kayitliMi : Boolean= false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)
        setTitle("Giriş Yap")
    }

    fun girisYap(view: View) {

        var girilenKullaniciAdi = kullaniciAdi.text.toString()
        var girilenSifre = kullaniciAdi.text.toString()

        var data = db.readData()
        for (i in 0 until data.size) {
            if (data.get(i).kullaniciAdi == girilenKullaniciAdi && data.get(i).sifre == girilenSifre) {
                kayitliMi = true
            }}
            if (kayitliMi == true) {
                val intent = Intent(this, MainActivityUsers::class.java)  //kullanıcı mapsine
                startActivity(intent)

                kayitliMi = false
                this.finish()
            } else {
                Toast.makeText(this, "Geçersiz kullanıcı adı veya şifre.", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        fun adminGecis(view: View) {
            val intent = Intent(this, adminLogin::class.java)  //kullanıcı mapsine
            startActivity(intent)

        }



}