package com.example.recyclerviewwithroomdatabase.view_model

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recyclerviewwithroomdatabase.database.AttendanceListDAO

class ContentViewModelFactory(private val dataSource: AttendanceListDAO, private val application:Application):ViewModelProvider.Factory {
    @Suppress("unchecked_case")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ContentViewFragmentViewModel::class.java))
            return ContentViewFragmentViewModel(dataSource, application) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}