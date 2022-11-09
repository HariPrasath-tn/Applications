package com.example.roomdatabaseapplication.view_model

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.roomdatabaseapplication.database.AttendanceListDAO
import javax.sql.CommonDataSource

/**
 * ViewModelFactory is a class that extends Factory of ViewModelProvider
 *
 * usage:
 *      Factory class are used when arguments are need to be passed to the view Model
 *
 * implementation below:
 *      class Constructor takes two arguments namely
 *          1.DAO interface object
 *          2.application Context
 *      parameters of constructor are the passed to the constructor of new instance of viewModel class
 *
 * finally ==> returns the instance if viewModelAvailable
 * else:
 *      throws IllegalArgumentException
 */
class HomeViewMModelFactory(
    private val dataSource:AttendanceListDAO,
    private val application: Application) : ViewModelProvider.Factory{

    @Suppress("unchecked_case")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeFragmentViewModel::class.java))
            return HomeFragmentViewModel(dataSource, application) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}