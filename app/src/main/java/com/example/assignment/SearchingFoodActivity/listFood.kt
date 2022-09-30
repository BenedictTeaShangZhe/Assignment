package com.example.assignment.SearchingFoodActivity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.R
import com.example.assignment.donerList
import com.google.firebase.database.*

class listFood : AppCompatActivity(), foodAdapter.OnItemClickListener{

    private lateinit var myDB: FirebaseDatabase
    private lateinit var foodRef: DatabaseReference
    private lateinit var foodArrayList: ArrayList<donerList>
    private lateinit var rvFoodList : RecyclerView
    private lateinit var btnCart: Button
    private lateinit var btnDirection : Button
    lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var address : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_food)

        toolbar = findViewById(R.id.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_back)
        toolbar.setNavigationOnClickListener(){
            finish()
        }

        btnDirection = findViewById(R.id.btnDirection)
        btnCart = findViewById(R.id.btnToCart)

        val stationname = intent.getStringExtra("stationName")
        val stationaddress = intent.getStringExtra("stationAddress")

        myDB = FirebaseDatabase.getInstance()
        foodRef = myDB.getReference("Doner Details")
        foodArrayList = arrayListOf<donerList>()

        val foodAdapter = foodAdapter(foodArrayList,this)
        rvFoodList = findViewById(R.id.rvFoodList)
        rvFoodList.adapter = foodAdapter
        rvFoodList.layoutManager= LinearLayoutManager(this)
        rvFoodList.setHasFixedSize(true)

        getFoodData(stationname)

        btnCart.setOnClickListener(){
            val intent = Intent(this,listCart::class.java)
            startActivity(intent)
        }

        btnDirection.setOnClickListener(){
            val address = Uri.parse("geo:0,0?q=" + stationaddress)
            val intent = Intent(Intent.ACTION_VIEW, address)
            startActivity(intent)
        }

    }

    private fun getFoodData(location: String?){
        foodRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot){
                if(snapshot.exists()){
                    for (foodSnapshot in snapshot.children){
                        if (foodSnapshot.child("location").value == location) {
                            val list = foodSnapshot.getValue(donerList::class.java)
                            foodArrayList.add(list!!)
                        }
                    }
                    val adapter = foodAdapter(foodArrayList,this@listFood)
                    rvFoodList.adapter= adapter
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, "Error Occured", Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun itemClick(position: Int) {
        val selectFood = foodArrayList[position]
        val bundle1 = Bundle()
        val bundle2 = Bundle()
        bundle1.putString("foodName", selectFood.food)
        bundle2.putString("stationAddress",selectFood.location)
        val intent = Intent(this, listCart::class.java)
        intent.putExtras(bundle1)
        intent.putExtras(bundle2)
        startActivity(intent)
    }
}
