package com.example.mapsdeneme.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.mapsdeneme.R
import com.example.mapsdeneme.adapter.UsersAdapter
import com.example.mapsdeneme.admin.AdminKullaniciEkleme
import com.example.mapsdeneme.admin.MapsActivity
import com.example.mapsdeneme.databinding.ActivityMainAdminBinding
import com.example.mapsdeneme.model.Place
import com.example.mapsdeneme.roomdb.PlaceDatabase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivityUsers : AppCompatActivity() {
    private lateinit var binding: ActivityMainAdminBinding
    private val compositeDisposable = CompositeDisposable()//

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainAdminBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val db =
            Room.databaseBuilder(applicationContext, PlaceDatabase::class.java, "Places").build()
        val placeDao = db.placeDao()

        compositeDisposable.add(
            placeDao.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handlerResponse)
        )
        setTitle("Duraklar")
    }

    private fun handlerResponse(placeList: List<Place>) {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = UsersAdapter(placeList)
        binding.recyclerView.adapter = adapter
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.user_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId== R.id.user_cikis){
            val intent=Intent(this, LoginPage::class.java)
            startActivity(intent)
            this.finish()
        }

        return super.onOptionsItemSelected(item)
    }



}
