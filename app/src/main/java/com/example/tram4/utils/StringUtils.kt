package com.example.tram4.utils

import android.content.Context

object StringUtils{
    fun parseStringArrayAsMap(ctx: Context, stringArrayResourceId: Int): Map<String, String> {
        val stringArray = ctx.resources.getStringArray(stringArrayResourceId)
        /*val outputArray = SparseArray<String>(stringArray.size)
        for (entry in stringArray) {
            val splitResult = entry.split("\\|".toRegex(), 2).toTypedArray()
            outputArray.put(splitResult[0], splitResult[1])
        }*/
        return stringArray.map{
            val split = it.split("\\|".toRegex(), 2).toTypedArray()
            split[0] to split[1]
        }.toMap()
    }
}