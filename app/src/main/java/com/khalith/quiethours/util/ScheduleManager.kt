package com.khalith.quiethours.util

import java.util.Calendar

object ScheduleManager {
    fun isCurrentlyInSchedule(
        startHour: Int,
        startMinute: Int,
        endHour: Int,
        endMinute: Int,
        days: Set<String>
    ): Boolean {
        val calendar = Calendar.getInstance()
        val currentDay = calendar.get(Calendar.DAY_OF_WEEK).toString() // Sunday=1, Monday=2, ...

        if (!days.contains(currentDay)) return false

        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        val currentMinute = calendar.get(Calendar.MINUTE)

        val currentTime = currentHour * 60 + currentMinute
        val startTime = startHour * 60 + startMinute
        val endTime = endHour * 60 + endMinute

        return if (startTime <= endTime) {
            currentTime in startTime..endTime
        } else {
            // Overnights (e.g., 22:00 to 06:00)
            currentTime >= startTime || currentTime <= endTime
        }
    }
}
