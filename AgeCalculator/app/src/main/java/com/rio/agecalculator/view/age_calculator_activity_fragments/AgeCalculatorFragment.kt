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
                Toast.makeText(activity, "Data Missing", Toast.LENGTH_SHORT).show()
        }
        return binding.root
    }
}