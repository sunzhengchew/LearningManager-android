package org.setu.learningmanager.learn_list

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.setu.learningmanager.R
import org.setu.learningmanager.subject.SubjectAddEditActivity
import org.setu.learningmanager.subject.SubjectListActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        supportActionBar?.apply {
            title = "Assignment"
            setDisplayHomeAsUpEnabled(true)
        }

        val root = findViewById<View>(R.id.main)

        val leftPadding = root.paddingLeft
        val topPadding = root.paddingTop
        val rightPadding = root.paddingRight
        val bottomPadding = root.paddingBottom

        ViewCompat.setOnApplyWindowInsetsListener(root) { view, insets ->
            val systemBars =
                insets.getInsets(WindowInsetsCompat.Type.systemBars())

            view.setPadding(
                leftPadding + systemBars.left,
                topPadding + systemBars.top,
                rightPadding + systemBars.right,
                bottomPadding + systemBars.bottom
            )

            insets
        }

        val viewSubjectsButton =
            findViewById<Button>(R.id.viewSubjectsButton)

        val addSubjectButton =
            findViewById<Button>(R.id.addSubjectButton)

        viewSubjectsButton.setOnClickListener {
            startActivity(
                Intent(this, SubjectListActivity::class.java)
            )
        }

        addSubjectButton.setOnClickListener {
            startActivity(
                Intent(this, SubjectAddEditActivity::class.java)
            )
        }
    }
}
