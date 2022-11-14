package com.rio.agecalculator.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rio.agecalculator.model.AgeCalculator

class AgeCalculatorFragmentViewModel : ViewModel() {
    var temp = 0
    var count = 0
    var startYear = MutableLiveData<String>()
    var startMonth = MutableLiveData<String>()
    var startDate = MutableLiveData<String>()
    var startHour = MutableLiveData<String>()
    var startMinute = MutableLiveData<String>()
    var startSecond = MutableLiveData<String>()
    var currentYear = MutableLiveData<String>()
    var currentMonth = MutableLiveData<String>()
    var currentDate = MutableLiveData<String>()
    var currentHour = MutableLiveData<String>()
    var currentMinute = MutableLiveData<String>()
    var currentSecond = MutableLiveData<String>()
    var dateResult = MutableLiveData<String>()
    var timeResult = MutableLiveData<String>()

    fun initializeAgeCalculation():Int{
        var start:Array<Int> = arrayOf(
            startYear.value?.toInt() ?: -1,
            startMonth.value?.toInt() ?: -1,
            startDate.value?.toInt() ?: -1,
            startHour.value?.toInt() ?: 0,
            startMinute.value?.toInt() ?: 0,
            startSecond.value?.toInt() ?: 0
        )

        var current: Array<Int> = arrayOf(
            currentYear.value?.toInt()?: -1,
            currentMonth.value?.toInt()?: -1,
            currentDate.value?.toInt()?: -1,
            currentHour.value?.toInt()?: 0,
            currentMinute.value?.toInt()?: 0,
            currentSecond.value?.toInt()?: 0
        )
        if(isDataMissing(start) || isDataMissing(current))
            return -1
//        if(!isDataEnteredCorrect())
//            return -1
        var result = AgeCalculator().calculateAge(start, current)

        timeResult.value = "${result[3]}:${result[4]}:${result[5]}"
        dateResult.value = "${result[0]}Years ${result[1]}Months ${result[2]}Days"
        try {
            if(count != 0) {
                temp = 1
                Thread.sleep(2000)
            }
            temp = 0
            count++
            Thread {
                while (temp == 0) {
                    timeResult.postValue("${result[3]}:${result[4]}:${result[5]}")
                    dateResult.postValue("${result[0]}Years ${result[1]}Months ${result[2]}Days")
                    Thread.sleep(1000)
                    if (result[5] == 59) {
                        result[5] = 0
                        result[4]++
                    } else {
                        result[5]++
                    }

                    if (result[4] == 60) {
                        result[4] = 0
                        result[3]++
                    }

                    if (result[3] == 24) {
                        result[3] = 0
                        result[2]++
                    }
                }
            }.start()
        }catch(ignored:Exception){}
        return 1
    }


    private fun isDataMissing(array:Array<Int>):Boolean{
        for(element in array){
            if(element == -1)
                return true
        }
        return false
    }

    /**
     * [isDataEnteredCorrect] is the method that validate the data entered
     * @return boolean true if entered data is correct else false
     */
    private fun isDataEnteredCorrect():Boolean{
        if(
            currentMonth.value?.toInt()!! < 1 || currentMonth.value?.toInt()!! > 12 || startMonth.value?.toInt()!! < 1 || startMonth.value?.toInt()!! > 12
            || currentDate.value?.toInt()!! < 1 || startDate.value?.toInt()!! < 1 || currentHour.value?.toInt()!! < 0 || startHour.value?.toInt()!! < 0
            || currentYear.value?.toInt()!! < startYear.value?.toInt()!!
            || currentYear.value?.toInt()!! == startYear.value?.toInt()!! && currentMonth.value?.toInt()!!<startMonth.value?.toInt()!!
            || currentYear.value?.toInt()!! == startYear.value?.toInt()!! && currentMonth.value?.toInt()!! == startMonth.value?.toInt()!! && currentDate.value?.toInt()!!<startDate.value?.toInt()!!
            || currentYear.value?.toInt()!! == startYear.value?.toInt()!! && currentMonth.value?.toInt()!! == startMonth.value?.toInt()!! && currentDate.value?.toInt()!! == startDate.value?.toInt()!! && currentHour.value?.toInt()!! < startHour.value?.toInt()!!
            || currentYear.value?.toInt()!! == startYear.value?.toInt()!! && currentMonth.value?.toInt()!! == startMonth.value?.toInt()!! && currentDate.value?.toInt()!! == startDate.value?.toInt()!! && currentHour.value?.toInt()!! == startHour.value?.toInt()!! && currentMinute.value?.toInt()!! < startMinute.value?.toInt()!!
            || currentYear.value?.toInt()!! == startYear.value?.toInt()!! && currentMonth.value?.toInt()!! == startMonth.value?.toInt()!! && currentDate.value?.toInt()!! == startDate.value?.toInt()!! && currentHour.value?.toInt()!! == startHour.value?.toInt()!! && currentMinute.value?.toInt()!! == startMinute.value?.toInt()!! && currentSecond.value?.toInt()!! < startSecond.value?.toInt()!!
        )
            return false
        return true
    }
}