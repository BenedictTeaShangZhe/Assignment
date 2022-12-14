package com.example.assignment.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.example.assignment.MainActivity
import com.example.assignment.R
import com.example.assignment.UserProfileActivity.UserAboutUs
import com.example.assignment.UserProfileActivity.UserApprovalRequest
import com.example.assignment.UserProfileActivity.UserPersonalInfo

private lateinit var sharedPreferences: SharedPreferences
//Below are the Key of login sharedPreferences
private var PREFS_KEY = "prefs"
private var PHONE_KEY = "phone"
//Below are to validate the status of login
private var phone = ""

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
        //List view of user profile fragment
        val arrayAdapter: ArrayAdapter<String>
        val userProfileOptions = arrayOf(
            "Apply for Identity Approval", "Set Personal Information", "About Us","Logout"
        )
        var userProfileListView = view.findViewById<ListView>(R.id.userProfileListView)
        arrayAdapter = ArrayAdapter(this.requireActivity(),android.R.layout.simple_list_item_1, userProfileOptions)
        userProfileListView.adapter = arrayAdapter

        userProfileListView.onItemClickListener = AdapterView.OnItemClickListener {
                parent, view, position, id ->
                when(position){
                    0->{val intent = Intent(this.requireActivity(), UserApprovalRequest::class.java)
                        startActivity(intent)}
                    1->{val intent = Intent(this.requireActivity(), UserPersonalInfo::class.java)
                        startActivity(intent)}
                    2->{val intent = Intent(this.requireActivity(), UserAboutUs::class.java)
                        startActivity(intent)}
                    3->{val editor: SharedPreferences.Editor = sharedPreferences.edit()
                        editor.clear()
                        editor.apply()
                        Toast.makeText(this.requireActivity(), "You have logged out", Toast.LENGTH_LONG).show()
                        val intent = Intent(this.requireActivity(), MainActivity::class.java)
                        startActivity(intent)
                        this.requireActivity().finish() }
                }

        }
    }
}