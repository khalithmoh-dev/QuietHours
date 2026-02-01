package com.khalith.quiethours.service

import android.telecom.Call
import android.telecom.CallScreeningService
import com.khalith.quiethours.data.GhostDatabase
import com.khalith.quiethours.data.GhostLog
import com.khalith.quiethours.util.BusyModeManager
import com.khalith.quiethours.util.RuleEngine
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BusyCallScreeningService : CallScreeningService() {

    private val serviceScope = CoroutineScope(Dispatchers.IO)

    override fun onScreenCall(details: Call.Details) {
        val number = details.handle.schemeSpecificPart ?: ""
        
        serviceScope.launch {
            val shouldGhost = RuleEngine.shouldGhost(applicationContext, number)
            
            if (shouldGhost) {
                // Log the rejection
                val db = GhostDatabase.getDatabase(applicationContext)
                val manager = BusyModeManager(applicationContext)
                db.ghostLogDao().insert(
                    GhostLog(number, System.currentTimeMillis(), manager.currentMode.name)
                )

                val response = CallResponse.Builder()
                    .setDisallowCall(true)
                    .setRejectCall(true)
                    .setSkipNotification(true)
                    .setSkipCallLog(true)
                    .build()

                respondToCall(details, response)
            } else {
                respondToCall(details, CallResponse.Builder().build())
            }
        }
    }
}
