package com.tejasbhong.calendar.feature.calendar.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.transition.platform.MaterialFadeThrough
import com.tejasbhong.calendar.R
import com.tejasbhong.calendar.databinding.FragmentCalendarBinding
import com.tejasbhong.calendar.feature.calendar.domain.model.CalendarItem.DayOfMonth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class CalendarFragment : Fragment(R.layout.fragment_calendar) {
    private lateinit var viewBinding: FragmentCalendarBinding
    private lateinit var calendarMonthAdapter: CalendarMonthAdapter
    private val viewModel by viewModels<CalendarViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialFadeThrough()
        exitTransition = MaterialFadeThrough()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUi()
        observeUiData()
    }

    private fun setupUi() {
        viewBinding = FragmentCalendarBinding.bind(requireView())
        viewBinding.previousMb.setOnClickListener {
            viewBinding.calendarVp2.setCurrentItem(viewBinding.calendarVp2.currentItem - 1, true)
        }
        viewBinding.currentMonthYearMtv.setOnClickListener {
            showMonthYearPicker()
        }
        viewBinding.nextMb.setOnClickListener {
            viewBinding.calendarVp2.setCurrentItem(viewBinding.calendarVp2.currentItem + 1, true)
        }
        viewBinding.daysOfWeekRv.adapter = CalendarDayOfWeekAdapter()
        calendarMonthAdapter = CalendarMonthAdapter(
            onClick = { dayOfMonth: DayOfMonth ->
                findNavController().navigate(
                    CalendarFragmentDirections.actionCalendarFragmentToTasksFragment(dayOfMonth.epoch)
                )
            },
        )
        viewBinding.calendarVp2.adapter = calendarMonthAdapter
        viewBinding.calendarVp2.offscreenPageLimit = 1
        viewBinding.calendarVp2.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            val calendar = Calendar.getInstance()
            var lastPosition = 24
            override fun onPageSelected(position: Int) {
                val monthOffset = position - lastPosition
                calendar.add(Calendar.MONTH, monthOffset)
                lastPosition = position
                viewBinding.currentMonthYearMtv.text = String.format(
                    Locale.getDefault(),
                    "%tB %d",
                    calendar,
                    calendar.get(Calendar.YEAR),
                )
            }
        })
    }

    private fun showMonthYearPicker() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.select_month_year)
            .setSingleChoiceItems(
                viewModel.monthAndYears.toTypedArray(),
                viewBinding.calendarVp2.currentItem
            ) { dialog, which ->
                viewBinding.calendarVp2.setCurrentItem(which, false)
                dialog.dismiss()
            }
            .show()
    }

    private fun observeUiData() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.uiData.collect { uiData: UiData ->
                    calendarMonthAdapter.submitList(uiData.monthsData) {
                        viewBinding.calendarVp2.setCurrentItem((uiData.monthsData.size - 1) / 2, false)
                    }
                }
            }
        }
    }
}
