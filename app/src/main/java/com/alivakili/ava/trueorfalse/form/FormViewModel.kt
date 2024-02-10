package com.alivakili.ava.trueorfalse.form

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FormViewModel () : ViewModel(){

    private val _state: MutableStateFlow<FormState> = MutableStateFlow(FormState.Idle)
    val state: StateFlow<FormState> =_state

    fun startGame(form: FormModel){
        _state.value=FormState.Idle
        viewModelScope.launch {
            if(!form.isValid()){
                _state.value=FormState.InvalidForm(
                    isAmountValid = form.isAmountValid(),
                    isDifficultyValid = form.isDifficultyValid(),
                    isCategoryValid = form.isCategoryValid(),
                    )

            }else{
                _state.value= FormState.Success(
                    amount = form.amount,
                    difficulty = form.difficulty,
                    category = form.category
                )
            }
        }
    }

    companion object{
        fun factory(): ViewModelProvider.Factory{
            return object : ViewModelProvider.Factory{
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return FormViewModel()as T
                }
            }
        }
    }
}
