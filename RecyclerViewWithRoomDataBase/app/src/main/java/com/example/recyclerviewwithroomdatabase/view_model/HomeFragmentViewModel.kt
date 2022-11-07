package com.example.recyclerviewwithroomdatabase.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.recyclerviewwithroomdatabase.database.AttendanceList
import com.example.recyclerviewwithroomdatabase.database.AttendanceListDAO
import com.example.recyclerviewwithroomdatabase.database.AttendanceListDAO.*
import com.example.recyclerviewwithroomdatabase.database.AttendanceListDataBase.*
import kotlinx.coroutines.*

class HomeFragmentViewModel(
    private val database: AttendanceListDAO,
    application:Application
): AndroidViewModel(application) {
    val workType = MutableLiveData<String>()
    val currentWork = MutableLiveData<String?>()
    private var viewModelJob = Job()

    init{
        currentWork.value = ""
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private var attendance = MutableLiveData<AttendanceList>()

    fun loadCurrentWork() {
        uiScope.launch {
            currentWork.value = getLastWork()?.workType
        }

    }

    fun onWorkStart(){
        uiScope.launch {
            val newCheckIn = AttendanceList(workType = workType.value)
            insert(newCheckIn)
        }
    }

    private suspend fun insert(attendance: AttendanceList){
        withContext(Dispatchers.IO){
            database.insert(attendance)
        }
    }

    fun onWorkStop(){
        uiScope.launch {
            val workDoneData = getLastWork()

            workDoneData?.workStopTime = System.currentTimeMillis();
            if (workDoneData != null) {
                update(workDoneData)
            }
        }
        workType.value = ""
        currentWork.value = ""
    }

    private suspend fun getLastWork(): AttendanceList?{
        return withContext(Dispatchers.IO){
            var temp: AttendanceList? = database.getLastWork()
            if(temp?.workStartTime != temp?.workStopTime){
                temp = null;
            }
            temp
        }
    }

    private suspend fun update(checkOutUpdate: AttendanceList){
        withContext(Dispatchers.IO){
            database.update(checkOutUpdate)
        }
    }
}