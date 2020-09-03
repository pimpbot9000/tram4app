package com.example.tram4

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tram4.api.models.DepartureInfo

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var timetableModelRef: TimetableViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        recyclerView = findViewById(R.id.recycler_view)

        val timetableModel: TimetableViewModel by viewModels()
        timetableModelRef = timetableModel

        timetableModel.getTimeTable().observe(this, Observer<List<DepartureInfo>> { departures ->

            if (recyclerView.adapter == null) {
                recyclerView.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = TimeTableAdapter(departures)
                }
            } else {
                (recyclerView.adapter as TimeTableAdapter).update(departures)
            }
        })
    }

    override fun onStart(){
        super.onStart()
        Log.d("JEEJEE", "onStart")
        timetableModelRef.refreshTimeTable()
        timetableModelRef.resumeUpdate()

    }

    override fun onStop() {
        super.onStop()
        Log.d("JEEJEE", "onStop")
        timetableModelRef.pauseUpdate()

    }

    override fun onResume() {
        super.onResume()
        Log.d("JEEJEE", "onResume")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("JEEJEE", "onRestart")
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                Log.d("JEEJEE", "hello")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }



    private fun showErrorToast(throwable: Throwable) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(applicationContext, throwable.message, Toast.LENGTH_LONG).show()
        }
    }
}
