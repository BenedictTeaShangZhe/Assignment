package com.example.assignment.SearchingFoodActivity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.example.assignment.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class listCart : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences

    //Below are the Key of login sharedPreferences
    private var PREFS_KEY = "prefs"
    private var PHONE_KEY = "phone"
    //Below are to validate the status of login
    private var phone = ""

    private lateinit var myDB: FirebaseDatabase
    private lateinit var userRef: DatabaseReference
    private lateinit var orderedRef: DatabaseReference

    private lateinit var btnConfirmOrder : Button
    lateinit var toolbar: Toolbar
    private lateinit var myCartModel : cartModel
    private lateinit var listCart1 : ListView
    private lateinit var listCart2 : ListView
    private lateinit var rgMode : RadioGroup
    private lateinit var rbDelivery : RadioButton
    private lateinit var rbPickup : RadioButton
    private lateinit var num : String
    private var count : Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_cart)

        toolbar = findViewById(R.id.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_back)
        toolbar.setNavigationOnClickListener(){
            finish()
        }

        myDB = FirebaseDatabase.getInstance()
        userRef = myDB.getReference("User")
        orderedRef = myDB.getReference("userOrdered")

        myCartModel = ViewModelProvider(this).get(cartModel::class.java)
        sharedPreferences = getSharedPreferences(PREFS_KEY, MODE_PRIVATE)
        phone = sharedPreferences.getString(PHONE_KEY, "").toString()

        btnConfirmOrder = findViewById(R.id.btnConfirmOrder)
        rbDelivery = findViewById(R.id.rbDelivery)
        rbPickup = findViewById(R.id.rbPickup)
        listCart1 = findViewById(R.id.lvItemName)
        listCart2 = findViewById(R.id.lvItemQuantity)
        rgMode = findViewById(R.id.rgMode)

        num = "2000"
        var rbIsPickup: Boolean = true

        val address = intent.getStringExtra("stationAddress").toString()
        val name = intent.getStringExtra("foodName").toString()
        val quantity = "1"
        myCartModel.addItem(CartData(name,quantity))
        displayCartList()

        userRef.child(phone).get().addOnSuccessListener {
            num = it.child("birthday").value.toString()
        }
        val userAge = num.takeLast(4)
        val ageInt = userAge.toInt()
        val calendar = Calendar.getInstance()
        val year = calendar[Calendar.YEAR]
        val finalAge = year - ageInt
        rgMode.setOnCheckedChangeListener { rgMode, checkId ->
            if (finalAge < 60 ){
                rbDelivery.setClickable(false)
            }
            else {
                if (checkId == R.id.rbDelivery)
                    rbIsPickup = false
                if (checkId == R.id.rbPickup)
                    rbIsPickup = true
            }
        }

        btnConfirmOrder.setOnClickListener() {
            ++count
            orderedRef.child(count.toString()).get().addOnSuccessListener() {
                val phoneNo = phone
                val foodName = name
                val foodQuantity = quantity
                val foodAddress = address
                val orderStatus = "waiting to receive"
                if (rbIsPickup == true) {
                    val mode = "Pickup"
                    val ordered1 =
                        OrderedData(phoneNo, foodName, foodQuantity, foodAddress, mode, orderStatus)
                    orderedRef.child(count.toString()).setValue(ordered1)
                        .addOnSuccessListener {
                            Toast.makeText(
                                applicationContext,
                                "Successfully ordered",
                                Toast.LENGTH_LONG
                            ).show()
                        }.addOnFailureListener {
                            Toast.makeText(applicationContext, "Error!!", Toast.LENGTH_LONG).show()
                        }
                }
                if (rbIsPickup == false) {
                    val mode = "Delivery"
                    val ordered2 =
                        OrderedData(phoneNo, foodName, foodQuantity, foodAddress, mode, orderStatus)
                    orderedRef.child(count.toString()).setValue(ordered2)
                        .addOnSuccessListener {
                            Toast.makeText(
                                applicationContext,
                                "Successfully ordered",
                                Toast.LENGTH_LONG
                            ).show()
                        }.addOnFailureListener {
                            Toast.makeText(applicationContext, "Error!!", Toast.LENGTH_LONG).show()
                        }
                }
            }
            val intent = Intent(this, listUserOrdered::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun displayCartList() {
        val selectedItemName: MutableList<String> = mutableListOf<String>()
        val selectedItemQuantity: MutableList<String> = mutableListOf<String>()
        for(c:CartData in myCartModel.getCartList()){
            selectedItemName.add(c.itemName)
            selectedItemQuantity.add(c.itemQuantity)
        }

        val adapter1 = ArrayAdapter(this, android.R.layout.simple_list_item_1, selectedItemName)
        val adapter2 = ArrayAdapter(this, android.R.layout.simple_list_item_1, selectedItemQuantity)

        listCart1.adapter = adapter1
        listCart2.adapter = adapter2
    }


}