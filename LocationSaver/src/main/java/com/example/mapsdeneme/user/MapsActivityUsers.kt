package com.example.mapsdeneme.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import com.example.mapsdeneme.R

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.mapsdeneme.databinding.ActivityMapsUsersBinding
import com.example.mapsdeneme.model.Place
import com.example.mapsdeneme.roomdb.PlaceDao
import com.example.mapsdeneme.roomdb.PlaceDatabase
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.PolylineOptions

class MapsActivityUsers : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsUsersBinding

    private var selectedLongitude : Double? =null
    private var selectedLatitude : Double? =null
    private  lateinit var db : PlaceDatabase
    private lateinit var placeDao: PlaceDao
    var placeFromMain : Place? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        selectedLatitude=0.0
        selectedLongitude=0.0
        db= Room.databaseBuilder(applicationContext, PlaceDatabase::class.java,"Places").build()
        placeDao=db.placeDao()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        placeFromMain=intent.getSerializableExtra("selectedPlace2") as? Place
        placeFromMain?.let {
            val latLng=LatLng(it.latitude,it.longitude)
            val okul=LatLng(40.8222319,29.9209847)
            mMap.addMarker(MarkerOptions().position(latLng).title(it.name))
            mMap.addMarker(MarkerOptions().position(okul).icon(BitmapDescriptorFactory.fromResource(
                R.drawable.school
            )).title("KOU"))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,12f))

    }

        val polyline1 = googleMap.addPolyline(
            PolylineOptions()
            .clickable(true)
            .add(
                LatLng(-35.016, 143.321),
                LatLng(-34.747, 145.592),
                LatLng(-34.364, 147.891),
                LatLng(-33.501, 150.217),
                LatLng(-32.306, 149.248),
                LatLng(-32.491, 147.309)))

}}