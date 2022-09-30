package com.example.assignment.SearchingFoodActivity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.R
import com.google.firebase.database.*

class listOrdered : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences

    private var PREFS_KEY = "prefs"
    private var PHONE_KEY = "phone"
    private var phone = ""

    private lateinit var myDB: FirebaseDatabase
    private lateinit var orderedRef: DatabaseReference
    private lateinit var userOrderedArrayList: ArrayList<dataOrdered>
    private lateinit var rvUserOrdered : RecyclerView
    lateinit var toolbar: androidx.appcompat.widget.Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_user_ordered)

        toolbar = findViewById(R.id.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_back)
        toolbar.setNavigationOnClickListener(){
            finish()
        }

        sharedPreferences = getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)
        phone = sharedPreferences.getString(PHONE_KEY, "").toString()

        myDB = FirebaseDatabase.getInstance()
        orderedRef = myDB.getReference("userOrdered")
        userOrderedArrayList = arrayListOf<dataOrdered>()

        val orderedAdapter = orderedAdapter(userOrderedArrayList)

        rvUserOrdered = findViewById(R.id.rvUserOrdered)
        rvUserOrdered.adapter = orderedAdapter
        rvUserOrdered.layoutManager = LinearLayoutManager(this)
        rvUserOrdered.setHasFixedSize(true)
        getUserOrdered(phone)
    }

    private fun getUserOrdered(number: String ?){
        orderedRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot){
                if(snapshot.exists()){
                    for (orderedSnapshot in snapshot.children){
                        if (orderedSnapshot.child("phoneNo").value == number) {
                            val list = orderedSnapshot.getValue(dataOrdered::class.java)
                            userOrderedArrayList.add(list!!)
                        }
                    }
                    val adapter = orderedAdapter(userOrderedArrayList)
                    rvUserOrdered.adapter= adapter
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, "Error Occured", Toast.LENGTH_LONG).show()
            }
        })
    }
}