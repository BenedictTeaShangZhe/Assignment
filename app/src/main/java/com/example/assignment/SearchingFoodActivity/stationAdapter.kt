package com.example.assignment.SearchingFoodActivity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.R

class stationAdapter (private val stationList : ArrayList<StationData>, private val listener: OnItemClickListener)
    : RecyclerView.Adapter<stationAdapter.MyViewHolder> () {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener{

        val stationName: TextView = view.findViewById(R.id.tvStationName)
        val stationAddress: TextView = view.findViewById(R.id.tvStationAddress)

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
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.food_station_rv_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = stationList[position]

        holder.stationName.text= currentItem.StationName
        holder.stationAddress.text = currentItem.StationAddress

    }

    override fun getItemCount(): Int {
        return stationList.size
    }
}