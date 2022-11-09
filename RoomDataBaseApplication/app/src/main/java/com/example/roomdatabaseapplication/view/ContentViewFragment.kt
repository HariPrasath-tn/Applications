package com.example.roomdatabaseapplication.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.roomdatabaseapplication.R
import com.example.roomdatabaseapplication.database.AttendanceListDataBase
import com.example.roomdatabaseapplication.databinding.FragmentContentViewBinding
import com.example.roomdatabaseapplication.view_model.ContentViewFragmentViewModel
import com.example.roomdatabaseapplication.view_model.ContentViewModelFactory
import com.example.roomdatabaseapplication.view_model.HomeFragmentViewModel
import com.example.roomdatabaseapplication.view_model.HomeViewMModelFactory

class ContentViewFragment : Fragment() {
    private lateinit var binding:FragmentContentViewBinding
    private lateinit var viewModel: ContentViewFragmentViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_content_view, container, false)
        val application = requireNotNull(this.activity).application
        val dataSource = AttendanceListDataBase.getInstance(application).attendanceListDAO
        val viewModelFactory = ContentViewModelFactory(dataSource, application)
        viewModel = ViewModelProvider(this, viewModelFactory)[ContentViewFragmentViewModel::class.java]
        binding.viewModel = viewModel

        binding.back.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_contentViewFragment_to_homeFragment);
        }

        binding.lifecycleOwner = this
        return binding.root
    }
}