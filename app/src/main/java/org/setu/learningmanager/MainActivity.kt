package org.setu.learningmanager

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val root = findViewById<android.view.View>(R.id.main)

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
                Intent(this, AddEditActivity::class.java)
            )
        }
    }
}
