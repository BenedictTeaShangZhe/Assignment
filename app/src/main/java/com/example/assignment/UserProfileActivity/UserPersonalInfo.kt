package com.example.assignment.UserProfileActivity

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toolbar
import com.example.assignment.R

class UserPersonalInfo : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences
    lateinit var toolbar: Toolbar
    //Below are the Key of login sharedPreferences
    private var PREFS_KEY = "prefs"
    private var PHONE_KEY = "phone"
    //Below are to validate the status of login
    private var phone = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_personal_info)
        toolbar = findViewById(R.id.toolbar)
        toolbar.setNavigationOnClickListener(){
            finish()
        }

    }
}