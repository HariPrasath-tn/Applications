package com.example.patternvalidator.view

import android.database.DatabaseUtils
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.patternvalidator.R
import com.example.patternvalidator.databinding.FragmentPatternValidatorBinding
import com.example.patternvalidator.view_model.PatterValidatorViewModel

class PatternValidatorFragment : Fragment() {
    private lateinit var binding:FragmentPatternValidatorBinding
    private lateinit var viewModel:PatterValidatorViewModel;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // creating data binding between xml and activity
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pattern_validator, container, false)
        viewModel = ViewModelProvider(this)[PatterValidatorViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.validateBtn.setOnClickListener {
            viewModel.startMatching()
        }
        return binding.root;
    }

}