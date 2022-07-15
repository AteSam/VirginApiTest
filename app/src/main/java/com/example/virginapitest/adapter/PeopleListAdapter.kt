package com.example.virginapitest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.virginapitest.domain.entity.People
import com.example.virginapitest.databinding.PeoplePersonViewBinding

class PeopleListAdapter(private val onItemClicked: (People) -> Unit) : ListAdapter<People, PeopleListAdapter.ItemsViewHolder>(DiffCallback)  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsViewHolder = ItemsViewHolder(
        PeoplePersonViewBinding.inflate(
            LayoutInflater.from(parent.context)))

    override fun onBindViewHolder(holder: ItemsViewHolder, position: Int) {
        val person = getItem(position)
        holder.bind(person)
        holder.itemView.setOnClickListener {
            onItemClicked(person)
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<People>() {
        override fun areItemsTheSame(oldItem: People, newItem: People): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: People, newItem: People): Boolean {
            return oldItem.firstName == newItem.firstName
        }
    }

    inner class ItemsViewHolder(private val binding: PeoplePersonViewBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(person: People) {
            binding.personId.text = person.id.toString()
            binding.personName.text = person.firstName
            binding.personJob.text = person.email
        }
    }

}