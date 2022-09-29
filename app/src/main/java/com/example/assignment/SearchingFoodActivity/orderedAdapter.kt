package com.example.assignment.SearchingFoodActivity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.R

class orderedAdapter(private val userOrderedList : ArrayList<dataOrdered>)
    : RecyclerView.Adapter<orderedAdapter.MyViewHolder> () {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val oFoodName: TextView = view.findViewById(R.id.tvOfoodName)
        val oFoodAddress: TextView = view.findViewById(R.id.tvOfoodAddress)
        val oMode: TextView = view.findViewById(R.id.tvOmode)
        val oFoodQuantity: TextView = view.findViewById(R.id.tvOfoodQuantity)
        val oStatus: TextView = view.findViewById(R.id.tvOstatus)
        val oPhone: TextView = view.findViewById(R.id.tvOphoneNo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.food_rv_user_ordered, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = userOrderedList[position]

        holder.oFoodName.text= currentItem.foodName
        holder.oFoodQuantity.text=currentItem.foodQuantity
        holder.oMode.text=currentItem.mode
        holder.oStatus.text=currentItem.orderStatus
        holder.oFoodAddress.text=currentItem.foodAddress
        holder.oPhone.text=currentItem.phoneNo

    }

    override fun getItemCount(): Int {
        return userOrderedList.size
    }
}