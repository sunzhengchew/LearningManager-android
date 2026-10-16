package org.setu.learningmanager.assignment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.setu.learningmanager.R
import org.setu.learningmanager.models.AssignmentModel

class AssignmentAdapter(
    private var assignments: List<AssignmentModel>,
    private val onEdit: (AssignmentModel) -> Unit,
    private val onDelete: (AssignmentModel) -> Unit,
    private val doneListener: DoneListener,
) : RecyclerView.Adapter<AssignmentAdapter.AssignmentViewHolder>() {

    class AssignmentViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val titleText: TextView =
            itemView.findViewById(R.id.titleText)

        val descriptionInput: TextView = itemView.findViewById(R.id.descriptionText)

        val deadlineText: TextView = itemView.findViewById(R.id.deadlineText)

        val statusText: TextView = itemView.findViewById(R.id.statusText)

        val editButton: ImageButton =
            itemView.findViewById(R.id.editButton)

        val deleteButton: ImageButton =
            itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AssignmentViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.item_placed_assignment,
                parent,
                false
            )

        return AssignmentViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(
        holder: AssignmentViewHolder,
        position: Int
    ) {

        val assignment = assignments[position]

        holder.titleText.text = assignment.title

        holder.descriptionInput.text = assignment.description

        holder.deadlineText.text = "Due: ${assignment.deadline}"

        holder.statusText.text = assignment.status

        holder.editButton.setOnClickListener {
            onEdit(assignment)
        }

        holder.deleteButton.setOnClickListener {
            onDelete(assignment)
        }
        holder.itemView.setOnLongClickListener {
            doneListener.onStatusLongClick(assignment)
            true
        }
    }

    override fun getItemCount(): Int {
        return assignments.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateAssignments(newAssignments: List<AssignmentModel>) {
        assignments = newAssignments
        notifyDataSetChanged()
    }
}

