package com.rio.agecalculator.model

import java.util.stream.IntStream.range

/**
 * [AgeCalculator] is the class that has the functionality to calculate the age for the given dates
 */
class AgeCalculator {

    // array of integers containing days count for every month
    private val DAYS_IN_MONTHS:Array<Int> = arrayOf(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)

    // array of string representing the value order in date
    private val dateOrder:Array<String> = arrayOf("year", "month", "days", "hour", "minute", "second")

    /**
     * [calculateAge] is a method of class [AgeCalculator]
     * that calculate the time space between the given dates (start date, end date)
     *
     * @param startDate is array of Integer(representing starting date(orderedBy {YYYY,MM,DD,HH,mm,SS}))
     * @param currentDate is array Integer (representing till date (orderedBy {YYYY,MM,DD,HH,mm,SS}))
     *
     * @return a string containing age for the given data
     */
    fun calculateAge(startDate:Array<Int>, currentDate:Array<Int>):Array<Int>{
        // if the given year is leap Year then Feb has 29 days
        if(isLeapYear(currentDate[0])){
            DAYS_IN_MONTHS[1] = 29
        }

        /*
         * since the start and current date is of type array iterating through them
         */
        for(valueAt in 5 downTo 0) {
            /*
             * since each months has their own days count inCase of current date is smaller than the
             * start, barrows the days from the previous month corresponding to their value
             */
            if(valueAt == 2 && currentDate[valueAt] < startDate[valueAt]){
                currentDate[valueAt] += DAYS_IN_MONTHS[currentDate[valueAt-1]-2]
                if(currentDate[valueAt-1] == 0) {
                    currentDate[valueAt - 1] = 12
                    currentDate[0]--
                }else {
                    currentDate[valueAt - 1] -= 1
                }
            }
            // calculating the difference corresponding to their type
            currentDate[valueAt] = calculateDifference(startDate[valueAt], currentDate[valueAt], dateOrder[valueAt])
            if(currentDate[valueAt] < 0){
                currentDate[valueAt] *= -1
                currentDate[valueAt-1] -= 1
            }

        }
        return currentDate
    }

    /**
     * [calculateDifference] is a method to find the difference between the start
     * and current
     *
     * @param start is an Integer (representing starting value)
     * @param current is an Integer (representing ending value)
     *
     * @return an signed Integer of time difference (negative sign representing borrowed month/day/hour/minute/second for current)
     */
    private fun calculateDifference(start:Int, current:Int, valueType:String):Int{
        // using when to return values for different valueTypes
        return when(valueType){
            // minute and second shares the same properties so they share the same code
            //if start is greater then borrowing the second/minute from the current minute/hour
            // else returns the difference
            "second", "minute" -> {
                if(start>current){
                    return start - (60+current)
                }
                return current-start
            }
            // if start is greater then borrowing the hours from the current day
            // else returns the difference
            "hour" -> {
                if(start>current){
                    return start - (24+current)
                }
                return current-start
            }
            // if start is greater then borrowing the month from the current year
            // else returns the difference
            "month" ->{
                if(start>current){
                    return start - (12+current)
                }
                return current-start
            }
            // this condition executes for the year and days and returns the difference
            else -> current-start
        }
    }

    private fun isLeapYear(year:Int):Boolean{
        if(year%400 == 0 || (year%4 ==0 && year%100 != 0))
            return true
        return false
    }
}