package com.example.recyclerviewwithroomdatabase.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.databinding.adapters.ViewBindingAdapter.OnViewAttachedToWindow
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.recyclerviewwithroomdatabase.R
import com.example.recyclerviewwithroomdatabase.database.AttendanceListDataBase
import com.example.recyclerviewwithroomdatabase.databinding.FragmentHomeBinding
import com.example.recyclerviewwithroomdatabase.view_model.ContentViewFragmentViewModel
import com.example.recyclerviewwithroomdatabase.view_model.HomeFragmentViewModel
import com.example.recyclerviewwithroomdatabase.view_model.HomeViewMModelFactory

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeFragmentViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        val application = requireNotNull(this.activity).application
        val dataSource = AttendanceListDataBase.getInstance(application).attendanceListDAO
        val viewModelFactory = HomeViewMModelFactory(dataSource, application)
        viewModel = ViewModelProvider(this, viewModelFactory)[HomeFragmentViewModel::class.java]
        binding.homeViewModel = viewModel

        binding.lifecycleOwner = this

        // setting on click listener for the button Attendance List button
        binding.attendanceListButton.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_contentViewFragment)
        }

        binding.apply {
            startWork.setOnClickListener {

                if(workType.text.toString().isNotEmpty()) {
                    workType.visibility = View.INVISIBLE
                    startWork.visibility = View.INVISIBLE

                    currentWork.visibility = View.VISIBLE
                    stopWork.visibility = View.VISIBLE

                    homeViewModel?.onWorkStart()
                    homeViewModel?.loadCurrentWork()
                }
                else
                    Toast.makeText(activity, "Enter a valid WorkType", Toast.LENGTH_SHORT).show()
            }

            stopWork.setOnClickListener {
                workType.visibility = View.VISIBLE
                startWork.visibility = View.VISIBLE

                homeViewModel?.onWorkStop()
                currentWork.visibility = View.INVISIBLE
                stopWork.visibility = View.INVISIBLE
            }


            // setting on click listener for the button Attendance List button
            attendanceListButton.setOnClickListener {
                if(stopWork.isVisible)
                    Toast.makeText(activity, "Finish work to view Work History", Toast.LENGTH_SHORT).show()
                else
                    Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_contentViewFragment)
            }
        }

        return binding.root
    }
}
