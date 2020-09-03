package com.example.tram4

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tram4.api.TramService
import com.example.tram4.api.models.DepartureInfo
import java.util.*
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.disposables.CompositeDisposable


class TimetableViewModel : ViewModel() {

    private val tramService: TramService = TramService()
    private var updating = false
    private var timer: Timer? = null
    private val compositeDisposable = CompositeDisposable()

    private val timetable: MutableLiveData<Timetable> by lazy {
        MutableLiveData<Timetable>().also {

        }
    }

    fun getTimeTable(): LiveData<Timetable> {
        return timetable
    }

    fun refreshTimeTable() {
        Log.d("JEEJEE", "data updated")
        compositeDisposable.clear()
        val d = tramService.getService().getTimeTable().subscribe({ result ->
            val newTimeTable = Timetable(result, "OK")
            timetable.postValue(newTimeTable)
        }, { throwable ->
            timetable.postValue(Timetable(listOf(), throwable.message ?: "unknown error"))
        }

        )
        compositeDisposable.add(d)
    }

    private fun startTimer() {

        if (!updating) {
            Log.d("JEEJEE", "starting timer")
            timer = Timer()
            timer?.scheduleAtFixedRate(object : TimerTask() {
                override fun run() {
                    refreshTimeTable()
                }
            }, 5 * 1000, 5 * 1000)
            updating = true
        }
    }

    fun resumeUpdate() {
        startTimer()
    }

    fun pauseUpdate() {
        Log.d("JEEJEE", "stop updating")
        if (updating) {
            timer?.cancel()
            timer?.purge()
            updating = false
        }
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}

