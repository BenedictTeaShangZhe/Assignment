package com.example.assignment.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.assignment.MainActivity
import com.example.assignment.R
import com.example.assignment.UserLogin

// Below are used to do the login function
lateinit var sharedPreferences: SharedPreferences
//Below are the Key of login sharedPreferences
private var PREFS_KEY = "prefs"
private var PHONE_KEY = "phone"
//Below are to validate the status of login
private var phone = ""

private lateinit var btnLogout:Button

class UserProfileFragment : Fragment(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_profile, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = this.requireActivity().getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)
        btnLogout = view.findViewById(R.id.btnLogout)
        btnLogout.setOnClickListener(){
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.clear()
            editor.apply()
            val intent = Intent(this.requireActivity(), MainActivity::class.java)
            startActivity(intent)
            this.requireActivity().finish()
        }
    }
}