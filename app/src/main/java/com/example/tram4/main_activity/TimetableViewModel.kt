package com.example.tram4.main_activity


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tram4.api.TramService
import com.example.tram4.utils.SensibleTimer
import io.reactivex.disposables.CompositeDisposable
import okhttp3.HttpUrl


class TimetableViewModel : ViewModel() {

    private val tramService: TramService = TramService(HttpUrl.parse(TramService.BASE_URL)!!)
    private val compositeDisposable = CompositeDisposable()
    private var stop: String? = null
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

        if(stop == null){ //should never happen
            timetable.postValue(
                Timetable(
                    listOf(),
                    "Pysäkkiä ei asetettu"
                )
            )
            return
        }

        compositeDisposable.clear()
        val d = tramService.getService().getTimeTable(stop!!).subscribe({ result ->
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

    fun setStop(stop: String){
        this.stop = stop
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

}

