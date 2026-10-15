package org.setu.learningmanager

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.setu.learningmanager.models.SubjectModel

class SubjectAdapter(
    private var subjects: List<SubjectModel>,
    private val onEdit: (SubjectModel) -> Unit,
    private val onDelete: (SubjectModel) -> Unit
) : RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder>() {

    class SubjectViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val titleText: TextView =
            itemView.findViewById(R.id.titleText)

        val editButton: Button =
            itemView.findViewById(R.id.editButton)

        val deleteButton: Button =
            itemView.findViewById(R.id.deleteButton)
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

        holder.titleText.text =
            "${subject.id}: ${subject.name}"

        holder.editButton.setOnClickListener {
            onEdit(subject)
        }

        holder.deleteButton.setOnClickListener {
            onDelete(subject)
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

