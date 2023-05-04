package com.example.connectfood

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import android.view.animation.AnimationUtils
import android.widget.TextView
import java.util.*

@Suppress("DEPRECATION")
class DonorScreenOneActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donor_screen_one)

        val SPLASH_DELAY = 5000 // 3 seconds
        val TEXT_DELAY = 2500 // 3 seconds
        val intentionMessageTitle: TextView = findViewById(R.id.intentionMessageTitle)
        val intentionMessageSub: TextView = findViewById(R.id.intentionMessageSub)

        val animOut = AnimationUtils.loadAnimation(this, R.anim.fade_out)
        val animIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)

        val timerOne = Handler()
        val timerTwo = Handler()

        timerOne.postDelayed({
            intentionMessageTitle.startAnimation(animOut)
            timerOne.postDelayed({
                intentionMessageTitle.text = "Estamos quase lá"
                intentionMessageTitle.startAnimation(animIn)
            }, animOut.duration)
        }, TEXT_DELAY.toLong())

        timerOne.postDelayed({
            intentionMessageSub.startAnimation(animOut)
            timerOne.postDelayed({
                intentionMessageSub.text = "Estamos listando os locais onde recebem doações"
                intentionMessageSub.startAnimation(animIn)
            }, animOut.duration)
        }, TEXT_DELAY.toLong())

        timerTwo.postDelayed({
            val intent = Intent(this, DonorRecipientsListActivity::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_DELAY.toLong())
    }
}

//class DonorScreenOneActivity : AppCompatActivity() {
//
//    private lateinit var locationManager: LocationManager
//    private lateinit var locationListener: LocationListener
//    private lateinit var locationTextView: TextView
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_donor_screen_one)
//
//        locationTextView = findViewById(R.id.locationTextView)
//        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
//
//        locationListener = object : LocationListener {
//            override fun onLocationChanged(location: Location) {
//                val geocoder = Geocoder(this@DonorScreenOneActivity, Locale.getDefault())
//                val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
//                val address = addresses?.get(0)?.getAddressLine(0)
//                locationTextView.text = "Endereço: $address"
//            }
//
//            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
//
//            override fun onProviderEnabled(provider: String) {}
//
//            override fun onProviderDisabled(provider: String) {}
//        }
//    }
//
//    override fun onStart() {
//        super.onStart()
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
//        } else {
//            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, locationListener)
//        }
//    }
//
//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode == 1 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, locationListener)
//            }
//        }
//    }
//
//    override fun onStop() {
//        super.onStop()
//        locationManager.removeUpdates(locationListener)
//    }
//}
