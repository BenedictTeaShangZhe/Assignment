package com.example.assignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UserRegister : AppCompatActivity() {
    //realtime database initialization (Copy)
    private lateinit var myDB: FirebaseDatabase
    private lateinit var myRef: DatabaseReference

    private lateinit var phoneNumberContainer: TextInputLayout
    private lateinit var passwordContainer: TextInputLayout
    private lateinit var confirmPasswordContainer: TextInputLayout
    private lateinit var txtPhoneNumber: EditText
    private lateinit var txtPassword: EditText
    private lateinit var txtConfirmPassword: EditText
    private lateinit var registerButton: Button
    private lateinit var clearButton: Button
    private lateinit var LoginText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_register)

        //Database declaration(Copy)
        myDB = FirebaseDatabase.getInstance()
        myRef = myDB.getReference("User")

        phoneNumberContainer = findViewById(R.id.phoneNumberContainer)
        passwordContainer = findViewById(R.id.passwordContainer)
        confirmPasswordContainer = findViewById(R.id.confirmpasswordContainer)
        txtPhoneNumber = findViewById(R.id.txtPhoneNumber)
        txtPassword = findViewById(R.id.txtPassword)
        txtConfirmPassword = findViewById(R.id.txtconfirmPassword)
        registerButton = findViewById(R.id.btnRegister)
        clearButton = findViewById(R.id.btnClear)
        LoginText = findViewById(R.id.tvRegister)

        //input validation
        phoneFocusListener()
        passwordFocusListener()
        confirmPasswordFocusListener()

        registerButton.setOnClickListener(){
            phoneNumberContainer.helperText =validPhone()
            passwordContainer.helperText =validPassword()
            confirmPasswordContainer.helperText =validConfirmPassword()
            val validPhoneNumber = phoneNumberContainer.helperText == null
            val validPassword = passwordContainer.helperText == null
            val validConfirmPassword = confirmPasswordContainer.helperText == null
            txtPhoneNumber.clearFocus()
            txtPassword.clearFocus()
            txtConfirmPassword.clearFocus()

            if(validPhoneNumber && validPassword && validConfirmPassword){
                register()
            }else{
                invalidForm()
            }
        }

        clearButton.setOnClickListener{
            txtPhoneNumber.text.clear()
            txtPassword.text.clear()
            txtConfirmPassword.text.clear()
            txtPhoneNumber.clearFocus()
            txtPassword.clearFocus()
            txtConfirmPassword.clearFocus()
            phoneNumberContainer.helperText="Required"
            passwordContainer.helperText="Required"
            confirmPasswordContainer.helperText="Required"
        }

        LoginText.setOnClickListener{
            val intent = Intent(this, UserLogin::class.java)
            startActivity(intent)
            finish()
        }
    }

    //if all input valid
    private fun register(){
        val phoneNumber:String = txtPhoneNumber.text.toString()


        var message = "Phone Number: "+phoneNumber+"\nPlease Make Sure Your Phone Number & Password are Correct!"

        AlertDialog.Builder(this).setTitle("Register Confirmation").setMessage(message).setPositiveButton("Register"){_,dialog->
            //Put the user into database
            //Check the phone number is existed in database or not
            myRef.child(phoneNumber).get().addOnSuccessListener(){
                if(it.exists()){
                    //If phone number is existed
                    Toast.makeText(applicationContext, "Phone number already used", Toast.LENGTH_LONG).show()
                    txtPhoneNumber.text.clear()
                    phoneNumberContainer.helperText="Phone number already used"
                }
                else{
                    val savePhone = txtPhoneNumber.text.toString()
                    val savePassword = txtPassword.text.toString()
                    val user = User(savePhone,savePassword)
                    myRef.child(savePhone).setValue(user).addOnSuccessListener {
                        Toast.makeText(applicationContext, "Register Successful.Please Login Now", Toast.LENGTH_LONG).show()
                        val intent = Intent(this, UserLogin::class.java)
                        startActivity(intent)
                        finish()
                    }.addOnFailureListener {
                        Toast.makeText(applicationContext, "Register Failed", Toast.LENGTH_LONG).show()
                    }
                }

            }.addOnFailureListener(){

            }

        }.setNeutralButton("Cancel"){_,dialog->

        }.show()
    }


    //if input are not valid
    private fun invalidForm(){
        var message = ""
        if(phoneNumberContainer.helperText != null){
            message+="Phone Number: "+phoneNumberContainer.helperText+"\n\n"
        }
        if(passwordContainer.helperText != null){
            message+="Password: "+passwordContainer.helperText+"\n\n"
        }
        if(confirmPasswordContainer.helperText != null){
            message+="ConfirmPassword: "+confirmPasswordContainer.helperText+"\n\n"
        }

        AlertDialog.Builder(this).setTitle("Invalid Form").setMessage(message).setPositiveButton("Okay"){_,_->
            //Action
        }.show()
    }




//Below are the input validation
    private fun phoneFocusListener(){
        txtPhoneNumber.setOnFocusChangeListener{_,focused->
            if(!focused){

                phoneNumberContainer.helperText=validPhone()
            }

        }
    }

    //return warning string
    private fun validPhone(): String? {
        val phoneText = txtPhoneNumber.text.toString()
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
        }else if(!passwordText.matches(".*[@%^&=+!].*".toRegex())){
            return "Must Contain At least 1 Special Character (@#\$%^&=+!)"
        }
        return null
    }

    private fun confirmPasswordFocusListener(){
        txtConfirmPassword.setOnFocusChangeListener{_,focused->
            if(!focused){

                confirmPasswordContainer.helperText=validConfirmPassword()
            }

        }
    }

    //return warning string
    private fun validConfirmPassword(): String? {
        val passwordText = txtPassword.text.toString()
        val confirmPasswordText = txtConfirmPassword.text.toString()

        if(confirmPasswordText!=passwordText){
            return "Confirm Password is not same with Password"
        }
        return null
    }
}