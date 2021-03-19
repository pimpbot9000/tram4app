package pyramidihuijaus.app.tram4.utils

import android.content.Context

object StringUtils{
    fun parseStringArrayAsMap(ctx: Context, stringArrayResourceId: Int): Map<String, String> {
        val stringArray = ctx.resources.getStringArray(stringArrayResourceId)

        return stringArray.map{
            val split = it.split("\\|".toRegex(), 2).toTypedArray()
            split[0] to split[1]
        }.toMap()
    }
}