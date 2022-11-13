package com.rio.worldweather.view_model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FLocationListViewModelFactory(val context:Context): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(LocationListViewModel::class.java)){
            return LocationListViewModel(context) as T
        }

        // throwing exception if the viewModel is not assignable
        throw IllegalArgumentException("Illegal Argument Provide")
    }
}