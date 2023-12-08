package com.example.capstone

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.capstone.List.LocalShopArray

class LocalshopAdapter(private val shopList: ArrayList<LocalShopArray>) : RecyclerView.Adapter<LocalshopAdapter.MyViewHolder2>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder2 {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.shop_adapter, parent, false)
        return MyViewHolder2(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder2, position: Int) {

        holder.shopName.text = shopList[position].ShopName
        holder.shopDes.text = shopList[position].ShopDescription
        holder.shopLoc.text = shopList[position].ShopLocation
    }

    override fun getItemCount(): Int = shopList.size

    class MyViewHolder2(itemView : View) : RecyclerView.ViewHolder(itemView){
        val shopName: TextView = itemView.findViewById(R.id.shopName)
        val shopDes: TextView = itemView.findViewById(R.id.shopDes)
        val shopLoc: TextView = itemView.findViewById(R.id.shopLoc)

    }

}