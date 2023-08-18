package com.example.animalia.screens.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animalia.domain.User
import com.example.animalia.repository.UserRepository
import kotlinx.coroutines.launch

class ProfileViewModel(
    var userRepository: UserRepository
) : ViewModel() {
    private var _currentUser = MutableLiveData<User>()
    val currentUser: LiveData<User>
        get() = _currentUser

    init {
        viewModelScope.launch {
            userRepository.refreshUser()
            _currentUser.value = userRepository.getUser()
        }
    }

    fun editUserPersonalInfo(firstname: String, lastname: String) {
        _currentUser.value?.firstName = firstname
        _currentUser.value?.lastName = lastname
        viewModelScope.launch {
            userRepository.updateUser(_currentUser.value!!)
            _currentUser.value = userRepository.getUser()
        }
    }
}