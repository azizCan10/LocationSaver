package com.example.mapsdeneme.admin

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.room.Room
import com.example.mapsdeneme.R

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.mapsdeneme.databinding.ActivityMapsBinding
import com.example.mapsdeneme.model.Place
import com.example.mapsdeneme.roomdb.PlaceDao
import com.example.mapsdeneme.roomdb.PlaceDatabase
import com.google.android.material.snackbar.Snackbar
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MapsActivity : AppCompatActivity(), OnMapReadyCallback,GoogleMap.OnMapLongClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var locationManager :LocationManager
    private lateinit var locationListener: LocationListener
    private lateinit var permissionLauncher : ActivityResultLauncher<String>
    private var selectedLongitude : Double? =null
    private var selectedLatitude : Double? =null
    private  lateinit var db : PlaceDatabase
    private lateinit var placeDao: PlaceDao
    val compositeDisposable = CompositeDisposable()   //rxjava icin gerekli  --- kullan,at
    var placeFromMain : Place? =null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        registerLauncher()
        selectedLatitude=0.0
        selectedLongitude=0.0
        db= Room.databaseBuilder(applicationContext,PlaceDatabase::class.java,"Places").build()
        placeDao=db.placeDao()
        setTitle("Durak Ekleme")
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnMapLongClickListener(this)

        val intent=intent
        val info=intent.getStringExtra("info")

        if (info=="new"){
            binding.saveButton.visibility=View.VISIBLE
            binding.deleteButton.visibility=View.GONE


            locationManager=this.getSystemService(LOCATION_SERVICE) as LocationManager
            locationListener=object : LocationListener{
                override fun onLocationChanged(location: Location) {

                }
            }
            if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){

                if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION)){
                    Snackbar.make(binding.root,"Konum icin izin gerekli.",Snackbar.LENGTH_INDEFINITE).setAction("Izin Ver"){

                        permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                    }.show()

                }else{
                    permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                }

            }else{
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,100,10f,locationListener)

                val lastLocation =locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                if (lastLocation != null){
                    val lastUserLocation =LatLng(lastLocation.latitude,lastLocation.longitude)
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastUserLocation,10f))
                }
                mMap.isMyLocationEnabled=true

            }

        }else{
        mMap.clear()
            placeFromMain=intent.getSerializableExtra("selectedPlace") as? Place
            placeFromMain?.let {
                val latLng=LatLng(it.latitude,it.longitude)
                mMap.addMarker(MarkerOptions().position(latLng).title(it.name))
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,12f))
                binding.placeText.setText(it.name)
                 binding.saveButton.visibility=View.GONE
                binding.deleteButton.visibility=View.VISIBLE

            }

        }
    }
    private fun registerLauncher(){
        permissionLauncher=registerForActivityResult(ActivityResultContracts.RequestPermission()){result ->
            if (result){
                if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 10f, locationListener)
                    val lastLocation =locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                    if (lastLocation != null){
                        val lastUserLocation =LatLng(lastLocation.latitude,lastLocation.longitude)
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastUserLocation,10f))
                    }
                }
            }else{
                Toast.makeText(this@MapsActivity,"Izin Gerekli.",Toast.LENGTH_LONG).show()

            }

        }
    }

    override fun onMapLongClick(p0: LatLng) {
        mMap.clear()
        mMap.addMarker(MarkerOptions().position(p0))
        selectedLatitude=p0.latitude
        selectedLongitude=p0.longitude

    }
    fun save(view: View){
        val place = Place(binding.placeText.text.toString(),selectedLatitude!!,selectedLongitude!!)
        compositeDisposable.add(
            placeDao.insert(place)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handlerResponse)
        )
    }
    private fun handlerResponse(){
        val intent= Intent(this, MainActivityAdmin::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
    fun delete(view: View){

        placeFromMain?.let {
            compositeDisposable.add(placeDao.delete(it).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(this::handlerResponse))
        }


    }
}