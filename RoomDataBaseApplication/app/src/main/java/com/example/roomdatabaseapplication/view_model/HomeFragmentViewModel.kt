package com.example.roomdatabaseapplication.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.roomdatabaseapplication.database.AttendanceList
import com.example.roomdatabaseapplication.database.AttendanceListDAO
import com.example.roomdatabaseapplication.database.AttendanceListDAO.*
import com.example.roomdatabaseapplication.database.AttendanceListDataBase.*
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

    // stops all the coroutine function once the function OnCleared is called
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel() // method to stop the job
    }

    // creating Scope for the coroutines with respect tie the Dispatcher and viewModelJob
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)


    /**
     * loadCurrentWork is a function that is get Execute when a process is started and not yet stopped
     *
     * assigns the value of the workType in the mutable live data "currentWork" which is directly
     * linked with the text view in the layout
     */
    fun loadCurrentWork() {
        uiScope.launch {
            currentWork.value = getLastWork()?.workType
        }

    }

    /**
     * onWorkStart() is the function that is called when the buttonView in the fragment is
     * clicked
     *
     * launching a new job to insert the data
     */
    fun onWorkStart(){
        uiScope.launch {
            val newCheckIn = AttendanceList(workType = workType.value)
            insert(newCheckIn)
        }
    }

    /**
     * method insert -> to insert data into the database
     *
     * keyword 'suspend' to mention run with different thread
     * assigning a thread to the job by the dispatcher and
     * calling the method insert with argument object of the
     * dataclass to insert into the database
     */
    private suspend fun insert(attendance:AttendanceList){
        withContext(Dispatchers.IO){
            database.insert(attendance)
        }
    }


    /**
     * method onWorkStop -> to update the ending time of the work into the database
     *
     * clearing the textView and the edittext since the work is completed
     *
     * launching the new job to update the data in the database(workEnd Time)
     * Methodology:
     *      getting the data from the database and updating the respective data
     *      then passing it to the function update() as an argument
     *      'update()' which updates the database
     */
    fun onWorkStop(){
        workType.value = ""
        currentWork.value = ""
        uiScope.launch {
            val workDoneData = getLastWork()

            workDoneData?.workStopTime = System.currentTimeMillis();
            if (workDoneData != null) {
                update(workDoneData)
            }
        }
    }

    /**
     * method getLastWork() -> gets the work data that is lastly inserted into the database
     *
     * input ==>  UNIT
     * output ==>  attendanceList class Object
     *
     * methodology:
     *      getting the last inserted data from the database using the method "getLAstWork()" of
     *      DAO interface and assigning it to the temporary variable and the returning it
     */
    private suspend fun getLastWork():AttendanceList?{
        return withContext(Dispatchers.IO){
            var temp:AttendanceList? = database.getLastWork()
            if(temp?.workStartTime != temp?.workStopTime){
                temp = null;
            }
            temp
        }
    }


    /**
     *  method update() -> to update the data into the database
     *
     *  methodology:
     *      assigning a new thread to the work by using the Dispatcher
     *      and updating the data into the database using the method update() of the
     *      DAO interface
     */
    private suspend fun update(checkOutUpdate:AttendanceList){
        withContext(Dispatchers.IO){
            database.update(checkOutUpdate)
        }
    }
}