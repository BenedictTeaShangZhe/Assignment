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
import com.example.assignment.AdminProfileActivity.CheckApprovedUsers
import com.example.assignment.AdminProfileActivity.ReviewOfApprovalApplication
import com.example.assignment.MainActivity
import com.example.assignment.R
import com.example.assignment.UserProfileActivity.UserAboutUs
import com.example.assignment.UserProfileActivity.UserApprovalRequest
import com.example.assignment.UserProfileActivity.UserPersonalInfo

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

private lateinit var sharedPreferences: SharedPreferences
//Below are the Key of login sharedPreferences
private var PREFS_KEY = "prefs"
private var PHONE_KEY = "phone"
//Below are to validate the status of login
private var phone = ""

/**
 * A simple [Fragment] subclass.
 * Use the [AdminProfile.newInstance] factory method to
 * create an instance of this fragment.
 */
class AdminProfile : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = this.requireActivity().getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)
        //List view of user profile fragment
        val arrayAdapter: ArrayAdapter<String>
        val userProfileOptions = arrayOf(
            "Review of Approval Applications", "Check Approved Users","Logout"
        )
        var userProfileListView = view.findViewById<ListView>(R.id.userProfileListView)
        arrayAdapter = ArrayAdapter(this.requireActivity(),android.R.layout.simple_list_item_1, userProfileOptions)
        userProfileListView.adapter = arrayAdapter

        userProfileListView.onItemClickListener = AdapterView.OnItemClickListener {
                parent, view, position, id ->
            when(position){
                0->{val intent = Intent(this.requireActivity(), ReviewOfApprovalApplication::class.java)
                    startActivity(intent)}
                1->{val intent = Intent(this.requireActivity(), CheckApprovedUsers::class.java)
                    startActivity(intent)}
                2->{val editor: SharedPreferences.Editor = sharedPreferences.edit()
                    editor.clear()
                    editor.apply()
                    Toast.makeText(this.requireActivity(), "You have logged out", Toast.LENGTH_LONG).show()
                    val intent = Intent(this.requireActivity(), MainActivity::class.java)
                    startActivity(intent)
                    this.requireActivity().finish()
                    }
            }

        }
    }
}