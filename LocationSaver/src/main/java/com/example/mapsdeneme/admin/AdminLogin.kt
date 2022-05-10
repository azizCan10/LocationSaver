package com.example.mapsdeneme.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.mapsdeneme.R
import com.example.mapsdeneme.admin.MainActivityAdmin
import kotlinx.android.synthetic.main.activity_admin_login.*

class adminLogin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_login)
        setTitle("Admin")
    }
    fun adminGiris (view : View){
        var girilenAdminKA= adminKa.text.toString()
        var girilenAdminSifre= adminSifre.text.toString()

        if(girilenAdminKA=="admin" && girilenAdminSifre=="admin"){
            val intent = Intent(this, MainActivityAdmin::class.java)  //kullanıcı mapsine
            startActivity(intent)
            this.finish()
        }else {
            Toast.makeText(this, "Geçersiz kullanıcı adı veya şifre.", Toast.LENGTH_SHORT)
                .show()
        }

    }
}