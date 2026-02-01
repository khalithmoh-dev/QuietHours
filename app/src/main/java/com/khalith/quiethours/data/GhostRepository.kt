package com.khalith.quiethours.data

import kotlinx.coroutines.flow.Flow

class GhostRepository(private val ghostNumberDao: GhostNumberDao, private val ghostLogDao: GhostLogDao) {
    // We'll use a simple list instead of Flow for now as Java interfaces are simpler
    fun getAllGhostNumbers(): List<GhostNumber> = ghostNumberDao.allGhostNumbersList
    fun getAllLogs(): List<GhostLog> = ghostLogDao.allLogs

    fun insertGhostNumber(ghostNumber: GhostNumber) {
        ghostNumberDao.insert(ghostNumber)
    }

    fun deleteGhostNumber(ghostNumber: GhostNumber) {
        ghostNumberDao.delete(ghostNumber)
    }

    fun insertLog(log: GhostLog) {
        ghostLogDao.insert(log)
    }

    fun clearLogs() {
        ghostLogDao.clearLogs()
    }
}
