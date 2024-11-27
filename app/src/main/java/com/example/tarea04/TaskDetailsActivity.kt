package com.example.tarea04

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.activity.ComponentActivity

class TaskDetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.task_layout)

        val name = findViewById<TextView>(R.id.name)
        val subject = findViewById<TextView>(R.id.subject)
        val date = findViewById<TextView>(R.id.date)
        val time = findViewById<TextView>(R.id.time)
        val description = findViewById<TextView>(R.id.description)
        val priority = findViewById<CheckBox>(R.id.checkBox)

        val taskName = intent.getStringExtra("taskName")
        val taskSubject = intent.getStringExtra("taskSubject")
        val taskDate = intent.getStringExtra("taskDate")
        val taskTime = intent.getStringExtra("taskTime")
        val taskDescription = intent.getStringExtra("taskDescription")
        val taskPriority = intent.getBooleanExtra("taskPriority", false)

        name.text = taskName
        subject.text = taskSubject
        date.text = taskDate
        time.text = taskTime
        description.text = taskDescription
        priority.isChecked = taskPriority

        val buttonBack = findViewById<Button>(R.id.buttonBack)
        buttonBack.setOnClickListener {
            finish()
        }
    }
}