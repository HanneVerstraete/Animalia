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
}