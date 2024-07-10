package com.tejasbhong.calendar.feature.calendar.presentation

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.color.MaterialColors
import com.tejasbhong.calendar.databinding.ItemCalendarDayBinding
import com.tejasbhong.calendar.feature.calendar.domain.model.CalendarItem
import java.text.SimpleDateFormat
import java.util.Locale

class CalendarDayAdapter(
    context: Context,
    private val onClick: (CalendarItem.DayOfMonth) -> Unit,
) : ListAdapter<CalendarItem, CalendarDayAdapter.ViewHolder>(CalendarItemDiffCallback()) {
    private val simpleDayOfMonthFormat = SimpleDateFormat("dd", Locale.getDefault())
    private val colorPrimaryContainer: Int = MaterialColors.getColor(
        context,
        com.google.android.material.R.attr.colorPrimaryContainer,
        Color.TRANSPARENT,
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCalendarDayBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class ViewHolder(
        private val itemBinding: ItemCalendarDayBinding,
    ) : RecyclerView.ViewHolder(itemBinding.root) {
        init {
            itemBinding.root.setOnClickListener {
                if (adapterPosition == -1) return@setOnClickListener
                val calendarItem = getItem(adapterPosition)
                if (calendarItem !is CalendarItem.DayOfMonth) return@setOnClickListener
                onClick(calendarItem)
            }
        }

        fun bind(position: Int) {
            when (val calendarItem = getItem(position)) {
                is CalendarItem.DayOfMonth -> handleDayOfMonth(calendarItem)
                is CalendarItem.Empty -> handleEmpty()
            }
        }

        private fun handleEmpty() {
            itemBinding.root.visibility = View.INVISIBLE
        }

        private fun handleDayOfMonth(calendarItem: CalendarItem.DayOfMonth) {
            itemBinding.root.visibility = View.VISIBLE
            itemBinding.root.setCardBackgroundColor(
                if (calendarItem.isToday) {
                    colorPrimaryContainer
                } else {
                    Color.TRANSPARENT
                }
            )
            itemBinding.dayOfMonthMtv.text = simpleDayOfMonthFormat.format(calendarItem.epoch)
            itemBinding.tasksCountMtv.text = if (calendarItem.tasksCount == 0) ""
            else calendarItem.tasksCount.toString()
        }
    }

    class CalendarItemDiffCallback : DiffUtil.ItemCallback<CalendarItem>() {
        override fun areItemsTheSame(oldItem: CalendarItem, newItem: CalendarItem): Boolean {
            return oldItem.epoch == newItem.epoch
        }

        override fun areContentsTheSame(oldItem: CalendarItem, newItem: CalendarItem): Boolean {
            return when {
                oldItem is CalendarItem.DayOfMonth && newItem is CalendarItem.DayOfMonth -> oldItem.isToday == newItem.isToday && oldItem.tasksCount == newItem.tasksCount && oldItem.epoch == newItem.epoch
                oldItem is CalendarItem.Empty && newItem is CalendarItem.Empty -> true
                else -> false
            }
        }
    }
}
