package com.example.dvoitekh.fitnesstracker

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.widget.Toast
import android.content.pm.PackageManager
import android.Manifest.permission
import android.location.*
import kotlinx.android.synthetic.main.activity_navigation.*
import java.io.IOException
import java.util.*


class MainActivity : Activity(), LocationListener {

    private lateinit var locationManager: LocationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        ActivityCompat.requestPermissions(this, arrayOf(permission.ACCESS_FINE_LOCATION), 1)
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            1 -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    /* CAL METHOD requestLocationUpdates */

                    // Parameters :
                    //   First(provider)    :  the name of the provider with which to register
                    //   Second(minTime)    :  the minimum time interval for notifications,
                    //                         in milliseconds. This field is only used as a hint
                    //                         to conserve power, and actual time between location
                    //                         updates may be greater or lesser than this value.
                    //   Third(minDistance) :  the minimum distance interval for notifications, in meters
                    //   Fourth(listener)   :  a {#link LocationListener} whose onLocationChanged(Location)
                    //                         method will be called for each location update

                    /********* After registration onLocationChanged method   */
                    /********* called periodically after each 3 sec  */
                    locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 10.toFloat(), this)
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return
            }
        }// other 'case' lines to check for other
        // permissions this app might request
    }

    /************* Called after each 3 sec  */
    override fun onLocationChanged(location: Location) {
        val longitude = "Longitude: " + location.getLongitude()
        val latitude = "Latitude: " + location.getLatitude()
        var cityName: String? = null
        val gcd = Geocoder(baseContext, Locale.getDefault())
        val addresses: List<Address>
        try {
            addresses = gcd.getFromLocation(location.latitude, location.longitude, 1)
            if (addresses.isNotEmpty()) {
                System.out.println(addresses[0].getLocality())
                cityName = addresses[0].locality + ", " + addresses[0].subLocality
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        val s = longitude + "\n" + latitude + "\n\nMy Current City is: " + cityName
        locationTxt.text = s
    }

    override fun onProviderDisabled(provider: String) {

        /******** Called when User off Gps  */

        Toast.makeText(baseContext, "Gps turned off ", Toast.LENGTH_LONG).show()
    }

    override fun onProviderEnabled(provider: String) {

        /******** Called when User on Gps   */

        Toast.makeText(baseContext, "Gps turned on ", Toast.LENGTH_LONG).show()
    }

    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
        // TODO Auto-generated method stub

    }
}
