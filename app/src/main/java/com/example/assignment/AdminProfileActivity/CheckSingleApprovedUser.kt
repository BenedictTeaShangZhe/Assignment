package com.example.assignment.AdminProfileActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.assignment.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CheckSingleApprovedUser : AppCompatActivity() {
    private lateinit var phone : String

    //realtime database initialization (Copy)
    private lateinit var myDB: FirebaseDatabase
    private lateinit var myRef: DatabaseReference

    //XML initialization
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var tvPhoneApproved: TextView
    private lateinit var tvUserNameApproved: TextView
    private lateinit var tvICNumberApproved: TextView
    private lateinit var tvGenderApproved: TextView
    private lateinit var tvAddressApproved: TextView
    private lateinit var tvBirthdayApproved: TextView
    private lateinit var btnReject_Approved : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_single_approved_user)
        //toolbar declaration
        toolbar = findViewById(R.id.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_back)
        toolbar.setNavigationOnClickListener(){
            val intent = Intent(this, CheckApprovedUsers::class.java)
            startActivity(intent)
            finish()
        }

        //XML declaration
        tvPhoneApproved = findViewById(R.id.tvPhoneApproved)
        tvUserNameApproved = findViewById(R.id.tvUserNameApproved)
        tvICNumberApproved = findViewById(R.id.tvICNumberApproved)
        tvGenderApproved = findViewById(R.id.tvGenderApproved)
        tvAddressApproved = findViewById(R.id.tvAddressApproved)
        tvBirthdayApproved = findViewById(R.id.tvBirthdayApproved)
        btnReject_Approved = findViewById(R.id.btnReject_Approved)

        //Database declaration(Copy)
        myDB = FirebaseDatabase.getInstance()
        //reference = database table
        myRef = myDB.getReference("User")

        //get the phone from the previous activity
        val bundle = intent.extras
        if (bundle != null){
            phone = bundle.getString("phone").toString()
        }

        //shows the user information in text view
        myRef.child(phone).get().addOnSuccessListener(){
            tvPhoneApproved.text = it.child("phoneNumber").value.toString()
            tvUserNameApproved.text = it.child("name").value.toString()
            tvICNumberApproved.text = it.child("icNumber").value.toString()
            tvGenderApproved.text = it.child("gender").value.toString()
            tvAddressApproved.text = it.child("address").value.toString()
            tvBirthdayApproved.text = it.child("birthday").value.toString()
        }.addOnFailureListener(){
        }

        btnReject_Approved.setOnClickListener(){
            val rejectOptions = arrayOf("IC Photo Not Clear","Personal Info Not Complete","Personal Info Not Match with IC")
            var rejectReason : String = "IC Photo Not Clear"
            var rejectSuccess : Boolean = true

            AlertDialog.Builder(this).setTitle("Select Gender").setSingleChoiceItems(rejectOptions,0){ _, position->
                when(position){
                    0-> rejectReason = "IC Photo Not Clear"
                    1-> rejectReason = "Personal Info Not Complete"
                    2-> rejectReason = "Personal Info Not Match with IC"
                }
            }.
            setPositiveButton("OK"){ _, _->
                myRef.child(phone).child("rejectedReason").setValue(rejectReason).addOnFailureListener(){
                    rejectSuccess=false}
                myRef.child(phone).child("status").setValue("Not Approved").addOnFailureListener(){
                    rejectSuccess=false}
                if(rejectSuccess){
                    Toast.makeText(applicationContext, "User"+tvUserNameApproved.text.toString()+"have been rejected", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, CheckApprovedUsers::class.java)
                    startActivity(intent)
                    finish()}
            }.setNeutralButton("Cancel"){_,_->}.show()
        }

    }
}