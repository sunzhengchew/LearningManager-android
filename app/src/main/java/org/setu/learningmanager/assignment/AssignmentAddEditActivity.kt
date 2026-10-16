package org.setu.learningmanager.assignment

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.setu.learningmanager.AppData
import org.setu.learningmanager.R
import org.setu.learningmanager.models.AssignmentModel
import java.util.Calendar
import java.util.Locale

class AssignmentAddEditActivity : AppCompatActivity() {

    private lateinit var titleInput: EditText
    private lateinit var descriptionInput: EditText
    private lateinit var deadlineInput: EditText
    private lateinit var statusSpinner: Spinner

    private var editingId: Long? = null
    private var subjectId: Long = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.assignment_add_edit)

        supportActionBar?.apply {
            title = "Add Assignment"
            setDisplayHomeAsUpEnabled(true)
        }

        titleInput = findViewById(R.id.titleInput)
        descriptionInput = findViewById(R.id.descriptionInput)
        deadlineInput = findViewById(R.id.deadlineInput)
        statusSpinner = findViewById(R.id.statusSpinner)

        val formTitle = findViewById<TextView>(R.id.formTitle)
        val saveButton = findViewById<Button>(R.id.saveButton)
        val cancelButton = findViewById<Button>(R.id.cancelButton)

        subjectId = intent.getLongExtra("subjectId", -1L)
        editingId = intent.getLongExtra("id", -1L)
            .takeIf { it != -1L }

        if (AppData.store.findOneSubject(subjectId) == null) {
            showMessage("Subject not found")
            finish()
            return
        }

        formTitle.text =
            if (editingId == null) "Add Assignment" else "Edit Assignment"

        val id = editingId
        if (id != null && !loadExistingAssignment(id)) {
            finish()
            return
        }

        deadlineInput.setOnClickListener {
            showDatePicker()
        }

        saveButton.setOnClickListener {
            saveAssignment()
        }

        cancelButton.setOnClickListener {
            finish()
        }
    }

    private fun loadExistingAssignment(id: Long): Boolean {
        val assignment = AppData.store.findOneAssignment(id)

        if (assignment == null || assignment.subjectId != subjectId) {
            showMessage("Assignment not found")
            return false
        }

        titleInput.setText(assignment.title)
        descriptionInput.setText(assignment.description)
        deadlineInput.setText(assignment.deadline)

        val options = resources.getStringArray(
            R.array.assignment_status_options
        )

        val position = options.indexOf(assignment.status)
        statusSpinner.setSelection(
            if (position >= 0) position else 0
        )

        return true
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val parts = deadlineInput.text.toString().split("-")

        if (parts.size == 3) {
            val year = parts[0].toIntOrNull()
            val month = parts[1].toIntOrNull()
            val day = parts[2].toIntOrNull()

            if (year != null && month != null && day != null) {
                calendar.set(year, month - 1, day)
            }
        }

        DatePickerDialog(
            this,
            { _, year, month, day ->
                deadlineInput.setText(
                    String.format(
                        Locale.ROOT,
                        "%04d-%02d-%02d",
                        year,
                        month + 1,
                        day
                    )
                )
                deadlineInput.error = null
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun saveAssignment() {
        val title = titleInput.text.toString().trim()
        val description = descriptionInput.text.toString().trim()
        val deadline = deadlineInput.text.toString().trim()

        if (title.isEmpty()) {
            titleInput.error = "Assignment title is required"
            titleInput.requestFocus()
            return
        }

        if (deadline.isEmpty()) {
            deadlineInput.error = "Select a deadline"
            showMessage("Select a deadline")
            return
        }

        val assignment = AssignmentModel(
            id = editingId ?: 0L,
            subjectId = subjectId,
            title = title,
            description = description,
            deadline = deadline,
            status = statusSpinner.selectedItem.toString()
        )

        if (editingId == null) {
            AppData.store.createAssignment(assignment)
            showMessage("Assignment created")
        } else {
            val updated = AppData.store.updateAssignment(assignment)

            if (!updated) {
                showMessage("Assignment not found")
                return
            }

            showMessage("Assignment updated")
        }

        finish()
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}