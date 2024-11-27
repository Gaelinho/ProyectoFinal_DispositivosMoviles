package com.example.tarea04

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout

class PendientesActivity : ComponentActivity() {

    private val menuTask : ArrayList<Task> = ArrayList<Task>();

    private lateinit var mMenuSections: Array<String>
    private lateinit var mDrawerLayout: DrawerLayout
    private lateinit var mDrawerList: ListView
    private lateinit var mDrawerToggle: ActionBarDrawerToggle

    private val editTaskLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val editedTask: Task = result.data?.getSerializableExtra("editedTask") as Task
            val position = result.data?.getIntExtra("position", -1) ?: -1

            if (position != -1) {
                menuTask[position] = editedTask  // Update the task at the specified position
                (findViewById<ListView>(R.id.list).adapter as TaskAdapter).notifyDataSetChanged()  // Notify the adapter
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.task_list_layout)

        crearMenu()

        mMenuSections = resources.getStringArray(R.array.menu_items)
        mDrawerLayout = (findViewById(R.id.drawer_layout) as DrawerLayout)
        mDrawerList = (findViewById(R.id.left_drawer) as ListView)

        mDrawerList.adapter = ArrayAdapter(
            this,
            R.layout.left_drawer,
            mMenuSections
        )

        mDrawerToggle = object : ActionBarDrawerToggle(
            this,
            mDrawerLayout,
            R.string.DrawerOpened,
            R.string.DrawerClosed
        ) {
            override fun onDrawerOpened(drawerView: View) {
                Log.d("PendientesActivity", "Drawer Opened")
            }
            override fun onDrawerClosed(drawerView: View) {
                Log.d("PendientesActivity", "Drawer Closed")
            }
        }

        mDrawerList.onItemClickListener = DrawerItemClickListener()
        mDrawerLayout.addDrawerListener(mDrawerToggle)

        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar!!.setHomeButtonEnabled(true)
    }

    private class DrawerItemClickListener : OnItemClickListener {
        override fun onItemClick(
            parent: AdapterView<*>,
            view: View, position: Int, id: Long
        ) {
            if (parent.adapter.getItem(position) == "Agregar Tarea"){
                val intent = Intent(parent.context, AddTaskActivity::class.java)
                parent.context.startActivity(intent)
            } else {
                Log.d(
                    "PendientesActivity",
                    (parent.adapter.getItem(position) as String)
                )
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.actionbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true
        }
        val id = item.itemId
        when (id) {
            R.id.ord_fecha -> {
                Log.d("PendientesActivity", "Ordenar por Fecha")
                return true
            }
            R.id.ord_materia -> {
                Log.d("PendientesActivity", "Ordenar por Materia")
                return true
            }
            R.id.prior_menu -> {
                Log.d("PendientesActivity", "Mostrar Prioritarios")
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        mDrawerToggle.syncState()
    }

    fun crearMenu() {
        menuTask.add(
            Task(
                "Tarea 04",
                "Dispositivos Moviles",
                "Intents",
                "19/11/2024",
                "23:59",
                false
            )
        )
        menuTask.add(
            Task(
                "Examen 03",
                "Dispositivos Moviles",
                "Bases de Datos",
                "22/11/2024",
                "07:00",
                true
            )
        )
        menuTask.add(
            Task(
                "Practica 07",
                "Compiladores",
                "Verificacion de tipos",
                "22/11/2024",
                "23:59",
                false
            )
        )

        var action : String? = intent.getStringExtra("action")

        if (action == "edit") {
            var position = intent.getIntExtra("position", -1)
            Toast.makeText(this, "Cambios guardados", Toast.LENGTH_SHORT).show()
            if (position != -1) {
                menuTask.removeAt(position)
                var nuevo : Task = Task(intent.getStringExtra("taskName").toString(),
                    intent.getStringExtra("taskSubject").toString(),
                    intent.getStringExtra("taskDescription").toString(),
                    intent.getStringExtra("taskDate").toString(),
                    intent.getStringExtra("taskTime").toString(),
                    intent.getBooleanExtra("taskPriority", false))
                menuTask.add(nuevo)
            }
        } else if (action == "add") {
            var nuevo : Task = Task(intent.getStringExtra("taskName").toString(),
                intent.getStringExtra("taskSubject").toString(),
                intent.getStringExtra("taskDescription").toString(),
                intent.getStringExtra("taskDate").toString(),
                intent.getStringExtra("taskTime").toString(),
                intent.getBooleanExtra("taskPriority", false))
            menuTask.add(nuevo)
        }


        val adapter: TaskAdapter = TaskAdapter(this, R.layout.task_in_list, menuTask)
        val listView: ListView = findViewById(R.id.list)
        listView.adapter = adapter
    }
}
