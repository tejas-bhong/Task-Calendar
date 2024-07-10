package com.tejasbhong.calendar.feature.tasks.presentation

import android.os.Bundle
import android.view.View
import androidx.core.view.doOnPreDraw
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.platform.MaterialFadeThrough
import com.tejasbhong.calendar.R
import com.tejasbhong.calendar.common.domain.model.Task
import com.tejasbhong.calendar.databinding.FragmentTasksBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Locale

@AndroidEntryPoint
class TasksFragment : Fragment(R.layout.fragment_tasks) {
    private lateinit var viewBinding: FragmentTasksBinding
    private lateinit var tasksAdapter: TasksAdapter
    private val viewModel by viewModels<TasksViewModel>()
    private val args by navArgs<TasksFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialFadeThrough()
        exitTransition = MaterialFadeThrough()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUi()
        setupObservables()
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchAllTasks(userId = 123, epoch = args.selectedDate)
    }

    private fun setupUi() {
        viewBinding = FragmentTasksBinding.bind(requireView())
        viewBinding.root.doOnPreDraw {
            viewBinding.tasksRv.updatePadding(bottom = (viewBinding.addTaskEfab.height * 1.3).toInt())
        }
        viewBinding.selectedDateMtv.text = SimpleDateFormat(
            "EEEE, dd MMMM yyyy",
            Locale.getDefault(),
        ).format(args.selectedDate)
        tasksAdapter = TasksAdapter(
            onDeleteClick = { task: Task ->
                viewModel.onDeleteClick(task, args.selectedDate)
            },
        )
        viewBinding.tasksRv.adapter = tasksAdapter
        viewBinding.addTaskEfab.setOnClickListener {
            findNavController().navigate(
                TasksFragmentDirections.actionTasksFragmentToAddTaskFragment(args.selectedDate)
            )
        }
    }

    private fun setupObservables() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.uiData.collect { uiData: UiData ->
                    withContext(Dispatchers.Main) {
                        if (uiData.tasks.isEmpty()) {
                            viewBinding.noTasksMtv.visibility = View.VISIBLE
                            viewBinding.tasksRv.visibility = View.GONE
                        } else {
                            viewBinding.noTasksMtv.visibility = View.GONE
                            viewBinding.tasksRv.visibility = View.VISIBLE
                        }
                        tasksAdapter.submitList(uiData.tasks)
                    }
                }
            }
        }
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.vmEvent.collect { vmEvent: VmEvent ->
                    withContext(Dispatchers.Main) {
                        when (vmEvent) {
                            is VmEvent.FailedToLoadTasks, VmEvent.FailedToDeleteTask -> informInternetNotAvailableDialog()

                            is VmEvent.TaskDeleted -> informTaskDeleted()
                        }
                    }
                }
            }
        }
    }

    private fun informInternetNotAvailableDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.an_error_occurred)
            .setMessage(getString(R.string.check_your_internet_connection))
            .setPositiveButton(R.string.ok, null)
            .show()
    }

    private fun informTaskDeleted() {
        Snackbar.make(
            viewBinding.root,
            R.string.task_deleted,
            Snackbar.LENGTH_SHORT,
        ).setAnchorView(viewBinding.addTaskEfab).show()
    }
}
