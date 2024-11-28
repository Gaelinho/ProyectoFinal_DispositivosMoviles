package com.example.proyectofinal

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
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout

class PendientesActivity : ComponentActivity() {

    private lateinit var mMenuSections: Array<String>
    private lateinit var mDrawerLayout: DrawerLayout
    private lateinit var mDrawerList: ListView
    private lateinit var mDrawerToggle: ActionBarDrawerToggle

    private val editTaskLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val editedTask: Task = result.data?.getSerializableExtra("editedTask") as Task
            val position = result.data?.getIntExtra("position", -1) ?: -1

            if (position != -1) {
                (findViewById<ListView>(R.id.list).adapter as TaskAdapter).notifyDataSetChanged()
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
        val taskBDD : TaskBDD = TaskBDD(this)
        taskBDD.openForRead()
        val adapter: TaskAdapter = TaskAdapter(this, R.layout.task_in_list, taskBDD.getAllTasks())
        taskBDD.close()
        val listView: ListView = findViewById(R.id.list)
        listView.adapter = adapter
    }
}
