package com.tejasbhong.calendar.feature.add_task.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.transition.platform.MaterialFadeThrough
import com.tejasbhong.calendar.R
import com.tejasbhong.calendar.databinding.FragmentAddTaskBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Locale

@AndroidEntryPoint
class AddTaskFragment : Fragment(R.layout.fragment_add_task) {
    private lateinit var viewBinding: FragmentAddTaskBinding
    private lateinit var savingAlertDialog: AlertDialog
    private val viewModel by viewModels<AddTaskViewModel>()
    private val args by navArgs<AddTaskFragmentArgs>()

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

    private fun setupUi() {
        savingAlertDialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.saving)
            .setView(R.layout.linear_progress_indicator)
            .setCancelable(false)
            .create()
        viewBinding = FragmentAddTaskBinding.bind(requireView())
        viewBinding.selectedDateMtv.text = SimpleDateFormat(
            "EEEE, dd MMMM yyyy",
            Locale.getDefault(),
        ).format(args.selectedDate)
        viewBinding.titleTiet.doAfterTextChanged { viewBinding.titleTil.error = null }
        viewBinding.descriptionTiet.doAfterTextChanged { viewBinding.descriptionTil.error = null }
        viewBinding.saveTaskMb.setOnClickListener {
            viewModel.onSaveClick(
                args.selectedDate,
                viewBinding.titleTiet.text?.toString(),
                viewBinding.descriptionTiet.text?.toString(),
            )
        }
    }

    private fun setupObservables() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.uiData.collect { uiData: UiData ->
                    withContext(Dispatchers.Main) {
                        viewBinding.titleTil.error = if (uiData.isTitleInvalid) {
                            getString(R.string.invalid)
                        } else {
                            null
                        }
                        viewBinding.descriptionTil.error = if (uiData.isDescriptionInvalid) {
                            getString(R.string.invalid)
                        } else {
                            null
                        }
                    }
                }
            }
        }
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.vmEvent.collect { vmEvent: VmEvent ->
                    withContext(Dispatchers.Main) {
                        savingAlertDialog.dismiss()
                        when (vmEvent) {
                            is VmEvent.Saving -> savingAlertDialog.show()
                            is VmEvent.TaskSaved -> informTaskSaved()
                            is VmEvent.FailedToSaveTask -> informTaskSaveFailed()
                            else -> Unit
                        }
                    }
                }
            }
        }
    }

    private fun informTaskSaved() {
        savingAlertDialog.dismiss()
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.task_saved)
            .setPositiveButton(R.string.ok, null)
            .setOnDismissListener {
                findNavController().navigateUp()
            }
            .show()
    }

    private fun informTaskSaveFailed() {
        savingAlertDialog.dismiss()
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.an_error_occurred)
            .setMessage(R.string.check_your_internet_connection)
            .setPositiveButton(R.string.ok, null)
            .setOnDismissListener {
                findNavController().navigateUp()
            }
            .show()
    }
}
