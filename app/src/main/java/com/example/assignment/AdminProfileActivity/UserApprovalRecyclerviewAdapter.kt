package com.example.assignment.AdminProfileActivity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.R
import com.example.assignment.User


class UserApprovalRecyclerviewAdapter (private val approvalList : ArrayList<User>,
                   private val listener: OnItemClickListener)

    : RecyclerView.Adapter<UserApprovalRecyclerviewAdapter.MyViewHolder> () {

    inner class MyViewHolder(view: View)
        : RecyclerView.ViewHolder(view), View.OnClickListener{

        val tvUserPhoneNumber : TextView = itemView.findViewById(R.id.tvUserPhoneNumber)
        val tvUserName : TextView = itemView.findViewById(R.id.tvUserName)

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
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.approval_recyclerview_item, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = approvalList[position]
        holder.tvUserPhoneNumber.text = currentItem.phoneNumber
        holder.tvUserName.text = currentItem.name
    }

    override fun getItemCount(): Int {
        return approvalList.size
    }

}
