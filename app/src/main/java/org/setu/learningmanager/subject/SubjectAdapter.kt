package org.setu.learningmanager.subject

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.setu.learningmanager.R
import org.setu.learningmanager.models.SubjectModel

class SubjectAdapter(
    private var subjects: List<SubjectModel>,
    private val onEdit: (SubjectModel) -> Unit,
    private val onDelete: (SubjectModel) -> Unit,
    private val onAssignments: (SubjectModel) -> Unit
) : RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder>() {

    class SubjectViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val titleText: TextView =
            itemView.findViewById(R.id.titleText)

        val editButton: ImageButton =
            itemView.findViewById(R.id.editButton)

        val deleteButton: ImageButton =
            itemView.findViewById(R.id.deleteButton)

        val assignmentButton: ImageButton =
            itemView.findViewById(R.id.assignmentButton)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SubjectViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.item_placed_subject,
                parent,
                false
            )

        return SubjectViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(
        holder: SubjectViewHolder,
        position: Int
    ) {

        val subject = subjects[position]

        holder.titleText.text = subject.name

        holder.editButton.setOnClickListener {
            onEdit(subject)
        }

        holder.deleteButton.setOnClickListener {
            onDelete(subject)
        }
        holder.assignmentButton.setOnClickListener {
            onAssignments(subject)
        }
    }

    override fun getItemCount(): Int {
        return subjects.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateSubjects(newSubjects: List<SubjectModel>) {
        subjects = newSubjects
        notifyDataSetChanged()
    }
}

