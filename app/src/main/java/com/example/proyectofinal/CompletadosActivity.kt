package com.example.proyectofinal

import android.os.Bundle
import android.view.MenuItem
import android.widget.ListView
import androidx.activity.ComponentActivity

class CompletadosActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.task_list_comp_layout)

        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setHomeButtonEnabled(true)

        crearMenu()
    }

    fun crearMenu() {
        val taskBDD : TaskBDD = TaskBDD(this)
        taskBDD.openForRead()
        var tasks : ArrayList<Task> = taskBDD.getCompletedTasks()
        taskBDD.close()
        val adapter: TaskAdapterComp = TaskAdapterComp(this, R.layout.task_in_list_comp, tasks)
        val listView: ListView = findViewById(R.id.list)
        listView.adapter = adapter

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
