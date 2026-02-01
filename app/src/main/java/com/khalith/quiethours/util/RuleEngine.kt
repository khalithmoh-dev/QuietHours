package com.khalith.quiethours.util

import android.content.Context
import android.provider.ContactsContract
import com.khalith.quiethours.data.GhostDatabase
import kotlinx.coroutines.flow.first

object RuleEngine {
    suspend fun shouldGhost(context: Context, incomingNumber: String): Boolean {
        val manager = BusyModeManager(context)
        if (!manager.appEnabled) return false

        val db = GhostDatabase.getDatabase(context)
        val isGhostListed = db.ghostNumberDao().getByNumber(incomingNumber) != null
        val isInSchedule = ScheduleManager.isCurrentlyInSchedule(
            manager.scheduleStartHour,
            manager.scheduleStartMinute,
            manager.scheduleEndHour,
            manager.scheduleEndMinute,
            manager.scheduleDays
        )

        return when (manager.currentMode) {
            BusyModeManager.Mode.OFF -> false
            BusyModeManager.Mode.BUSY -> true
            BusyModeManager.Mode.WORK -> {
                // Reject only numbers in Ghost List during scheduled time
                isGhostListed && isInSchedule
            }
            BusyModeManager.Mode.SLEEP -> {
                // Reject all calls EXCEPT favorite/starred contacts during scheduled time
                if (isInSchedule) {
                    !isFavorite(context, incomingNumber)
                } else {
                    false
                }
            }
            BusyModeManager.Mode.CUSTOM -> {
                // RuleEngine for decision (customizable, here we can use ghost list during schedule)
                isGhostListed || (isInSchedule && isGhostListed)
            }
        }
    }

    private fun isFavorite(context: Context, number: String): Boolean {
        val uri = ContactsContract.Contacts.CONTENT_URI
        val projection = arrayOf(ContactsContract.Contacts.STARRED)
        val selection = "${ContactsContract.CommonDataKinds.Phone.NUMBER} = ?"
        val selectionArgs = arrayOf(number)

        // Simpler check: point to Phone.CONTENT_URI for number matching
        val phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        val cursor = context.contentResolver.query(
            phoneUri,
            arrayOf(ContactsContract.CommonDataKinds.Phone.STARRED),
            "${ContactsContract.CommonDataKinds.Phone.NUMBER} = ?",
            arrayOf(number),
            null
        )

        var isStarred = false
        cursor?.use {
            if (it.moveToFirst()) {
                isStarred = it.getInt(0) == 1
            }
        }
        return isStarred
    }
}
