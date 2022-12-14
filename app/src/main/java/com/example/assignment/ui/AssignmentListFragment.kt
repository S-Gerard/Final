package com.example.assignment.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.assignment.BaseApplication
import com.example.assignment.R
import com.example.assignment.databinding.FragmentAssignmentListBinding
import com.example.assignment.model.Assignment
import com.example.assignment.ui.adapter.AssignmentListAdapter
import com.example.assignment.ui.viewmodel.AssignmentViewModel
import com.example.assignment.ui.viewmodel.AssignmentViewModelFactory

class AssignmentListFragment : Fragment() {

    private val viewModel: AssignmentViewModel by activityViewModels {
        AssignmentViewModelFactory(
            (activity?.application as BaseApplication).database.assignmentDao()
        )
    }

    private var _binding: FragmentAssignmentListBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAssignmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = AssignmentListAdapter { assignment ->
            val action = AssignmentListFragmentDirections
                .actionAssignmentListFragmentToAssignmentDetailFragment(assignment.id)
            findNavController().navigate(action)
        }

        viewModel.assignments.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        binding.apply {
            recyclerView.adapter = adapter
            addAssignmentFab.setOnClickListener {
                findNavController().navigate(
                    R.id.action_assignmentListFragment_to_addAssignmentFragment
                )
            }
        }
    }

}
