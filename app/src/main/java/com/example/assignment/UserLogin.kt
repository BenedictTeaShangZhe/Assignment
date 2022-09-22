package com.example.assignment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UserLogin : AppCompatActivity() {
    // Below are used to do the login function
    lateinit var sharedPreferences: SharedPreferences
    //Below are the Key of login sharedPreferences
    private var PREFS_KEY = "prefs"
    private var PHONE_KEY = "phone"
    //Below are to validate the status of login
    private var phone = ""

    //realtime database initialization (Copy)
    private lateinit var myDB: FirebaseDatabase
    private lateinit var myRef: DatabaseReference

    private lateinit var phoneNumberContainer:TextInputLayout
    private lateinit var passwordContainer:TextInputLayout
    private lateinit var txtPhone:EditText
    private lateinit var txtPassword:EditText
    private lateinit var loginButton:Button
    private lateinit var clearButton:Button
    private lateinit var registerText:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_login)
        //XML declaration
        phoneNumberContainer = findViewById(R.id.phoneNumberContainer)
        passwordContainer = findViewById(R.id.passwordContainer)
        txtPhone = findViewById(R.id.txtPhoneNumber)
        txtPassword = findViewById(R.id.txtPassword)
        loginButton = findViewById(R.id.btnRegister)
        clearButton = findViewById(R.id.btnClear)
        registerText = findViewById(R.id.tvRegister)


        //Database declaration(Copy)
        myDB = FirebaseDatabase.getInstance()
        myRef = myDB.getReference("User")

        //input validation
        phoneFocusListener()
        passwordFocusListener()



        loginButton.setOnClickListener(){
            phoneNumberContainer.helperText =validPhone()
            passwordContainer.helperText =validPassword()
            val validPhoneNumber = phoneNumberContainer.helperText == null
            val validPassword = passwordContainer.helperText == null
            txtPhone.clearFocus()
            txtPassword.clearFocus()


            if(validPhoneNumber && validPassword ){
                login()
            }else{
                invalidForm()
            }

        }

        clearButton.setOnClickListener{
            txtPhone.text.clear()
            txtPassword.text.clear()
            txtPhone.clearFocus()
            txtPassword.clearFocus()
        }

        registerText.setOnClickListener{
            val intent = Intent(this, UserRegister::class.java)
            startActivity(intent)
            finish()
        }

    }




    private fun login() {
        val phoneNumber:String = txtPhone.text.toString()
        val password:String = txtPassword.text.toString()


        myRef.child(phoneNumber).get().addOnSuccessListener(){
            //If phone number is existed
            if(it.exists()){
                if(it.child("password").value.toString() == password){
                    sharedPreferences = getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)
                    val editor: SharedPreferences.Editor = sharedPreferences.edit()
                    editor.putString(PHONE_KEY, txtPhone.text.toString())
                    editor.apply()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()

                }else{
                    AlertDialog.Builder(this).setTitle("Login Failed").setMessage("The password is not correct").setPositiveButton("Okay"){
                            _,_->
                    }.show()
                }

            }
            else{
                AlertDialog.Builder(this).setTitle("Login Failed").setMessage("The phone number is not existed").setPositiveButton("Okay"){
                    _,_->
                }.show()
            }
    }
    }

    private fun invalidForm() {
        var message = ""
        if(phoneNumberContainer.helperText != null){
            message+="Phone Number: "+phoneNumberContainer.helperText+"\n\n"
        }
        if(passwordContainer.helperText != null){
            message+="Password: "+passwordContainer.helperText+"\n\n"
        }

        AlertDialog.Builder(this).setTitle("Invalid Form").setMessage(message).setPositiveButton("Okay"){ _, _->
            //Action
        }.show()
    }


    private fun phoneFocusListener(){
        txtPhone.setOnFocusChangeListener{_,focused->
            if(!focused){

                phoneNumberContainer.helperText=validPhone()
            }

        }
    }

    //return warning string
    private fun validPhone(): String? {
        val phoneText = txtPhone.text.toString()
        if(!phoneText.matches(".*[0-9].*".toRegex())){
            return "Must Only Contains Numbers"
        }
        if(phoneText.length!=10){
            return "Must be 10 digits"
        }
        return null
    }


    private fun passwordFocusListener(){
        txtPassword.setOnFocusChangeListener{_,focused->
            if(!focused){
                passwordContainer.helperText=validPassword()
            }

        }
    }
    //return warning string
    private fun validPassword(): String? {
        val passwordText = txtPassword.text.toString()
        if(passwordText.length<8){
            return "Minimum 8 Character Password"
        }else if(!passwordText.matches(".*[A-Z].*".toRegex())){
            return "Must Contain At least 1 Upper-case Character"
        }else if(!passwordText.matches(".*[a-z].*".toRegex())){
            return "Must Contain At least 1 Lower-case Character"
        }else if(!passwordText.matches(".*[@#\$%^&=+!].*".toRegex())){
            return "Must Contain At least 1 Special Character (@#\$%^&=+!)"
        }
        return null
    }
}