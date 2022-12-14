package com.example.assignment.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.assignment.BaseApplication
import com.example.assignment.R
import com.example.assignment.databinding.FragmentAddAssignmentBinding
import com.example.assignment.model.Assignment
import com.example.assignment.ui.viewmodel.AssignmentViewModelFactory
import com.example.assignment.ui.viewmodel.AssignmentViewModel


class AddAssignmentFragment : Fragment() {

    private val navigationArgs: AddAssignmentFragmentArgs by navArgs()

    private var _binding: FragmentAddAssignmentBinding? = null

    private lateinit var assignment: Assignment

    private val binding get() = _binding!!

    private val viewModel: AssignmentViewModel by activityViewModels{
        AssignmentViewModelFactory(
            (activity?.applicationContext as BaseApplication).database.assignmentDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAddAssignmentBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.id
        if (id > 0) {

            viewModel.assignment(id).observe(viewLifecycleOwner) {
                assignment = it
                bindAssignment(it)
            }

            binding.deleteBtn.visibility = View.VISIBLE
            binding.deleteBtn.setOnClickListener {
                deleteAssignment(assignment)
            }
        } else {
            binding.saveBtn.setOnClickListener {
                addAssignment()
            }
        }
    }

    private fun deleteAssignment(assignment: Assignment) {
        viewModel.deleteAssignment(assignment)
        findNavController().navigate(
            R.id.action_addAssignmentFragment_to_assignmentListFragment
        )
    }

    private fun addAssignment() {
        if (isValidEntry()) {
            viewModel.addAssignment(
                binding.nameInput.text.toString(),
                binding.locationAddressInput.text.toString(),
                binding.inSeasonCheckbox.isChecked,
                binding.notesInput.text.toString()
            )
            findNavController().navigate(
                R.id.action_addAssignmentFragment_to_assignmentListFragment
            )
        }
    }

    private fun updateAssignment() {
        if (isValidEntry()) {
            viewModel.updateAssignment(
                id = navigationArgs.id,
                name = binding.nameInput.text.toString(),
                address = binding.locationAddressInput.text.toString(),
                inSeason = binding.inSeasonCheckbox.isChecked,
                notes = binding.notesInput.text.toString()
            )
            findNavController().navigate(
                R.id.action_addAssignmentFragment_to_assignmentListFragment
            )
        }
    }

    private fun bindAssignment(assignment: Assignment) {
        binding.apply{
            nameInput.setText(assignment.name, TextView.BufferType.SPANNABLE)
            locationAddressInput.setText(assignment.address, TextView.BufferType.SPANNABLE)
            inSeasonCheckbox.isChecked = assignment.inSeason
            notesInput.setText(assignment.notes, TextView.BufferType.SPANNABLE)
            saveBtn.setOnClickListener {
                updateAssignment()
            }
        }

    }

    private fun isValidEntry() = viewModel.isValidEntry(
        binding.nameInput.text.toString(),
        binding.locationAddressInput.text.toString()
    )

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
