package com.example.proyectofinal

import android.os.Bundle
import android.util.Log
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

        val taskBDD : TaskBDD = TaskBDD(this)
        taskBDD.openForRead()
        val id : Int = intent.getIntExtra("id", -1)
        val task : Task = taskBDD.getTask(id)
        Log.d("TaskDetailsActivity", task.isPrioridad.toString())
        taskBDD.close()

        name.setText(task.nombre)
        subject.setText(task.materia)
        date.setText(task.fecha)
        time.setText(task.hora)
        description.setText(task.descripcion)
        priority.isChecked = task.isPrioridad

        val buttonBack = findViewById<Button>(R.id.buttonBack)
        buttonBack.setOnClickListener {
            finish()
        }
    }
}