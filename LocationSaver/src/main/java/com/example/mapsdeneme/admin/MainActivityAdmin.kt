package com.example.mapsdeneme.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.mapsdeneme.user.LoginPage
import com.example.mapsdeneme.R
import com.example.mapsdeneme.adapter.PlaceAdapter
import com.example.mapsdeneme.databinding.ActivityMainAdminBinding
import com.example.mapsdeneme.model.Place
import com.example.mapsdeneme.roomdb.PlaceDatabase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivityAdmin : AppCompatActivity() {

    private lateinit var binding: ActivityMainAdminBinding
    private val compositeDisposable =CompositeDisposable()//
    private lateinit var places: ArrayList<Place>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainAdminBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        places=ArrayList()

        val db= Room.databaseBuilder(applicationContext,PlaceDatabase::class.java,"Places").build()
        val placeDao=db.placeDao()

        compositeDisposable.add(
            placeDao.getAll()
                .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handlerResponse)
        )
        setTitle("Admin")

    }
    private fun handlerResponse(placeList : List<Place>){
        binding.recyclerView.layoutManager=LinearLayoutManager(this)
        val adapter =PlaceAdapter(placeList)
        binding.recyclerView.adapter=adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.maps_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId== R.id.durak_ekle){
            val intent=Intent(this, MapsActivity::class.java)
            intent.putExtra("info","new")
            startActivity(intent)
        }
        if (item.itemId==R.id.admin_cikis_yap){
            val intent=Intent(this, LoginPage::class.java)
            startActivity(intent)
            this.finish()
        }
        if (item.itemId==R.id.kullanici_ekle){
            val intent=Intent(this, AdminKullaniciEkleme::class.java)
            startActivity(intent)
            this.finish()
        }

        return super.onOptionsItemSelected(item)
    }
}