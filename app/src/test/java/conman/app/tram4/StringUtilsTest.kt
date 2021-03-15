package conman.app.tram4

import androidx.test.platform.app.InstrumentationRegistry
import com.example.tram4.R
import conman.app.tram4.utils.StringUtils

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.robolectric.RobolectricTestRunner

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(RobolectricTestRunner::class)
class StringUtilsTest {

    @Test
    fun parseStringArraysAsMap() {

        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val map = StringUtils.parseStringArrayAsMap(appContext,
            R.array.stops_map
        )

        assertTrue(map["alepa"].equals("Laajalahden aukio"))
        assertTrue(map["portti"].equals("Munkkiniemen puistotie"))

    }
}
