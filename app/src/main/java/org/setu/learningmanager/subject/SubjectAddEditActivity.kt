package org.setu.learningmanager.subject

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.setu.learningmanager.AppData
import org.setu.learningmanager.R
import org.setu.learningmanager.models.SubjectModel

class SubjectAddEditActivity : AppCompatActivity() {

    private lateinit var subjectNameInput: EditText
    private var editingId: Long? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.subject_add_edit)

        subjectNameInput = findViewById(R.id.nameInput)

        val formTitle = findViewById<TextView>(R.id.formTitle)
        val saveButton = findViewById<Button>(R.id.saveButton)
        val cancelButton = findViewById<Button>(R.id.cancelButton)

        editingId = intent.getLongExtra("id", -1L)
            .takeIf { it != -1L }

        editingId?.let { id ->
            formTitle.text = "Edit Subject"
            loadExistingSubject(id)
        }

        saveButton.setOnClickListener {
            saveSubject()
        }

        cancelButton.setOnClickListener {
            finish()
        }
    }

    private fun loadExistingSubject(id: Long) {
        val subject = AppData.store.findOneSubject(id)

        if (subject == null) {
            Toast.makeText(
                this,
                "Subject not found",
                Toast.LENGTH_SHORT
            ).show()

            finish()
            return
        }

        subjectNameInput.setText(subject.name)
    }

    private fun saveSubject() {
        val name = subjectNameInput.text.toString().trim()

        if (name.isEmpty()) {
            subjectNameInput.error = "Subject name is required"
            return
        }

        val id = editingId

        if (id == null) {
            val subject = SubjectModel(name = name)
            AppData.store.createSubject(subject)

            Toast.makeText(
                this,
                "Subject created",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            val subject = SubjectModel(
                id = id,
                name = name
            )

            val updated = AppData.store.updateSubject(subject)

            if (!updated) {
                Toast.makeText(
                    this,
                    "Subject not found",
                    Toast.LENGTH_SHORT
                ).show()
                return
            }

            Toast.makeText(
                this,
                "Subject updated",
                Toast.LENGTH_SHORT
            ).show()
        }

        finish()
    }
}