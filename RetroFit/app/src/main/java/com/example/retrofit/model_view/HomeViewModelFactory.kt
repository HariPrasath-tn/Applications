package com.example.retrofit.model_view

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * HomeViewModelFactory is an user defined class that extends ViewModelProvider.FactoryClass
 *  > USAGE: used to pass arguments to the viewModel
 *
 *  Methodology:
 *      return the instance of the viewModel class by validating whether the class extends the
 *      lifeCycle's ViewModel class
 *      if not ==> @throws illegalArgumentException
 */
class HomeViewModelFactory(private val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(HomeFragmentViewModel::class.java)){
            return HomeFragmentViewModel(application) as T
        }
        throw java.lang.IllegalArgumentException("Unable to create the instance of the view model")
    }
}