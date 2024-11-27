package com.example.tarea04

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.ComponentActivity
import java.text.SimpleDateFormat
import java.util.Locale

class EditTaskActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_task_layout)

        val taskName = intent.getStringExtra("taskName")
        val taskSubject = intent.getStringExtra("taskSubject")
        val taskDate = intent.getStringExtra("taskDate")
        val taskTime = intent.getStringExtra("taskTime")
        val taskDescription = intent.getStringExtra("taskDescription")
        val taskPriority = intent.getBooleanExtra("taskPriority", false)
        val position = intent.getIntExtra("position", -1)

        val name = findViewById<EditText>(R.id.task_name)
        val subject = findViewById<Spinner>(R.id.spinner_task_subject)
        val date = findViewById<EditText>(R.id.edit_due_date)
        val time = findViewById<EditText>(R.id.time)
        val description = findViewById<EditText>(R.id.task_desc)
        val priority = findViewById<CheckBox>(R.id.checkBox)

        val subjects = arrayOf("Dispositivos Moviles", "Compiladores", "Complejidad Computacional", "Redes de Computadoras")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, subjects)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        subject.adapter = adapter

        name.setText(taskName)
        date.setText(taskDate)
        time.setText(taskTime)
        description.setText(taskDescription)
        priority.isChecked = taskPriority

        subject.setSelection(subjects.indexOf(taskSubject))

        val buttonConfirm = findViewById<Button>(R.id.buttonConfirm)
        buttonConfirm.setOnClickListener {
            val dateString = date.text.toString()
            val timeString = time.text.toString()

            // Regular expressions for strict format validation
            val dateRegex = """^\d{2}/\d{2}/\d{4}$""".toRegex()
            val timeRegex = """^\d{2}:\d{2}$""".toRegex()

            if (!dateRegex.matches(dateString)) {
                Toast.makeText(this, "Formato de fecha incorrecto (dd/MM/yyyy)", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            } else if (!timeRegex.matches(timeString)) {
                Toast.makeText(this, "Formato de hora incorrecto (HH:mm)", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            } else {
                val data = Intent(this, PendientesActivity::class.java)
                data.putExtra("taskName", name.text.toString())
                data.putExtra("taskSubject", subject.selectedItem.toString())
                data.putExtra("taskDate", dateString)
                data.putExtra("taskTime", timeString)
                data.putExtra("taskDescription", description.text.toString())
                data.putExtra("taskPriority", priority.isChecked)
                data.putExtra("position", position)
                data.putExtra("action", "edit")

                startActivity(data)
            }
        }
    }
}