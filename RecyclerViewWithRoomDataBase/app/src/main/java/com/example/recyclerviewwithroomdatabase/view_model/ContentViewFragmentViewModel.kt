package com.example.recyclerviewwithroomdatabase.view_model

import android.annotation.SuppressLint
import android.app.Application
import android.content.res.Resources
import android.os.Build
import android.text.Html
import android.text.Spanned
import androidx.core.text.HtmlCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.recyclerviewwithroomdatabase.database.AttendanceList
import com.example.recyclerviewwithroomdatabase.database.AttendanceListDAO
import kotlinx.coroutines.*
import java.text.SimpleDateFormat

class ContentViewFragmentViewModel(private val database: AttendanceListDAO, application: Application) : AndroidViewModel(application) {
    val list = MutableLiveData<ArrayList<String>>()
    private var viewModelJob = Job()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init{
        initializeListing()

    }

    fun initializeListing(){
        var _list = arrayListOf<String>()
        uiScope.launch {
            val attendanceList = getTodayAttendanceList()
                for(attendance in attendanceList){
                    if(attendance.workStartTime != attendance.workStopTime) {
                        _list.add( "UserID = ${attendance.userID}\nworkStartTime : " +
                                "\n\t\t${convertLongToDateString(attendance.workStartTime)}\nworkStopTime : " +
                                "\n\t\t${convertLongToDateString(attendance.workStopTime)}" +
                                "\nTotalWorkStartTime : ${attendance.workStopTime.minus(attendance.workStartTime) / 1000 / 60 / 60}:" +
                                "${attendance.workStopTime.minus(attendance.workStartTime) / 1000 / 60}:" +
                                "${attendance.workStopTime.minus(attendance.workStartTime) / 1000}\n" +
                                "Work Type : ${attendance.workType}\n")
                    }else{
                        _list.add("UserID = ${attendance.userID}\nworkStartTime : " +
                                "\n\t\t${convertLongToDateString(attendance.workStartTime)}\nworkStopTime : " +
                                "\n\t\tNot Yet Checked Out\n" +
                                "Work Type : ${attendance.workType}\n")
                    }
                }
            list.value = _list
        }
    }


    @Suppress
    private fun convertLongToDateString(systemTime:Long):String {
        return SimpleDateFormat("EEEE MMM-dd-yyyy ' \n\t\tTime: ' HH:mm").format(systemTime).toString()
    }
    private suspend fun getTodayAttendanceList():List<AttendanceList>{
        return withContext(Dispatchers.IO){
            database.getList()
        }
    }
}