package com.rio.agecalculator.view.age_calculator_activity_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.rio.agecalculator.R
import com.rio.agecalculator.databinding.FragmentAgeCalculatorBinding
import com.rio.agecalculator.view_model.AgeCalculatorFragmentViewModel
import java.text.SimpleDateFormat

/**
 * A simple [Fragment] subclass.
 * Use the [AgeCalculatorFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AgeCalculatorFragment : Fragment() {
    // creating a container for the binding variable that establish connection between the fragment
    // and layout xml file
    private lateinit var binding:FragmentAgeCalculatorBinding
    // creating a container for the viewModel variable that establish viewModel data connection between the
    // viewModel,fragment and layout
    private lateinit var viewModel:AgeCalculatorFragmentViewModel
    private var thread: Thread? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // establishing dataBinding between fragment and the layout
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_age_calculator, container, false)
        // creating instance of the viewModel using viewModelProvider
        viewModel = ViewModelProvider(this)[AgeCalculatorFragmentViewModel::class.java]
        // assigning the viewModel to the layout's ViewModel variable
        binding.viewModel = viewModel

        // assigning lifecycleOwner to the binding variable
        binding.lifecycleOwner = this

        // setting onClick listener to the button confirm that initialize the age calculation
        binding.confirm.setOnClickListener {
            var temp = viewModel.initializeAgeCalculation()
            if(temp == -1)
                Toast.makeText(activity, "Data Missing or Invalid Data", Toast.LENGTH_SHORT).show()
        }
        binding.radioGroup.check(binding.radioButton2.id)
        binding.apply {
            radioGroup.setOnCheckedChangeListener { _, i ->
                when(i){
                    radioButton.id -> {
                        setViewForCurrentTime()
                        viewModel?.apply {
                            try {
                                thread = Thread {
                                    try {
                                        while (!thread!!.isInterrupted) {
                                            var time = formatTime(System.currentTimeMillis())
                                            currentYear.postValue(time[0])
                                            currentMonth.postValue(time[1])
                                            currentDate.postValue(time[2])
                                            currentHour.postValue(time[3])
                                            currentMinute.postValue(time[4])
                                            currentSecond.postValue(time[5])
                                            Thread.sleep(1000)
                                        }
                                    }catch(ignored:Exception){}
                                }
                                thread!!.start()
                            }catch(ignored:Exception){}
                        }
                    }
                    radioButton2.id -> {
                        if (thread != null) {
                            thread!!.interrupt()
                        }
                        setViewForManual()
                        viewModel?.apply {
                            currentYear.value = ""
                            currentMonth.value = ""
                            currentDate.value = ""
                            currentHour.value = ""
                            currentMinute.value = ""
                            currentSecond.value = ""
                        }
                    }
                }

            }
        }
        return binding.root
    }


    /**
     * [formatTime] is the method that formats the time from milli seconds to standard format
     * @param time of type long representing the system time in milliSeconds
     * @return array of strings in format[yyyy,mm, dd, hh, mm, ss]
     */
    private fun formatTime(time:Long):Array<String>{
        return SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(time).toString().split("-").toTypedArray()
    }


    /**
     * [setViewForCurrentTime] is the method that disable all the views related to current time as
     * the is automatically set by the device
     */
    private fun setViewForCurrentTime(){
        binding.apply{
            yearCurrent.isEnabled = false
            monthCurrent.isEnabled = false
            dayCurrent.isEnabled = false
            hourCurrent.isEnabled = false
            minCurrent.isEnabled = false
            sCurrent.isEnabled = false
        }
    }

    /**
     * [setViewForManual] is the method that enables all the view that are related for the manual
     * input of the current time
     */
    private fun setViewForManual(){
        binding.apply{
            yearCurrent.isEnabled = true
            monthCurrent.isEnabled = true
            dayCurrent.isEnabled = true
            hourCurrent.isEnabled = true
            minCurrent.isEnabled = true
            sCurrent.isEnabled = true
        }
    }

}

