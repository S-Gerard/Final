package com.example.assignment.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.databinding.ListItemAssignmentBinding
import com.example.assignment.model.Assignment


class AssignmentListAdapter(
    private val clickListener: (Assignment) -> Unit
) : ListAdapter<Assignment, AssignmentListAdapter.AssignmentViewHolder>(DiffCallback) {

    class AssignmentViewHolder(
        private var binding: ListItemAssignmentBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(assignment: Assignment) {
            binding.assignment = assignment
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback: DiffUtil.ItemCallback<Assignment>() {
        override fun areItemsTheSame(oldItem: Assignment, newItem: Assignment): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Assignment, newItem: Assignment): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssignmentViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return AssignmentViewHolder(
            ListItemAssignmentBinding.inflate(layoutInflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: AssignmentViewHolder, position: Int) {
        val assignment = getItem(position)
        holder.itemView.setOnClickListener{
            clickListener(assignment)
        }
        holder.bind(assignment)
    }
}
