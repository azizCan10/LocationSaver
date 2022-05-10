package com.example.mapsdeneme.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mapsdeneme.user.MapsActivityUsers
import com.example.mapsdeneme.databinding.RecyclerRowBinding
import com.example.mapsdeneme.model.Place


class UsersAdapter(var placeList: List<Place>) : RecyclerView.Adapter<UsersAdapter.PlaceHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceHolder {
        val recyclerRowBinding: RecyclerRowBinding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlaceHolder(recyclerRowBinding)
    }


    override fun onBindViewHolder(holder: PlaceHolder, position: Int) {
        holder.recyclerRowBinding.recyclerViewTextView.setText(placeList[position].name)
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, MapsActivityUsers::class.java)
            intent.putExtra("selectedPlace2",placeList.get(position))
            holder.itemView.context.startActivity(intent)
        }
    }
    override fun getItemCount(): Int {
        return placeList.size
    }

    class PlaceHolder(val recyclerRowBinding: RecyclerRowBinding) : RecyclerView.ViewHolder(recyclerRowBinding.root) {

    }
}