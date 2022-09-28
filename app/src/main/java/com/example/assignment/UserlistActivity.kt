package com.example.assignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class UserlistActivity : AppCompatActivity() {

    private lateinit var dbref : DatabaseReference
    private lateinit var userRecyclerView : RecyclerView
    private lateinit var userArrayList : ArrayList<UserDataClass>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_userlist)

        userRecyclerView = findViewById(R.id.userList)
        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userRecyclerView.setHasFixedSize(true)

        userArrayList = arrayListOf<UserDataClass>()
        getUserData()



    }

    private fun getUserData()
    {
        dbref = FirebaseDatabase.getInstance().getReference("Doner Details")

        dbref.addValueEventListener(object : ValueEventListener
        {
            override fun onDataChange(snapshot: DataSnapshot)
            {
                if(snapshot.exists())
                {
                    for(userSnapshot in snapshot.children)

                    {
                        val user = userSnapshot.getValue(UserDataClass::class.java)
                        userArrayList.add(user!!)
                    }

                    userRecyclerView.adapter = testAdapter(userArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError)
            {
                TODO("Not yet implemented")
            }
        })
    }
}