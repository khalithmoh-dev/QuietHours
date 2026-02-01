package com.khalith.quiethours.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.khalith.quiethours.data.GhostLog
import com.khalith.quiethours.databinding.ItemGhostLogBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class GhostLogAdapter : ListAdapter<GhostLog, GhostLogAdapter.ViewHolder>(DiffCallback()) {

    private val dateFormat = SimpleDateFormat("MMM dd, HH:mm:ss", Locale.getDefault())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemGhostLogBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ViewHolder(private val binding: ItemGhostLogBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GhostLog) {
            binding.tvLogNumber.text = item.number
            binding.tvLogTime.text = dateFormat.format(Date(item.time))
            binding.tvLogMode.text = "Mode: ${item.mode}"
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<GhostLog>() {
        override fun areItemsTheSame(oldItem: GhostLog, newItem: GhostLog): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: GhostLog, newItem: GhostLog): Boolean =
            oldItem == newItem
    }
}
