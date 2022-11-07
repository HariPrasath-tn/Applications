package com.example.recyclerviewwithroomdatabase.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerviewwithroomdatabase.R
import com.example.recyclerviewwithroomdatabase.adapter.ViewAdapter
import com.example.recyclerviewwithroomdatabase.database.AttendanceListDataBase
import com.example.recyclerviewwithroomdatabase.databinding.FragmentContentViewBinding
import com.example.recyclerviewwithroomdatabase.view_model.ContentViewFragmentViewModel
import com.example.recyclerviewwithroomdatabase.view_model.ContentViewModelFactory

class ContentViewFragment : Fragment() {
    private lateinit var binding: FragmentContentViewBinding
    private lateinit var viewModel: ContentViewFragmentViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_content_view, container, false)
        val application = requireNotNull(this.activity).application
        val dataSource = AttendanceListDataBase.getInstance(application).attendanceListDAO
        val viewModelFactory = ContentViewModelFactory(dataSource, application)
        viewModel = ViewModelProvider(this, viewModelFactory)[ContentViewFragmentViewModel::class.java]
        binding.viewModel = viewModel

        binding.lifecycleOwner = this
        binding.myRecyclerView.layoutManager = LinearLayoutManager(activity)
//        viewModel.listData.observe(viewLifecycleOwner, Observer{
//            Toast.makeText(activity, viewModel.list.toString(), Toast.LENGTH_SHORT).show()
//            binding.myRecyclerView.adapter =
//                activity?.applicationContext?.let {
//                    viewModel.listData.value?.let { it1 ->
//                        ViewAdapter(
//                            it,
//                            it1
//                        )
//                    }
//                    }
//                }

        val adapter = ViewAdapter(application)
        binding.myRecyclerView.adapter = adapter
        viewModel.list.observe(viewLifecycleOwner,Observer{
            adapter.list = it
            Toast.makeText(activity, it.toString(), Toast.LENGTH_SHORT).show()
        })

        binding.back.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_contentViewFragment_to_homeFragment)
        }

        return binding.root
    }
}