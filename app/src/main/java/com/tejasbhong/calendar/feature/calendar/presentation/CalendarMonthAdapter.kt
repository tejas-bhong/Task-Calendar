package com.tejasbhong.calendar.feature.calendar.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tejasbhong.calendar.databinding.ItemCalendarBinding
import com.tejasbhong.calendar.feature.calendar.domain.model.CalendarItem

class CalendarMonthAdapter(
    private val onClick: (CalendarItem.DayOfMonth) -> Unit,
) : ListAdapter<List<CalendarItem>, CalendarMonthAdapter.ViewHolder>(
    CalendarItemListDiffCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCalendarBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class ViewHolder(
        private val binding: ItemCalendarBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        private var adapter = CalendarDayAdapter(
            binding.root.context,
            onClick = onClick,
        )

        fun bind(position: Int) {
            binding.calendarRv.adapter = adapter
            adapter.submitList(getItem(position))
        }
    }

    class CalendarItemListDiffCallback : DiffUtil.ItemCallback<List<CalendarItem>>() {
        override fun areItemsTheSame(
            oldItem: List<CalendarItem>,
            newItem: List<CalendarItem>,
        ): Boolean {
            return false
        }

        override fun areContentsTheSame(
            oldItem: List<CalendarItem>,
            newItem: List<CalendarItem>,
        ): Boolean {
            return false
        }
    }
}
