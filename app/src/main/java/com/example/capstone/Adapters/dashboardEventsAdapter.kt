package com.example.capstone.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.capstone.List.Events
import com.example.capstone.R

class dashboardEventsAdapter (private val eventsList: ArrayList<Events>) : RecyclerView.Adapter<dashboardEventsAdapter.MyViewHolder2>(){



    private lateinit var mListener : onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun onItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    class MyViewHolder2(itemView : View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView){
        val Title: TextView = itemView.findViewById(R.id.title)


        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder2 {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.dashboard_adapter, parent, false)
        return MyViewHolder2(itemView, mListener)
    }

    override fun onBindViewHolder(holder: MyViewHolder2, position: Int) {
        val events: Events = eventsList[position]
        holder.Title.text = events.eventTitle

    }

    override fun getItemCount(): Int = eventsList.size
}