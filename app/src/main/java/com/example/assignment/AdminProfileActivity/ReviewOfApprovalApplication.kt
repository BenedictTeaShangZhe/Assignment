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

class ReviewOfApprovalApplication : AppCompatActivity(),UserApprovalRecyclerviewAdapter.OnItemClickListener {
    //realtime database initialization (Copy)
    private lateinit var myDB: FirebaseDatabase
    private lateinit var myRef: DatabaseReference
    private lateinit var approval_recyclerview :RecyclerView
    private lateinit var approvalArrayList : ArrayList<User>

    //XML initialization
    lateinit var toolbar: androidx.appcompat.widget.Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review_of_approval_application)
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

        approval_recyclerview = findViewById(R.id.approved_recyclerview)
        approval_recyclerview.layoutManager = LinearLayoutManager(this)
        approval_recyclerview.setHasFixedSize(true)

        approvalArrayList = arrayListOf<User>()
        getApprovalData()

    }

    private fun getApprovalData() {
        myRef.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(approvalSnapshot in snapshot.children){
                        if(approvalSnapshot.child("status").value=="Requesting"){
                            val approval = approvalSnapshot.getValue(User::class.java)
                            approvalArrayList.add(approval!!)
                        }
                    }
                    var adapter = UserApprovalRecyclerviewAdapter(approvalArrayList,this@ReviewOfApprovalApplication)
                    approval_recyclerview.adapter= adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, "Error Occured", Toast.LENGTH_LONG).show()
            }

        })
    }

    override fun itemClick(position: Int) {
        val selectItem = approvalArrayList[position]
        val bundle = Bundle()
        bundle.putString("phone", selectItem.phoneNumber)

        val intent = Intent(this, ReviewSingelApprovalApplication::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
        finish()
    }
}