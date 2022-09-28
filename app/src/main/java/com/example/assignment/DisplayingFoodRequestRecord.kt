package com.example.assignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.StorageReference

class DisplayingFoodRequestRecord : AppCompatActivity(), donerRequestAdapter.OnItemClickListener {

    private lateinit var  database: FirebaseDatabase
    //something like db.
    private lateinit var  auth: FirebaseAuth

    private lateinit var storageRef: StorageReference

    private val donerRequestList = listOf(
        donerRequest("Tiger","123", "Ampang", "Ampang", "Ice","Must be Cold"),
        donerRequest("Jordan","123", "Rawang", "Rawang", "Fire","Must be Hot")


    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_displaying_food_request_record)

        val RequestingRecyclerView = findViewById<RecyclerView>(R.id.requestRecyclerView)
        val btnBack = findViewById<Button>(R.id.btnBackToRecord)

        val myAdapter = donerRequestAdapter(donerRequestList,this)

        RequestingRecyclerView.adapter = myAdapter

        RequestingRecyclerView.layoutManager = LinearLayoutManager(this)

        RequestingRecyclerView.setHasFixedSize(true)

        btnBack.setOnClickListener()
        {
            val intent = Intent(this, DisplayFoodRecord::class.java)
            startActivity(intent)

        }
    }

    override fun itemClick(position: Int) {
        //....
        val selectStudent = donerRequestList[position]
        Toast.makeText(this, selectStudent.name, Toast.LENGTH_SHORT).show()
    }

}