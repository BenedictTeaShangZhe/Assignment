package com.example.assignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.fragment.FoodDonateRecordFragment
import com.example.assignment.fragment.FoodRequestRecordFragment

class DisplayFoodRecord : AppCompatActivity(){


    private val foodDonateFragment = FoodDonateRecordFragment()
    private val foodRequestFragment = FoodRequestRecordFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_food_record)
        //replaceFragment(foodDonateFragment)

        val btnFragmentForDonate = findViewById<Button>(R.id.foodDonateRecordFragment)
        val btnFragmentForRequest = findViewById<Button>(R.id.foodRequestRecordFragment)


        btnFragmentForDonate.setOnClickListener()
        {
            //replaceFragment(foodDonateFragment)
            //val intent = Intent(this, DisplayingFoodDonateRecord::class.java)
            val intent = Intent(this,UserlistActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnFragmentForRequest.setOnClickListener()
        {
            //replaceFragment(foodRequestFragment)
            val intent = Intent(this, DisplayingFoodRequestRecord::class.java)
            startActivity(intent)


        }
    }

   /* private fun replaceFragment(fragment: Fragment){
        if(fragment != null)
        {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()
        }
    }*/



}