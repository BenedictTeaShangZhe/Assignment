package com.example.assignment.SearchingFoodActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.R
import com.google.firebase.database.*

class listStation : AppCompatActivity(), stationAdapter.OnItemClickListener {

    private lateinit var myDB: FirebaseDatabase
    private lateinit var stationRef: DatabaseReference
    private lateinit var stationArrayList: ArrayList<dataStation>
    private lateinit var rvStationList : RecyclerView
    lateinit var toolbar: androidx.appcompat.widget.Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_station)

        toolbar = findViewById(R.id.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_back)
        toolbar.setNavigationOnClickListener(){
            finish()
        }

        myDB = FirebaseDatabase.getInstance()
        stationRef = myDB.getReference("Station")
        stationArrayList = arrayListOf<dataStation>()

        val stationAdapter = stationAdapter(stationArrayList, this)

        rvStationList = findViewById(R.id.rvStationList)
        rvStationList.adapter = stationAdapter
        rvStationList.layoutManager = LinearLayoutManager(this)
        rvStationList.setHasFixedSize(true)

        getStationData()
    }

    private fun getStationData(){
        stationRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot){
                if(snapshot.exists()){
                    for (foodSnapshot in snapshot.children){
                        val list = foodSnapshot.getValue(dataStation::class.java)
                        stationArrayList.add(list!!)
                    }
                    val adapter = stationAdapter(stationArrayList,this@listStation)
                    rvStationList.adapter= adapter
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, "Error Occured", Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun itemClick(position: Int) {
        val selectStation = stationArrayList[position]
        val bundle1 = Bundle()
        val bundle2 = Bundle()
        bundle1.putString("stationName", selectStation.StationName)
        bundle2.putString("stationAddress",selectStation.StationAddress)
        val intent = Intent(this, listFood::class.java)
        intent.putExtras(bundle1)
        intent.putExtras(bundle2)
        startActivity(intent)
    }
}