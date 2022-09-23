package com.example.assignment.UserProfileActivity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.CallSuper
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.FileProvider
import com.example.assignment.BuildConfig
import com.example.assignment.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File
import java.io.IOException
import java.util.*


class UserApprovalRequest : AppCompatActivity() {
    //realtime database initialization (Copy)
    private lateinit var myDB: FirebaseDatabase
    private lateinit var myRef: DatabaseReference
    private lateinit var auth : FirebaseAuth

    //Storage database initialization (Copy)
    private lateinit var storageRef: StorageReference
    private  lateinit var IC_Front_imgUri: Uri
    private  lateinit var IC_Back_imgUri: Uri
    private lateinit var tempImagePath : String

    lateinit var sharedPreferences: SharedPreferences
    lateinit var toolbar: androidx.appcompat.widget.Toolbar
    //Below are the Key of login sharedPreferences
    private var PREFS_KEY = "prefs"
    private var PHONE_KEY = "phone"
    //Below are to validate the status of login
    private var phone = ""



    //XML initialization
    private lateinit var imageView_IC_Front:ImageView
    private lateinit var imageView_IC_Back:ImageView
    private lateinit var tv_Upload_Instruction : TextView
    private lateinit var btn_IC_Front : Button
    private lateinit var btn_IC_Back : Button
    private lateinit var btn_Submit_Approval : Button

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_approval_request)
        //Storage declaration
        storageRef = FirebaseStorage.getInstance().reference

        //Database declaration(Copy)
        myDB = FirebaseDatabase.getInstance()
        //reference = database table
        myRef = myDB.getReference("User")

        sharedPreferences = getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)
        phone = sharedPreferences.getString(PHONE_KEY, "").toString()

        //toolbar declaration
        toolbar = findViewById(R.id.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_back)
        toolbar.setNavigationOnClickListener(){
            finish()
        }

        IC_Front_imgUri = Uri.parse("")
        IC_Back_imgUri = Uri.parse("")

        // XML declaration
        imageView_IC_Front = findViewById(R.id.imageView_IC_Front)
        imageView_IC_Back = findViewById(R.id.imageView_IC_Back)
        tv_Upload_Instruction = findViewById(R.id.tv_Upload_Instruction)
        btn_IC_Front = findViewById(R.id.btn_IC_Front)
        btn_IC_Back = findViewById(R.id.btn_IC_Back)
        btn_Submit_Approval = findViewById(R.id.btn_Submit_Approval)

        //Detect have user applied or not
        myRef.child(phone).get().addOnSuccessListener(){
            val rejectReason = it.child("rejectedReason").value.toString()
            if(it.child("status").value.toString()=="Requesting"){
                AlertDialog.Builder(this).setTitle("Applied").setMessage("You have already applied before").
                setPositiveButton("Okay"){_,_->
                    finish()
                }.show()
            }
            if(it.child("status").value.toString()=="Rejected"){
                AlertDialog.Builder(this).setTitle("Rejected!").setMessage("You have been rejected due to:\n$rejectReason").
                setPositiveButton("Okay"){_,_->
                }.show()
            }
            if(it.child("status").value.toString()=="Approved"){
                AlertDialog.Builder(this).setTitle("Approved!").setMessage("You have been Approved").
                setPositiveButton("Okay"){_,_->
                    finish()
                }.show()
            }
        }

        btn_IC_Front.setOnClickListener(){
            AlertDialog.Builder(this).setTitle("Front IC").setMessage("Please select to take photo or choose from gallery").
            setPositiveButton("Camera"){_,_->
                IC_Front_imgUri=FileProvider.getUriForFile(this,"com.example.assignment.provider",createImageFile().also {
                    tempImagePath = it.absolutePath
                })
                getICFrontPhoto.launch(IC_Front_imgUri)
            }.setNeutralButton("Gallery"){_,_->
                getICFrontImage.launch("image/*")
            }.show()
        }

        btn_IC_Back.setOnClickListener(){
            AlertDialog.Builder(this).setTitle("Back IC").setMessage("Please select to take photo or choose from gallery").
            setPositiveButton("Camera"){_,_->
                IC_Back_imgUri=FileProvider.getUriForFile(this,"com.example.assignment.provider",createImageFile().also {
                    tempImagePath = it.absolutePath
                })
                getICBackPhoto.launch(IC_Back_imgUri)
            }.setNeutralButton("Gallery"){_,_->
                getICBackImage.launch("image/*")
            }.show()
        }


        btn_Submit_Approval.setOnClickListener() {
            if(IC_Front_imgUri == Uri.parse("")|| IC_Back_imgUri ==Uri.parse("")){
                AlertDialog.Builder(this).setTitle("Error").setMessage("You havent select all IC picture").
                setPositiveButton("Okay"){_,_->}.show()
            }else{
                var uploadICFrontSuccess : Boolean = true
                var uploadICBackSuccess : Boolean = true

                val uploadICFrontPhoto = storageRef.child("ICPhoto/"+phone+"_Front.png")
                uploadICFrontPhoto.putFile(IC_Front_imgUri). addOnFailureListener{
                    uploadICFrontSuccess = false }

                val uploadICBackPhoto = storageRef.child("ICPhoto/"+phone+"_Back.png")
                uploadICBackPhoto.putFile(IC_Back_imgUri). addOnFailureListener{
                    uploadICBackSuccess = false }


                if(uploadICFrontSuccess && uploadICBackSuccess){
                    myRef.child(phone).child("rejectedReason").setValue("")
                    myRef.child(phone).child("status").setValue("Requesting").addOnSuccessListener(){
                        Toast.makeText(applicationContext, "You have successfully apply for approval", Toast.LENGTH_LONG).show()
                        finish()
                    }
                }else{
                    Toast.makeText(applicationContext, "Submission Failed!!", Toast.LENGTH_LONG).show()
                }
            }
        }

    }


    private val getICFrontImage = registerForActivityResult(ActivityResultContracts.GetContent()){ Uri->
        if(Uri!=null){
            IC_Front_imgUri = Uri!!//Means must not null
            imageView_IC_Front.setImageURI(Uri)
        }else{
            Toast.makeText(applicationContext, "You did not select any photo", Toast.LENGTH_LONG).show()
        }
    }

    private val getICFrontPhoto = registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
        if (isSuccess) {
            imageView_IC_Front.setImageURI(IC_Front_imgUri)
        } else {
            Toast.makeText(applicationContext, "You did not take any photo", Toast.LENGTH_LONG).show()
        }
    }

    private val getICBackImage = registerForActivityResult(ActivityResultContracts.GetContent()){ Uri->
        if(Uri!=null){
            IC_Back_imgUri = Uri!!//Means must not null
            imageView_IC_Back.setImageURI(Uri)
        }else{
            Toast.makeText(applicationContext, "You did not select any photo", Toast.LENGTH_LONG).show()
        }
    }

    private val getICBackPhoto = registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
        if (isSuccess) {
            imageView_IC_Back.setImageURI(IC_Back_imgUri)
        } else {
            Toast.makeText(applicationContext, "You did not take any photo", Toast.LENGTH_LONG).show()
        }
    }

    private fun createImageFile(): File {
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("temp_image",".jpg",storageDir)
    }




    }

