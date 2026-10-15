package org.setu.learningmanager

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.setu.learningmanager.models.SubjectModel

class SubjectListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SubjectAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subject_list)

        recyclerView = findViewById(R.id.examRecyclerView)

        adapter = SubjectAdapter(
            subjects = AppData.store.findAllSubject(),

            onEdit = { subject ->
                editSubject(subject)
            },

            onDelete = { subject ->
                confirmDeleteSubject(subject)
            }
        )

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val returnButton = findViewById<Button>(R.id.examReturnButton)

        returnButton.setOnClickListener {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()

        if (::adapter.isInitialized) {
            adapter.updateSubjects(
                AppData.store.findAllSubject()
            )
        }
    }

    private fun editSubject(subject: SubjectModel) {
        val intent = Intent(this, AddEditActivity::class.java)
        intent.putExtra("id", subject.id)
        startActivity(intent)
    }

    private fun deleteSubject(subject: SubjectModel) {
        AppData.store.deleteSubject(subject.id)

        adapter.updateSubjects(
            AppData.store.findAllSubject()
        )
    }

    private fun confirmDeleteSubject(subject: SubjectModel) {
        AlertDialog.Builder(this)
            .setTitle("Delete Subject")
            .setMessage("Delete this mark?")
            .setPositiveButton("Delete") { _, _ ->
                deleteSubject(subject)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}