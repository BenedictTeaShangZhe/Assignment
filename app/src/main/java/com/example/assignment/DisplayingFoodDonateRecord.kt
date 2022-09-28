package com.example.assignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class DisplayingFoodDonateRecord : AppCompatActivity(), donateFoodAdapter.OnItemClickListener {

    private lateinit var  database: FirebaseDatabase
    //something like db.
    private lateinit var  auth: FirebaseAuth

    private lateinit var storageRef: StorageReference

    private lateinit var dbref:DatabaseReference


    private val foodDonerList = listOf(
        doner("Tiger", "123", "Setapak", "Ampang", "ice", "water", "2"),
        doner("Jordan", "123", "Rawang", "Setapak", "fire", "ice", "5")

    )

    // this is for authentication
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_displaying_food_donate_record)

        val btnBack = findViewById<Button>(R.id.btnBack)

        val myRecyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        val myAdapter = donateFoodAdapter(foodDonerList,this)

        myRecyclerView.adapter = myAdapter

        myRecyclerView.layoutManager = LinearLayoutManager(this)

        myRecyclerView.setHasFixedSize(true)

        //database


        btnBack.setOnClickListener()
        {
            val intent = Intent(this, DisplayFoodRecord::class.java)
            startActivity(intent)

        }


    }

    override fun itemClick(position: Int) {
        //....
        val selectStudent = foodDonerList[position]
        Toast.makeText(this, selectStudent.name, Toast.LENGTH_SHORT).show()
    }


}