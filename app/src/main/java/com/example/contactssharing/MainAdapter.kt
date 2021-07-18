package com.example.contactssharing

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class MainAdapter(var activity: Activity, var arrayList: ArrayList<ContactModel>) :
    RecyclerView.Adapter<MainAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_contact, parent,  false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = arrayList[position]
        holder.tvName.text = model.name
        holder.tvNumber.text = model.number
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    override fun getItemCount(): Int {
        return arrayList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //initialize variable
        var tvName: TextView
        var tvNumber: TextView

        init {
            tvName = itemView.findViewById(R.id.tv_name)
            tvNumber = itemView.findViewById(R.id.tv_number)
        }
    }

    init {
        notifyDataSetChanged()
    }
}