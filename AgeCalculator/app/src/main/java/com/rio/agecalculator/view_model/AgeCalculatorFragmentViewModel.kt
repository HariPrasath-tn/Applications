package com.rio.agecalculator.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rio.agecalculator.model.AgeCalculator

class AgeCalculatorFragmentViewModel : ViewModel() {
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
        var result = AgeCalculator().calculateAge(start, current)

        timeResult.value = "${result[3]}:${result[4]}:${result[5]}"
        dateResult.value = "${result[0]}Years ${result[1]}Months ${result[2]}Days"
        try {
            Thread {
                while (true) {
                    timeResult.value = "${result[3]}:${result[4]}:${result[5]}"
                    dateResult.value = "${result[0]}Years ${result[1]}Months ${result[2]}Days"
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
            }
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
}