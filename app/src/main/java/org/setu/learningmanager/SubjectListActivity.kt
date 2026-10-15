package org.setu.learningmanager

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
        setContentView(R.layout.activity_learn_list)

        recyclerView = findViewById(R.id.subjectRecyclerView)

        adapter = SubjectAdapter(
            subjects = AppData.store.findAllSubject(),

            onEdit = { subject ->
                editSubject(subject)
            },

            onDelete = { subject ->
                deleteSubject(subject)
            }
        )

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val returnButton = findViewById<Button>(R.id.returnButton)

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
}