package com.amity.assignment

import Adapter.GridViewAdapter
import SQLiteService
import ToDoListViewModel
import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.widget.GridView
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity

class MainActivity : AppCompatActivity() {

    private lateinit var gridView: GridView
    private val todoListViewModel: ToDoListViewModel by viewModels()
    private val dbHelper = SQLiteService(this)

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // checking internet connection

        val connectivityManager =
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

        if (capabilities != null) {
            // fetch ToDoList
            todoListViewModel.getToDoList()
            todoListViewModel.todoList.observe(this) {
                // save todoList to local storage
                todoListViewModel.saveOfflineValue(it!!, dbHelper)
                // gridview adapter
                gridView = findViewById(R.id.grid_view)
                val gridViewAdapter = GridViewAdapter(it, this)
                gridView.adapter = gridViewAdapter

            }
        } else {
            // read local storage data if device is offline
            todoListViewModel.readOfflineValue(dbHelper)
            gridView = findViewById(R.id.grid_view)
            val gridViewAdapter =
                todoListViewModel.todoList.value?.let { it1 -> GridViewAdapter(it1, this) }
            gridView.adapter = gridViewAdapter

        }
    }
}