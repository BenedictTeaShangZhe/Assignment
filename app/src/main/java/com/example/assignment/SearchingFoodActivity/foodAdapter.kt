package com.example.assignment.SearchingFoodActivity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.R

class foodAdapter(private val foodList: ArrayList<FoodData>, private val listener: foodAdapter.OnItemClickListener)
    : RecyclerView.Adapter<foodAdapter.MyViewHolder> () {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener{

        val foodName: TextView = view.findViewById(R.id.tvFoodName)
        val foodType: TextView = view.findViewById(R.id.tvfoodType)
        val foodQuantity: TextView = view.findViewById(R.id.tvFoodQuantity)

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            listener.itemClick(position)
        }
    }

    interface OnItemClickListener{
        fun itemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.food_rv_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = foodList[position]

        holder.foodName.text = currentItem.foodName
        holder.foodType.text = currentItem.foodType
        holder.foodQuantity.text = currentItem.foodQuantity.toString()

    }

    override fun getItemCount(): Int {
        return foodList.size
    }

}