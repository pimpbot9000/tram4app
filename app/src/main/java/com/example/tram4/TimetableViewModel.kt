package com.example.tram4

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tram4.api.TramService
import com.example.tram4.api.models.DepartureInfo
import java.util.*

class TimetableViewModel : ViewModel() {

    private val mTramService: TramService = TramService()
    private var updating = false
    private var timer: Timer? = null

    private val timetable : MutableLiveData<List<DepartureInfo>> by lazy {
        MutableLiveData<List<DepartureInfo>>().also {
            startTimer()
        }
    }

    fun getTimeTable(): LiveData<List<DepartureInfo>>{
        return timetable
    }

    fun fetchData(){
            Log.d("JEEJEE", "data updated")
            val d = mTramService.getService().getTimeTable().subscribe({
                    result -> timetable.postValue(result)
            }, {
                    throwable -> Log.d("JEEJEE", throwable.message)
            }

            )

    }

    private fun startTimer(){

        if(!updating) {
            Log.d("JEEJEE", "starting timer")
            timer = Timer()
            timer?.scheduleAtFixedRate(object : TimerTask() {
                override fun run() {
                    fetchData()
                }
            }, 5 * 1000, 5 * 1000)
            updating = true
        }
    }

    fun resumeUpdate(){
        startTimer()
    }

    fun pauseUpdate(){
        Log.d("JEEJEE", "stop updating")
        if(updating) {
            timer?.cancel()
            timer?.purge()
            updating = false
        }
    }
}

