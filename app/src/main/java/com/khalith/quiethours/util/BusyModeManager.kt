package com.khalith.quiethours.util

import android.content.Context
import android.content.SharedPreferences

class BusyModeManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("quiet_hours_prefs", Context.MODE_PRIVATE)

    enum class Mode {
        OFF, BUSY, WORK, SLEEP, CUSTOM
    }

    var appEnabled: Boolean
        get() = prefs.getBoolean("app_enabled", true)
        set(value) = prefs.edit().putBoolean("app_enabled", value).apply()

    var currentMode: Mode
        get() = Mode.valueOf(prefs.getString("current_mode", Mode.OFF.name) ?: Mode.OFF.name)
        set(value) = prefs.edit().putString("current_mode", value.name).apply()

    // Schedule: Start Hour, Start Minute, End Hour, End Minute, Days (bitmask or comma-separated)
    var scheduleStartHour: Int
        get() = prefs.getInt("schedule_start_hour", 9)
        set(value) = prefs.edit().putInt("schedule_start_hour", value).apply()

    var scheduleStartMinute: Int
        get() = prefs.getInt("schedule_start_minute", 0)
        set(value) = prefs.edit().putInt("schedule_start_minute", value).apply()

    var scheduleEndHour: Int
        get() = prefs.getInt("schedule_end_hour", 17)
        set(value) = prefs.edit().putInt("schedule_end_hour", value).apply()

    var scheduleEndMinute: Int
        get() = prefs.getInt("schedule_end_minute", 0)
        set(value) = prefs.edit().putInt("schedule_end_minute", value).apply()

    var scheduleDays: Set<String>
        get() = prefs.getStringSet("schedule_days", setOf("1", "2", "3", "4", "5")) ?: emptySet()
        set(value) = prefs.edit().putStringSet("schedule_days", value).apply()
}
