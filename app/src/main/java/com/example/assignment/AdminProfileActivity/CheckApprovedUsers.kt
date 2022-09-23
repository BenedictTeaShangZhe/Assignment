package com.example.assignment.AdminProfileActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.R
import com.example.assignment.User
import com.google.firebase.database.*

class CheckApprovedUsers : AppCompatActivity(),UserApprovalRecyclerviewAdapter.OnItemClickListener {
    //realtime database initialization (Copy)
    private lateinit var myDB: FirebaseDatabase
    private lateinit var myRef: DatabaseReference
    private lateinit var approved_recyclerview : RecyclerView
    private lateinit var approvedArrayList : ArrayList<User>

    //XML initialization
    lateinit var toolbar: androidx.appcompat.widget.Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_approved_users)

        //toolbar declaration
        toolbar = findViewById(R.id.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_back)
        toolbar.setNavigationOnClickListener(){
            finish()
        }

        //Database declaration(Copy)
        myDB = FirebaseDatabase.getInstance()
        //reference = database table
        myRef = myDB.getReference("User")

        approved_recyclerview = findViewById(R.id.approved_recyclerview)
        approved_recyclerview.layoutManager = LinearLayoutManager(this)
        approved_recyclerview.setHasFixedSize(true)

        approvedArrayList = arrayListOf<User>()
        getApprovedUserData()
    }

    private fun getApprovedUserData() {
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(approvalSnapshot in snapshot.children){
                        if(approvalSnapshot.child("status").value=="Approved"){
                            val approval = approvalSnapshot.getValue(User::class.java)
                            approvedArrayList.add(approval!!)
                        }
                    }
                    var adapter = UserApprovalRecyclerviewAdapter(approvedArrayList,this@CheckApprovedUsers)
                    approved_recyclerview.adapter= adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, "Error Occured", Toast.LENGTH_LONG).show()
            }

        })
    }

    override fun itemClick(position: Int) {
        val selectItem = approvedArrayList[position]
        val bundle = Bundle()
        bundle.putString("phone", selectItem.phoneNumber)

        val intent = Intent(this, CheckSingleApprovedUser::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
        finish()
    }
}