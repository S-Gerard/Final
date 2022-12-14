package com.example.assignment.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.assignment.BaseApplication
import com.example.assignment.R
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
            location.text = assignment.address
            notes.text = assignment.notes
            if (assignment.inSeason) {
                season.text = getString(R.string.in_season)
            } else {
                season.text = getString(R.string.out_of_season)
            }
            editAssignmentFab.setOnClickListener {
                val action = AssignmentDetailFragmentDirections
                    .actionAssignmentDetailFragmentToAddAssignmentFragment(assignment.id)
                findNavController().navigate(action)
            }

            location.setOnClickListener {
                launchMap()
            }
        }
    }

    private fun launchMap() {
        val address = assignment.address.let {
            it.replace(", ", ",")
            it.replace(". ", " ")
            it.replace(" ", "+")
        }
        val gmmIntentUri = Uri.parse("geo:0,0?q=$address")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)
    }
}
