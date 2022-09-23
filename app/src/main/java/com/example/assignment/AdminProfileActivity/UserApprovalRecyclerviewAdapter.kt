package com.example.assignment.AdminProfileActivity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.R
import com.example.assignment.User

class UserApprovalRecyclerviewAdapter(private val approvalList:ArrayList<User>) : RecyclerView.Adapter<UserApprovalRecyclerviewAdapter.MyViewHolder>() {


     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
         val itemView = LayoutInflater.from(parent.context).inflate(R.layout.approval_recyclerview_item,
         parent,false)
         return MyViewHolder(itemView)
     }

     override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
         val currentItem = approvalList[position]
         holder.tvUserPhoneNumber.text = currentItem.PhoneNumber
         holder.tvUserName.text = currentItem.Name
     }

     override fun getItemCount(): Int {
         return approvalList.size
     }


     class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
         val tvUserPhoneNumber : TextView = itemView.findViewById(R.id.tvUserPhoneNumber)
         val tvUserName : TextView = itemView.findViewById(R.id.tvUserName)
     }
 }