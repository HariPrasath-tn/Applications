package com.rio.worldweather.view

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.rio.worldweather.R

class WeatherActivity : AppCompatActivity() {
    private val PERMISSION_REQUEST_ACCESS_CODE=100
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        getCurrentLocation()
    }

    /**
     * [getCurrentLocation] is the method to fetch the location.
     * it handles the permissions and location, network availability
     */
    private fun getCurrentLocation(){
        if(!checkLocationPermission()){
            requestPermission(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION))
        }else if(!checkNetworkPermission()){
            requestPermission(arrayOf(Manifest.permission.INTERNET))
        }else{
            if(!checkLocationEnabled()){
                val title = "Location Not Available"
                val message:String = "This Application requires location access, Enable it in settings. \n\npress \"Yes\" to goto settings " +
                        "\n(Warning: Pressing \"No\" will close the application)"
                showAlertDialog(title, message, Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            }else if(!checkInternetEnabled()){
                val title = "Internet Not Available"
                val message:String = "This Application requires internet access, Enable it in settings. \n\npress \"Yes\" to goto settings " +
                        "\n(Warning: Pressing \"No\" will close the application)"
                showAlertDialog(title, message, Settings.ACTION_NETWORK_OPERATOR_SETTINGS)
            }
        }
    }

    /**
     * [requestPermission] method requests the user for the permissions provided in the array as a
     * parameter to this method
     * @param permissionsRequired of type array of String representing the permission required by
     * the device
     */
    private fun requestPermission(permissionsRequired:Array<String>){
        ActivityCompat.requestPermissions(this,
            permissionsRequired,PERMISSION_REQUEST_ACCESS_CODE
        )
    }

    /**
     * [checkLocationPermission]method verifies whether the location permission is provided by the
     * user for the application if provided then returns true else false
     * @return Boolean with respect to the permission provided or not
     */
    private fun checkLocationPermission():Boolean{
        // checking the course and fine location permission
        if(ActivityCompat.checkSelfPermission(this.applicationContext, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this.applicationContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager. PERMISSION_GRANTED
        ){
            return true
        }
        return false
    }

    /**
     * [checkLocationPermission]method verifies whether the network permission is provided by the
     * user for the application if provided then returns true else false
     * @return Boolean with respect to the permission provided or not
     */
    private fun checkNetworkPermission():Boolean{
        // checking the internet Permission
        if(ActivityCompat.checkSelfPermission(this.applicationContext, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED)
            return true
        return false
    }

    /**
     * [checkInternetEnabled] method verifies whether the internet has been enabled at the instant
     * and returns true if enabled else false
     * @return Boolean
     */
    private fun checkInternetEnabled():Boolean{
        val internetManager: ConnectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return internetManager.activeNetwork != null
    }

    /**
     * [checkInternetEnabled] method verifies whether the location has been enabled at the instant
     * and returns true if enabled else false
     * @return Boolean
     */
    private fun checkLocationEnabled():Boolean{
        val locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    /*
     * override method to get permissions required by the application from the user
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(!(grantResults.isEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)){
            getCurrentLocation()
        }else if(grantResults.isEmpty()){
            var title = "Location permission required"
            var message = "Location permission is must for the application to run. \n\n press \"Yes\" to goto settings" +
                    "\n(Warning: pressing \"No\" will close the application)"
            showAlertDialog(title, message, Settings.ACTION_APPLICATION_SETTINGS)
        }
    }

    /**
     * [showAlertDialog] method to show alert dialog box to redirect to the setting on pressing "yes",
     * on pressing "No" will close the application
     *
     * @param title representing the title of the dialog box
     * @param message representing the message of the dialog box
     * @param stringIntent representing the intent to be initialized
     */
    private fun showAlertDialog(title:String, message:String, stringIntent:String){
        var alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle(title)
        alertDialog.setMessage(message)
        alertDialog.setCancelable(true)
        alertDialog.setPositiveButton("Yes"){_,_ ->
            Intent(stringIntent).also {
                startActivity(it)
            }
        }
        alertDialog.setNegativeButton("No"){_,_ ->
            finish()
        }
        alertDialog.show()
    }

    override fun onRestart() {
        super.onRestart()
        // restarting fetching the location once the activity resumes
        getCurrentLocation()
    }
}