package com.tejasbhong.calendar.feature.calendar.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tejasbhong.calendar.databinding.ItemCalendarDayOfWeekBinding

class CalendarDayOfWeekAdapter : RecyclerView.Adapter<CalendarDayOfWeekAdapter.ViewHolder>() {
    private val items = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCalendarDayOfWeekBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(
        private val itemBinding: ItemCalendarDayOfWeekBinding,
    ) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(position: Int) {
            itemBinding.dayOfWeekMtv.text = items[position]
        }
    }
}
