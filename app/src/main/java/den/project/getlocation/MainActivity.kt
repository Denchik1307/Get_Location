package den.project.getlocation

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import den.project.getlocation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    lateinit var binding: ActivityMainBinding
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkPermissions()
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        binding.btGetLocation.setOnClickListener {
            getMyLocation()
        }
        binding.btOpenOnMap.setOnClickListener {
            openOnMap()
        }
    }


    private fun checkPermissions(): Boolean {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
            return false
        }
        return true
    }

    @SuppressLint("MissingPermission")
    private fun getMyLocation() {
        var myLocation: String = ""
        fusedLocationProviderClient.lastLocation?.addOnSuccessListener { location ->
            binding.tvLocation.text = "geo:${location.latitude},${location.longitude}"
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    Toast.makeText(this, "OK", Toast.LENGTH_LONG).show()
                }
                Toast.makeText(this, "OUCH", Toast.LENGTH_LONG).show()
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun openOnMap() {
        var myLocation: String = ""
        fusedLocationProviderClient.lastLocation?.addOnSuccessListener { location ->
            myLocation = "geo:" + location.latitude + "," + location.longitude
            Toast.makeText(this, myLocation, Toast.LENGTH_SHORT).show()
            val gmmIntentUri = Uri.parse(myLocation)
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            startActivity(mapIntent)
        }
    }
}





