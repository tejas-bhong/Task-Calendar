package com.tejasbhong.calendar.feature.tasks.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tejasbhong.calendar.common.domain.model.Task
import com.tejasbhong.calendar.databinding.ItemTaskBinding

class TasksAdapter(
    private val onDeleteClick: (task: Task) -> Unit,
) : ListAdapter<Task, TasksAdapter.ViewHolder>(TaskDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemTaskBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class ViewHolder(
        private val binding: ItemTaskBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.deleteMb.setOnClickListener {
                if (adapterPosition == -1) return@setOnClickListener
                onDeleteClick(getItem(adapterPosition))
            }
        }

        fun bind(position: Int) {
            val task = getItem(position)
            binding.titleMtv.text = task.title
            binding.descriptionMtv.text = task.description
        }
    }

    class TaskDiffCallback : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.title == newItem.title && oldItem.description == newItem.description
        }
    }
}
