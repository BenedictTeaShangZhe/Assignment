package com.example.assignment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class testAdapter(private val userList : ArrayList<UserDataClass>) : RecyclerView.Adapter<testAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.user_item,
            parent,false)
        return MyViewHolder(itemView)

    }



    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentitem = userList[position]

        holder.name.text = currentitem.name
        holder.phone.text = currentitem.phone
        holder.location.text = currentitem.location
        holder.address.text = currentitem.address
        holder.food.text = currentitem.food
        holder.foodType.text = currentitem.foodType
        holder.quantity.text = currentitem.quantity

    }

    override fun getItemCount(): Int {

        return userList.size
    }

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val name : TextView = itemView.findViewById(R.id.tvTestName)
        val phone : TextView = itemView.findViewById(R.id.tvTestPhone)
        val location : TextView = itemView.findViewById(R.id.tvTestLocation)
        val address : TextView = itemView.findViewById(R.id.tvTestAddress)
        val food : TextView = itemView.findViewById(R.id.tvTestFood)
        val foodType : TextView = itemView.findViewById(R.id.tvTestFoodType)
        val quantity : TextView = itemView.findViewById(R.id.tvTestQuantity)



    }




}