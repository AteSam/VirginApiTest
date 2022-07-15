package com.example.virginapitest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.virginapitest.databinding.RoomsListViewBinding
import com.example.virginapitest.domain.entity.Rooms

class RoomsListAdapter (private val onItemClicked: (Rooms) -> Unit) : ListAdapter<Rooms, RoomsListAdapter.ItemsViewHolder>(DiffCallback)  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsViewHolder = ItemsViewHolder(
        RoomsListViewBinding.inflate(
            LayoutInflater.from(parent.context)))

    override fun onBindViewHolder(holder: ItemsViewHolder, position: Int) {
        val room = getItem(position)
        holder.bind(room)
        holder.itemView.setOnClickListener {
            onItemClicked(room)
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Rooms>() {
        override fun areItemsTheSame(oldItem: Rooms, newItem: Rooms): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Rooms, newItem: Rooms): Boolean {
            return oldItem.name == newItem.name
        }
    }

    inner class ItemsViewHolder(private val binding: RoomsListViewBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(room: Rooms) {
            binding.roomName.text = room.name
            binding.maxOccupancy.text = room.max_occupancy.toString()
            binding.isOccupied.text = room.is_occupied.toString()
        }
    }

}