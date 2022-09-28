package com.example.assignment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class donerRequestAdapter(private val donerRequestList : List<donerRequest>,
                          private val listener: OnItemClickListener)
//UserAdapter is receive data input
//input pass in now in this project is UserList(input parameter)

//we need know user click which view /column just click which row

    : RecyclerView.Adapter<donerRequestAdapter.MyViewHolder> () {
    //:user adapter is type of recycler view


    inner class MyViewHolder(view: View)
        : RecyclerView.ViewHolder(view), View.OnClickListener{

        val tvName: TextView = view.findViewById(R.id.tvDonerRequestName)
        val tvPhone: TextView = view.findViewById(R.id.tvDonerRequestTelephoneNo)
        val tvLocation: TextView = view.findViewById(R.id.tvDonerRequestLocation)
        val tvAddress: TextView = view.findViewById(R.id.tvDonerRequestAddress)
        val tvFoodType: TextView = view.findViewById(R.id.tvDonerRequestFoodType)
        val tvRemark: TextView = view.findViewById(R.id.tvDonerRequestRemarks)

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {

            val position = bindingAdapterPosition

            if(position != RecyclerView.NO_POSITION){
                listener.itemClick(position)
            }
        }
    }

    interface OnItemClickListener{
        fun itemClick(position: Int)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.request_layout_view, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //while user click on recycler view
        //position is current user see  on recycle view if current user see no3 column then will be display no3 record
        val currentUser = donerRequestList[position]

//        holder.tvId.text = currentStudent.id
        holder.tvName.text = currentUser.name
        holder.tvPhone.text = currentUser.telephoneNumber
        holder.tvLocation.text = currentUser.location
        holder.tvAddress.text = currentUser.address
        holder.tvFoodType.text = currentUser.foodType
        holder.tvRemark.text = currentUser.remark

    }

    override fun getItemCount(): Int {
        return donerRequestList.size
    }

}