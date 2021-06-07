package com.wuba.wastesorting.utils

import androidx.compose.ui.graphics.Color


object DataUtils {
    val rgbRegex = Regex("RGB (\\d+),(\\d+),(\\d+)")
    val rangeRegex = Regex("RNG (\\d+)")
    val userRegex = Regex("USR (\\d)")
    val percentageRegex = Regex("PCT (\\d) (\\d+)")
    const val MAX_RANGE = 1000

    fun rgb2Color(rgb: IntArray): Color {
        return Color(rgb[0], rgb[1], rgb[2])
    }

    fun matchRgb(msg: String): Pair<Boolean, IntArray?> {
        var rgb: IntArray? = null
        val match = rgbRegex.matchEntire(msg)
        if (match != null) {
            rgb = intArrayOf(
                match.groupValues?.get(1).toInt(),
                match.groupValues?.get(2).toInt(),
                match.groupValues?.get(3).toInt()
            )
            return Pair(true, rgb)
        }
        return Pair(false, rgb)
    }

    fun matchRange(msg: String): Pair<Boolean, Int> {
        var range: Int = -1
        val match = rangeRegex.matchEntire(msg)
        if (match != null) {
            range = match.groupValues?.get(1).toInt()
            return Pair(true, range)
        }
        return Pair(false, range)
    }

    fun matchUserType(msg: String): Pair<Boolean, Int> {
        var userType: Int = 0
        val match = userRegex.matchEntire(msg)
        if (match != null) {
            userType = match.groupValues?.get(1).toInt()
            return Pair(true, userType)
        }
        return Pair(false, userType)
    }

    fun matchPercentage(msg: String): Triple<Boolean, Int, Int> {
        var isMatch = false
        var number = -1
        var percentage = 0
        val match = percentageRegex.matchEntire(msg)
        if (match != null) {
            number = match.groupValues?.get(1).toInt()
            percentage = match.groupValues?.get(2).toInt()
            isMatch = true
        }
        return Triple(isMatch, number, percentage)
    }

}
