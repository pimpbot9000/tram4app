package pyramidihuijaus.app.tram4.main_activity

import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.LinearInterpolator


object AnimationUtils {

    fun getAnimation(): Animation {
        val animation: Animation = AlphaAnimation(1.0f, 0.0f) // Change alpha from fully visible to invisible
        animation.duration = 1000 // duration - half a second
        animation.interpolator = LinearInterpolator() // do not alter animation rate
        animation.repeatCount = Animation.INFINITE // Repeat animation infinitely
        animation.repeatMode = Animation.REVERSE
        return animation
    }
}