package com.example.assignment.UserProfileActivity

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.assignment.R
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UserPersonalInfo : AppCompatActivity() {
    //realtime database initialization (Copy)
    private lateinit var myDB: FirebaseDatabase
    private lateinit var myRef: DatabaseReference
    private lateinit var auth : FirebaseAuth

    lateinit var sharedPreferences: SharedPreferences
    lateinit var toolbar: androidx.appcompat.widget.Toolbar
    //Below are the Key of login sharedPreferences
    private var PREFS_KEY = "prefs"
    private var PHONE_KEY = "phone"
    //Below are to validate the status of login
    private var phone = ""

    //XML initialization
    private lateinit var layout_UserName:TextInputLayout
    private lateinit var layout_ICNumber:TextInputLayout
    private lateinit var layout_Gender:TextInputLayout
    private lateinit var layout_Address:TextInputLayout
    private lateinit var layout_Birthday:TextInputLayout
    private lateinit var layout_Status:TextInputLayout
    private lateinit var tvUserName:TextView
    private lateinit var tvICNumber:TextView
    private lateinit var tvGender:TextView
    private lateinit var tvAddress:TextView
    private lateinit var tvBirthday:TextView
    private lateinit var tvStatus:TextView




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_personal_info)

        //toolbar declaration
        toolbar = findViewById(R.id.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_back)
        toolbar.setNavigationOnClickListener(){
            finish()
        }

        //XML declaration
        layout_UserName = findViewById(R.id.layout_UserName)
        layout_ICNumber = findViewById(R.id.layout_ICNumber)
        layout_Gender = findViewById(R.id.layout_Gender)
        layout_Address = findViewById(R.id.layout_Address)
        layout_Birthday = findViewById(R.id.layout_Birthday)
        layout_Status = findViewById(R.id.layout_Status)
        tvUserName = findViewById(R.id.tvUserName)
        tvICNumber = findViewById(R.id.tvICNumber)
        tvGender = findViewById(R.id.tvGender)
        tvAddress = findViewById(R.id.tvAddress)
        tvBirthday = findViewById(R.id.tvBirthday)
        tvStatus = findViewById(R.id.tvStatus)


        //Database declaration(Copy)
        myDB = FirebaseDatabase.getInstance()
        //reference = database table
        myRef = myDB.getReference("User")

        sharedPreferences = getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)
        phone = sharedPreferences.getString(PHONE_KEY, "").toString()

        //shows the user information in text view
        myRef.child(phone).get().addOnSuccessListener(){
            tvUserName.text = it.child("name").value.toString()
            tvICNumber.text = it.child("icNumber").value.toString()
            tvGender.text = it.child("gender").value.toString()
            tvAddress.text = it.child("address").value.toString()
            tvBirthday.text = it.child("birthday").value.toString()
            tvStatus.text = it.child("status").value.toString()

            if(it.child("name").value.toString()==""||
                it.child("icNumber").value.toString()==""||
                it.child("gender").value.toString()==""||
                it.child("address").value.toString()==""||
                it.child("birthday").value.toString()==""){
                Toast.makeText(applicationContext, "Please complete your personal information", Toast.LENGTH_LONG).show()
            }
        }



        layout_UserName.setOnClickListener() {
            val edittext = EditText(this)
            myRef.child(phone).get().addOnSuccessListener() {
                edittext.setText(it.child("name").value.toString())
            }
            AlertDialog.Builder(this).setTitle("Edit Name").setMessage("Please enter your name here").setView(edittext).
            setPositiveButton("OK"){ _, _->
                myRef.child(phone).child("name").setValue(edittext.text.toString()).addOnSuccessListener(){
                    Toast.makeText(applicationContext, "Name Changed Successfully", Toast.LENGTH_LONG).show()}

                myRef.child(phone).get().addOnSuccessListener(){
                    tvUserName.text = it.child("name").value.toString()}
            }.show()
        }
        layout_ICNumber.setOnClickListener() {
            val edittext = EditText(this)
            myRef.child(phone).get().addOnSuccessListener(){
                edittext.setText(it.child("icNumber").value.toString())
            }

            AlertDialog.Builder(this).setTitle("Edit Name").setMessage("Please enter your ic number here").setView(edittext).setPositiveButton("OK") {_, _->
                myRef.child(phone).child("icNumber").setValue(edittext.text.toString()).addOnSuccessListener(){
                    Toast.makeText(applicationContext, "IC Number Changed Successfully", Toast.LENGTH_LONG).show()}

                myRef.child(phone).get().addOnSuccessListener(){
                    tvICNumber.text = it.child("icNumber").value.toString()}
            }.show()
        }
        layout_Gender.setOnClickListener() {
            val genderOptions = arrayOf("Male","Female")
            var setGender : String = "Male"

            AlertDialog.Builder(this).setTitle("Select Gender").setSingleChoiceItems(genderOptions,0){_, position->
                if(position==0)
                {  setGender = "Male"
                }else{
                    setGender = "Female"
                }
            }.
            setPositiveButton("OK"){ _, _->
                myRef.child(phone).child("gender").setValue(setGender).addOnSuccessListener(){
                    Toast.makeText(applicationContext, "Gender Changed Successfully", Toast.LENGTH_LONG).show()}

                myRef.child(phone).get().addOnSuccessListener(){
                    tvGender.text = it.child("gender").value.toString()}
            }.show()
        }
        layout_Address.setOnClickListener() {
            val edittext = EditText(this)
            myRef.child(phone).get().addOnSuccessListener(){
                edittext.setText(it.child("address").value.toString())
            }

            AlertDialog.Builder(this).setTitle("Edit Name").setMessage("Please enter your address here").setView(edittext).setPositiveButton("OK") {_, _->
                myRef.child(phone).child("address").setValue(edittext.text.toString()).addOnSuccessListener(){
                    Toast.makeText(applicationContext, "Address Changed Successfully", Toast.LENGTH_LONG).show()}

                myRef.child(phone).get().addOnSuccessListener(){
                    tvAddress.text = it.child("address").value.toString()}
            }.show()
        }
        tvBirthday.setOnClickListener() {
            val edittext = EditText(this)
            myRef.child(phone).get().addOnSuccessListener(){
                edittext.setText(it.child("birthday").value.toString())
            }

            AlertDialog.Builder(this).setTitle("Edit Name").setMessage("Please enter your birthday here\nFormat:DD-MM-YYYY").setView(edittext).setPositiveButton("OK") {_, _->
                myRef.child(phone).child("birthday").setValue(edittext.text.toString()).addOnSuccessListener(){
                    Toast.makeText(applicationContext, "Birthday Changed Successfully", Toast.LENGTH_LONG).show()

                    myRef.child(phone).get().addOnSuccessListener(){
                        tvBirthday.text = it.child("birthday").value.toString()}
                }
            }.show()
        }
    }
}