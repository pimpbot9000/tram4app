package com.example.tram4.main_activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tram4.R
import com.example.tram4.settings_activity.SettingsActivity
import com.example.tram4.utils.StringUtils

import kotlinx.android.synthetic.main.activity_main.*
import java.util.prefs.Preferences

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var stopTextView: TextView
    private lateinit var timetableModelRef: TimetableViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        recyclerView = findViewById(R.id.recycler_view)
        stopTextView = findViewById(R.id.stop_text_view)

        val timetableModel: TimetableViewModel by viewModels()
        timetableModelRef = timetableModel



        timetableModel.getTimeTable().observe(this, Observer<Timetable> { timetable ->

            if (timetable.departures.isEmpty()) {
                showErrorToast(timetable.message)
            } else if (recyclerView.adapter == null) {
                recyclerView.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = TimeTableAdapter(timetable.departures)
                }
            } else {
                (recyclerView.adapter as TimeTableAdapter).update(timetable.departures)
            }

        })
    }

    override fun onStart() {
        super.onStart()
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val stop = prefs.getString("stop", resources.getString(R.string.default_stop))
        val map = StringUtils.parseStringArrayAsMap(this, R.array.stops_map)

        stopTextView.text = map.get(stop)
        timetableModelRef.setStop(stop!!)
        timetableModelRef.refreshTimeTable()
        timetableModelRef.resumeUpdate()

    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
        timetableModelRef.pauseUpdate()

    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showErrorToast(message: String) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
        }
    }

    companion object{
        private const val TAG = "MainActivity"
    }
}
