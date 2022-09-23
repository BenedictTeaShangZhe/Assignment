package com.example.assignment.AdminProfileActivity

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.assignment.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File


class ReviewSingelApprovalApplication : AppCompatActivity() {
    private lateinit var phone : String

    //realtime database initialization (Copy)
    private lateinit var myDB: FirebaseDatabase
    private lateinit var myRef: DatabaseReference

    //Storage database initialization (Copy)
    private lateinit var storageRef: StorageReference
    private  lateinit var IC_Front_imgUri: Uri
    private  lateinit var IC_Back_imgUri: Uri
    private lateinit var tempImagePath : String


    //XML initialization
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var imageView_IC_Front_Approve: ImageView
    private lateinit var imageView_IC_Back_Approve: ImageView
    private lateinit var tvUserName: TextView
    private lateinit var tvICNumber: TextView
    private lateinit var tvGender: TextView
    private lateinit var tvAddress: TextView
    private lateinit var tvBirthday: TextView
    private lateinit var btnApprove : Button
    private lateinit var btnReject : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review_singel_approval_application)
        //toolbar declaration
        toolbar = findViewById(R.id.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_back)
        toolbar.setNavigationOnClickListener(){
            finish()
        }

        //XML declaration
        imageView_IC_Front_Approve = findViewById(R.id.imageView_IC_Front_ApprovalReview)
        imageView_IC_Back_Approve = findViewById(R.id.imageView_IC_Back_ApprovalReview)
        tvUserName = findViewById(R.id.tvUserName)
        tvICNumber = findViewById(R.id.tvICNumber)
        tvGender = findViewById(R.id.tvGender)
        tvAddress = findViewById(R.id.tvAddress)
        tvBirthday = findViewById(R.id.tvBirthday)
        btnApprove = findViewById(R.id.btnApprove)
        btnReject = findViewById(R.id.btnReject)

        //Database declaration(Copy)
        myDB = FirebaseDatabase.getInstance()
        //reference = database table
        myRef = myDB.getReference("User")

        //Storage declaration
        storageRef = FirebaseStorage.getInstance().reference

        //get the phone from the previous activity
        val bundle = intent.extras
        if (bundle != null){
            phone = bundle.getString("phone").toString()
        }

        //shows the user information in text view
        myRef.child(phone).get().addOnSuccessListener(){
            tvUserName.text = it.child("name").value.toString()
            tvICNumber.text = it.child("icNumber").value.toString()
            tvGender.text = it.child("gender").value.toString()
            tvAddress.text = it.child("address").value.toString()
            tvBirthday.text = it.child("birthday").value.toString()
        }.addOnFailureListener(){
            tvUserName.text = "Fail"
            tvICNumber.text = "Fail"
            tvGender.text = "Fail"
            tvAddress.text = "Fail"
            tvBirthday.text = "Fail"
        }

        //get the IC picture
        val icFront = storageRef.child("ICPhoto/"+phone+"_Front.png")
        val icBack = storageRef.child("ICPhoto/"+phone+"_Back.png")
        val filefront = File.createTempFile("temp","png")
        val fileback = File.createTempFile("temp","png")
        icFront.getFile(filefront).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(filefront.absolutePath)
            imageView_IC_Front_Approve.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 500, 500, false))}.
        addOnFailureListener{Toast.makeText(applicationContext,it.message,Toast.LENGTH_LONG).show() }

        icBack.getFile(fileback).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(fileback.absolutePath)
            imageView_IC_Back_Approve.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 500, 500, false))}.
        addOnFailureListener{Toast.makeText(applicationContext,it.message,Toast.LENGTH_LONG).show() }

    }


}