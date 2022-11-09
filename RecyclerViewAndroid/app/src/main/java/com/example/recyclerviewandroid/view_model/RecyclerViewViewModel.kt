package com.example.recyclerviewandroid.view_model

import androidx.lifecycle.ViewModel
import com.example.recyclerviewandroid.ViewDataClass
import com.example.recyclerviewandroid.adapters.ViewAdapter

class RecyclerViewViewModel : ViewModel() {

    fun getItemList():ArrayList<ViewDataClass>{
        val list = ArrayList<ViewDataClass>()

        for(num in 1..50){
            list.add(ViewDataClass("$num*${5}=${num*5}", if(num%2 == 0){
                ViewAdapter.VIEW_TYPE_ONE
            }else{
                ViewAdapter.VIEW_TYPE_TWO
            }))
        }
        return list
    }
}