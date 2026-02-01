package com.khalith.quiethours.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.khalith.quiethours.data.GhostNumber
import com.khalith.quiethours.databinding.ItemGhostNumberBinding

class GhostNumberAdapter(private val onDeleteClick: (GhostNumber) -> Unit) :
    ListAdapter<GhostNumber, GhostNumberAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemGhostNumberBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ViewHolder(private val binding: ItemGhostNumberBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GhostNumber) {
            binding.tvName.text = item.name ?: "Unknown"
            binding.tvNumber.text = item.number
            binding.btnDelete.setOnClickListener { onDeleteClick(item) }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<GhostNumber>() {
        override fun areItemsTheSame(oldItem: GhostNumber, newItem: GhostNumber): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: GhostNumber, newItem: GhostNumber): Boolean =
            oldItem == newItem
    }
}
