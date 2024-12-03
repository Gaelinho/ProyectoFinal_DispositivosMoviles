package com.example.proyectofinal

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Spinner
import android.widget.TimePicker
import android.widget.Toast
import androidx.activity.ComponentActivity
import java.time.LocalDate
import java.time.LocalTime

class AddTaskActivity : ComponentActivity() {

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_task_layout)

        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setHomeButtonEnabled(true)

        val name = findViewById<EditText>(R.id.task_name)
        val subject = findViewById<Spinner>(R.id.spinner_task_subject)
        val date = findViewById<DatePicker>(R.id.edit_due_date)
        val time = findViewById<TimePicker>(R.id.time)
        val description = findViewById<EditText>(R.id.task_desc)
        val priority = findViewById<CheckBox>(R.id.checkBox)

        val subjects = arrayOf("Dispositivos Moviles", "Compiladores", "Complejidad Computacional", "Redes de Computadoras")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, subjects)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        subject.adapter = adapter

        val buttonAdd = findViewById<Button>(R.id.buttonAdd)
        buttonAdd.setOnClickListener {
            val dateString = LocalDate.of(date.year, date.month + 1, date.dayOfMonth)
            val timeString = LocalTime.of(time.hour, time.minute)

            val nuevoTask : Task = Task(name.text.toString(), subject.selectedItem.toString(), description.text.toString(), dateString, timeString, priority.isChecked)
            val taskBDD : TaskBDD = TaskBDD(this)
            taskBDD.openForWrite()
            taskBDD.insertTask(nuevoTask)
            taskBDD.close()

            finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}