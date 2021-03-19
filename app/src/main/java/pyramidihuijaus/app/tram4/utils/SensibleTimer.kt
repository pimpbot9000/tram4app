package pyramidihuijaus.app.tram4.utils

import java.util.*

class SensibleTimer(private val delay: Long, private val callback: (() -> Unit)){
    private var isRunning = false
    private var timer: Timer? = null

    fun startTimer(){
        if(!isRunning){
            timer = Timer()
            timer?.scheduleAtFixedRate(object: TimerTask() {
                override fun run() {
                    callback()
                }
            }, delay, delay)
            isRunning = true
        }
    }

    fun stopTimer(){
        if(isRunning){
            timer?.cancel()
            timer?.purge()
            isRunning = false
        }
    }
}