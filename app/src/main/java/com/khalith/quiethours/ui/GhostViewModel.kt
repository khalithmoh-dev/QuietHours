package com.khalith.quiethours.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.khalith.quiethours.data.GhostDatabase
import com.khalith.quiethours.data.GhostLog
import com.khalith.quiethours.data.GhostNumber
import com.khalith.quiethours.data.GhostRepository
import kotlinx.coroutines.launch

class GhostViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: GhostRepository
    val allGhostNumbers = androidx.lifecycle.MutableLiveData<List<GhostNumber>>()
    val allLogs = androidx.lifecycle.MutableLiveData<List<GhostLog>>()

    init {
        val db = GhostDatabase.getDatabase(application)
        repository = GhostRepository(db.ghostNumberDao(), db.ghostLogDao())
        refreshData()
    }

    fun refreshData() = viewModelScope.launch(kotlinx.coroutines.Dispatchers.IO) {
        allGhostNumbers.postValue(repository.getAllGhostNumbers())
        allLogs.postValue(repository.getAllLogs())
    }

    fun insertGhostNumber(ghostNumber: GhostNumber) = viewModelScope.launch(kotlinx.coroutines.Dispatchers.IO) {
        repository.insertGhostNumber(ghostNumber)
        refreshData()
    }

    fun deleteGhostNumber(ghostNumber: GhostNumber) = viewModelScope.launch(kotlinx.coroutines.Dispatchers.IO) {
        repository.deleteGhostNumber(ghostNumber)
        refreshData()
    }

    fun clearLogs() = viewModelScope.launch(kotlinx.coroutines.Dispatchers.IO) {
        repository.clearLogs()
        refreshData()
    }
}
