package com.example.assignment.UserProfileActivity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.assignment.R

class UserAboutUs : AppCompatActivity() {
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var tvPhone : TextView
    private lateinit var tvEmail : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_about_us)
        //toolbar declaration
        toolbar = findViewById(R.id.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_back)
        toolbar.setNavigationOnClickListener(){
            finish()
        }

        //XML declaration
        tvPhone = findViewById(R.id.tvPhone)
        tvEmail = findViewById(R.id.tvEmail)

        tvPhone.setOnClickListener(){
            checkPermission(Manifest.permission.CALL_PHONE, 1)
        }

        tvEmail.setOnClickListener(){
            val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"+"AppAdmin0104@gmail.com"));
            intent.putExtra(Intent.EXTRA_SUBJECT, "Contact From App User");
            intent.putExtra(Intent.EXTRA_TEXT, "Hi");
            startActivity(intent);
        }

    }


    // Function to check and request permission.
    private fun checkPermission(permission: String, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED) {
            // Requesting the permission
            ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
        } else {
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse("tel:" + "0104123456")
            startActivity(intent)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                Toast.makeText(this, "Phone Permission Denied", Toast.LENGTH_SHORT).show()
            }

    }
    }

}