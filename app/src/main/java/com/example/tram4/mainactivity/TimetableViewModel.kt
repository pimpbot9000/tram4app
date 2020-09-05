package com.example.tram4.mainactivity

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tram4.api.TramService
import com.example.tram4.utils.SensibleTimer
import io.reactivex.disposables.CompositeDisposable


class TimetableViewModel : ViewModel() {

    private val tramService: TramService = TramService()
    private val compositeDisposable = CompositeDisposable()
    private val timer = SensibleTimer(5000L){
        refreshTimeTable()
    }

    private val timetable: MutableLiveData<Timetable> by lazy {
        MutableLiveData<Timetable>()
    }

    fun getTimeTable(): LiveData<Timetable> {
        return timetable
    }

    fun refreshTimeTable() {
        Log.d("JEEJEE", "data updated")
        compositeDisposable.clear()
        val d = tramService.getService().getTimeTable("portti").subscribe({ result ->
            val newTimeTable = Timetable(result, "OK")
            timetable.postValue(newTimeTable)
        }, { throwable ->
            timetable.postValue(
                Timetable(
                    listOf(),
                    throwable.message ?: "unknown error"
                )
            )
        }

        )
        compositeDisposable.add(d)
    }

    fun resumeUpdate() = timer.startTimer()

    fun pauseUpdate() = timer.stopTimer()

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}

