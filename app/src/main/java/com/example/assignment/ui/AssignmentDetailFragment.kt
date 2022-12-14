package com.example.assignment.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.assignment.BaseApplication
import com.example.assignment.databinding.FragmentAssignmentDetailBinding
import com.example.assignment.model.Assignment
import com.example.assignment.ui.viewmodel.AssignmentViewModel
import com.example.assignment.ui.viewmodel.AssignmentViewModelFactory

class AssignmentDetailFragment : Fragment() {

    private val navigationArgs: AssignmentDetailFragmentArgs by navArgs()

    private val viewModel: AssignmentViewModel by activityViewModels{
        AssignmentViewModelFactory (
            (activity?.applicationContext as BaseApplication).database.assignmentDao()
        )
    }

    private lateinit var assignment: Assignment

    private var _binding: FragmentAssignmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAssignmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.id
        viewModel.assignment(id).observe(viewLifecycleOwner) {
            assignment = it
            bindAssignment()
        }
    }

    private fun bindAssignment() {
        binding.apply {
            name.text = assignment.name
            type.text = assignment.type
            notes.text = assignment.notes
            days.text = assignment.days
            editAssignmentFab.setOnClickListener {
                val action = AssignmentDetailFragmentDirections
                    .actionAssignmentDetailFragmentToAddAssignmentFragment(assignment.id)
                findNavController().navigate(action)
            }

        }
    }
}
