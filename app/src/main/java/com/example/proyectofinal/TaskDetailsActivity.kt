package com.example.proyectofinal

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.CheckBox
import android.widget.TextView
import androidx.activity.ComponentActivity

class TaskDetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.task_layout)

        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setHomeButtonEnabled(true)

        val name = findViewById<TextView>(R.id.name)
        val subject = findViewById<TextView>(R.id.subject)
        val date = findViewById<TextView>(R.id.date)
        val time = findViewById<TextView>(R.id.time)
        val description = findViewById<TextView>(R.id.description)
        val priority = findViewById<CheckBox>(R.id.checkBox)

        val taskBDD : TaskBDD = TaskBDD(this)
        taskBDD.openForRead()
        val id : Int = intent.getIntExtra("id", -1)
        val task : Task = taskBDD.getTask(id)
        Log.d("TaskDetailsActivity", task.isPrioridad.toString())
        taskBDD.close()

        name.setText(task.nombre)
        subject.setText(task.materia)
        date.setText(task.formattedFecha)
        time.setText(task.formattedHora)
        description.setText(task.descripcion)
        priority.isChecked = task.isPrioridad

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}