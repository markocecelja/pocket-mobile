package com.mcecelja.pocket.ui

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mcecelja.pocket.data.dto.users.UserDTO

open class BaseViewModel : ViewModel() {

    private val _allowBack: MutableLiveData<Boolean> = MutableLiveData<Boolean>(true)
    val allowBack: LiveData<Boolean> = _allowBack

    private val _loadingVisibility: MutableLiveData<Int> = MutableLiveData<Int>(View.INVISIBLE)
    val loadingVisibility: LiveData<Int> = _loadingVisibility

    private val _error: MutableLiveData<String> = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _user: MutableLiveData<UserDTO> = MutableLiveData<UserDTO>()
    val user: LiveData<UserDTO> = _user

    fun changeVisibility(visibility: Int) {
        _loadingVisibility.postValue(visibility)
    }

    fun setAllowBack(allowBack: Boolean) {
        _allowBack.postValue(allowBack)
    }

    fun setError(error: String) {
        _error.postValue(error)
    }

    fun setUser(userDTO: UserDTO) {
        _user.postValue(userDTO)
    }
}