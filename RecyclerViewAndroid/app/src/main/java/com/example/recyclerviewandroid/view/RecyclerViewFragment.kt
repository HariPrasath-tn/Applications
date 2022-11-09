package com.example.recyclerviewandroid.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.recyclerviewandroid.R
import com.example.recyclerviewandroid.adapters.ViewAdapter
import com.example.recyclerviewandroid.databinding.FragmentRecyclerViewBinding
import com.example.recyclerviewandroid.view_model.RecyclerViewViewModel

class RecyclerViewFragment : Fragment() {
    private lateinit var binding:FragmentRecyclerViewBinding
    private lateinit var recyclerViewViewModel: RecyclerViewViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recycler_view, container, false)
        recyclerViewViewModel = ViewModelProvider(this)[RecyclerViewViewModel::class.java]
        binding.myRecyclerView.layoutManager = GridLayoutManager(activity?.applicationContext, 2)

        val itemAdapter = activity?.let { ViewAdapter(it.applicationContext, recyclerViewViewModel.getItemList()) }
        binding.myRecyclerView.adapter = itemAdapter
        binding.lifecycleOwner = this

        return binding.root
    }
}