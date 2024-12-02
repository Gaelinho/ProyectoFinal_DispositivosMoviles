package com.example.proyectofinal

import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.ComponentActivity

class EditTaskActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_task_layout)

        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setHomeButtonEnabled(true)

        val taskBDD : TaskBDD = TaskBDD(this)
        taskBDD.openForRead()
        val id : Int = intent.getIntExtra("id", -1)
        val task : Task = taskBDD.getTask(id)
        taskBDD.close()

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

        name.setText(task.nombre)
        date.setText(task.fecha)
        time.setText(task.hora)
        description.setText(task.descripcion)
        priority.isChecked = task.isPrioridad

        subject.setSelection(subjects.indexOf(task.materia))

        val buttonConfirm = findViewById<Button>(R.id.buttonConfirm)
        val buttonDelete = findViewById<Button>(R.id.buttonDelete)

        buttonConfirm.setOnClickListener {
            val dateString = date.text.toString()
            val timeString = time.text.toString()

            val dateRegex = """^\d{2}/\d{2}/\d{4}$""".toRegex()
            val timeRegex = """^\d{2}:\d{2}$""".toRegex()

            if (!dateRegex.matches(dateString)) {
                Toast.makeText(this, "Formato de fecha incorrecto (dd/MM/yyyy)", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            } else if (!timeRegex.matches(timeString)) {
                Toast.makeText(this, "Formato de hora incorrecto (HH:mm)", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            } else {
                val editedTask : Task = Task(name.text.toString(), subject.selectedItem.toString(), description.text.toString(), dateString, timeString, priority.isChecked)
                var taskBDD : TaskBDD = TaskBDD(this)
                taskBDD.openForWrite()
                taskBDD.updateTask(id, editedTask)
                taskBDD.close()

                finish()
            }
        }

        buttonDelete.setOnClickListener {
            var taskBDD : TaskBDD = TaskBDD(this)
            taskBDD.openForWrite()
            taskBDD.deleteTask(id)
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