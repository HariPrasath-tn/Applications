package com.example.patternvalidator.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.patternvalidator.model.PatternCracker

/*
 * View model for the class PatternValidator fragment
 */
class PatterValidatorViewModel : ViewModel() {

    // mutable live data variables
    val sentence = MutableLiveData<String>()
    val pattern = MutableLiveData<String>()
    val result = MutableLiveData<String>()

    init{
        sentence.value = ""
        pattern.value = ""
        result.value = ""
    }



    // method to initialize pattern matching
    fun startMatching(){
        result.value = if(PatternCracker.beginCracking(pattern.value.toString(), sentence.value.toString()))
            "Pattern Matching"
        else
            "Pattern not Matching"
    }
}