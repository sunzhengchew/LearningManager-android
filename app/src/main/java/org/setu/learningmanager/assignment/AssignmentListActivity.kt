package org.setu.learningmanager.assignment

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.setu.learningmanager.subject.SubjectAddEditActivity
import org.setu.learningmanager.AppData
import org.setu.learningmanager.R
import org.setu.learningmanager.models.AssignmentModel

class AssignmentListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AssignmentAdapter

    private var subjectId: Long = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_assignment_list)
        supportActionBar?.apply {
            title = "Assignments"
            setDisplayHomeAsUpEnabled(true)
        }
        subjectId = intent.getLongExtra("subjectId", -1L)

        if (AppData.store.findOneSubject(subjectId) == null) {
            Toast.makeText(
                this,
                "Subject not found",
                Toast.LENGTH_SHORT
            ).show()

            finish()
            return
        }

        recyclerView = findViewById(R.id.assignmentRecyclerView)

        adapter = AssignmentAdapter(
            assignments = AppData.store.findAssignmentsBySubject(subjectId),

            onEdit = { assignment ->
                editAssignment(assignment)
            },

            onDelete = { assignment ->
                confirmDelete(assignment)
            },
            doneListener = object : DoneListener {
                override fun onStatusLongClick(assignment: AssignmentModel) {
                    confirmCompleted(assignment)
                }
            }
        )

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        findViewById<ImageButton>(R.id.addAssignmentButton)
            .setOnClickListener {
                val intent = Intent(
                    this,
                    AssignmentAddEditActivity::class.java
                )

                intent.putExtra("subjectId", subjectId)
                startActivity(intent)
            }

        findViewById<Button>(R.id.assignmentReturnButton)
            .setOnClickListener {
                finish()
            }
    }

    override fun onResume() {
        super.onResume()

        if (::adapter.isInitialized) {
            refreshAssignments()
        }
    }

    private fun refreshAssignments() {
        adapter.updateAssignments(
            AppData.store.findAssignmentsBySubject(subjectId)
        )
    }

    private fun editAssignment(assignment: AssignmentModel) {
        val intent = Intent(
            this,
            AssignmentAddEditActivity::class.java
        )

        intent.putExtra("subjectId", subjectId)
        intent.putExtra("id", assignment.id)

        startActivity(intent)
    }

    private fun deleteAssignment(assignment: AssignmentModel) {
        AppData.store.deleteAssignment(assignment.id)
        refreshAssignments()
    }

    private fun confirmDelete(assignment: AssignmentModel) {
        AlertDialog.Builder(this)
            .setTitle("Delete assignment")
            .setMessage("Delete \"${assignment.title}\"?")
            .setPositiveButton("Delete") { _, _ ->
                deleteAssignment(assignment)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun confirmCompleted(assignment: AssignmentModel) {
        AlertDialog.Builder(this)
            .setTitle("Complete assignment")
            .setMessage("Mark \"${assignment.title}\" as completed?")
            .setPositiveButton("Confirm") { _, _ ->
                val updatedAssignment = assignment.copy(
                    status = "Is completed"
                )

                if (AppData.store.updateAssignment(updatedAssignment)) {
                    refreshAssignments()
                } else {
                    Toast.makeText(
                        this,
                        "Assignment not found",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}