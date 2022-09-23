package com.example.assignment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.assignment.fragment.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    //realtime database initialization (Copy)
    private lateinit var myDB: FirebaseDatabase
    private lateinit var myRef: DatabaseReference
    private lateinit var auth : FirebaseAuth

    private val homepageFragment = HomepageFragment()
    private val searchingFragment = SearchingFragment()
    private val foodBankFragment = FoodBankFragment()
    private val userprofileFragment = UserProfileFragment()
    private val adminProfileFragment = AdminProfile()
    lateinit var bottom_navigation : BottomNavigationView
    // Below are used to do the login function
    private lateinit var sharedPreferences: SharedPreferences
    //Below are the Key of login sharedPreferences
    private var PREFS_KEY = "prefs"
    private var PHONE_KEY = "phone"
    //Below are to validate the status of login
    private var phone = ""

    //XML declaration
    private lateinit var btnLogout:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Database declaration(Copy)
        myDB = FirebaseDatabase.getInstance()
        //reference = database table
        myRef = myDB.getReference("User")
        auth = FirebaseAuth.getInstance()
        login("benedictassignment@gmail.com","ThisIsASecret!?")

        bottom_navigation = findViewById(R.id.bottom_navigation)

        //The default page is homepage and the default navigation button is homepage
        bottom_navigation.selectedItemId=R.id.ic_homepage
        replaceFragment(homepageFragment)

        //Initialized the sharedPreferences
        sharedPreferences = getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)



    }

    override fun onResume() {
        super.onResume()
        //Read the email & password from sharedPreferences, if not null means login already
        phone = sharedPreferences.getString(PHONE_KEY, "").toString()
        //Used to navigate to different fragment
        bottom_navigation.setOnItemSelectedListener{
            when(it.itemId){
                R.id.ic_homepage->replaceFragment(homepageFragment)
                R.id.ic_search->replaceFragment(searchingFragment)
                R.id.ic_foodbank->replaceFragment(foodBankFragment)
                R.id.ic_userprofile->{
                    if(phone == "0104123456"){
                        //if login with admin's account
                        replaceFragment(adminProfileFragment)}
                    else if (phone != "") {
                        //if already login then go to the fragment
                        replaceFragment(userprofileFragment)
                    } else{
                        //if haven't login then go to the login activity
                        val intent = Intent(this, UserLogin::class.java)
                        startActivity(intent)
                    }
                }
            }
            true
        }

    }

    override fun finish() {
        super.finish()
        //Disable configure firebase after closing the app
        auth.signOut()
    }

    private fun replaceFragment(fragment: Fragment){
        if(fragment!=null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container,fragment)
            transaction.commit()
        }
    }

    //To authenticate in firebase
    private fun  login(email: String, psw: String) {
        auth.signInWithEmailAndPassword(email, psw)
            .addOnSuccessListener {
                val user = auth.currentUser
            }
            .addOnFailureListener { e ->
                Toast.makeText(applicationContext,e.toString(),Toast.LENGTH_LONG).show()
            }
    }
}