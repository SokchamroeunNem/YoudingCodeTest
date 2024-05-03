package com.test.youdingandroidtest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.youdingandroidtest.database.UserEntity
import com.test.youdingandroidtest.repository.UserRepository
import com.test.youdingandroidtest.utils.DataStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: UserRepository) : ViewModel() {

    private var _usersList: MutableLiveData<DataStatus<List<UserEntity>>> = MutableLiveData()
    val usersList: LiveData<DataStatus<List<UserEntity>>> get() = _usersList

    private val _loginResult = MutableStateFlow<Boolean?>(null)
    val loginResult: StateFlow<Boolean?> = _loginResult

    init {
        getAllUsers()
    }


    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            val user = repository.getUserByEmailAndPassword(email, password)
            if (user != null) {
                _loginResult.value = true
            } else {
                _loginResult.value = false
            }
        }
    }

    fun saveUser(entity: UserEntity) = viewModelScope.launch {
        repository.saveUser(entity)
    }

    fun deleteUser(entity: UserEntity) = viewModelScope.launch {
        repository.deleteUser(entity)
    }

    fun getAllUsers() = viewModelScope.launch {
        _usersList.postValue(DataStatus.Loading())
        repository.getAllUsers()
            .catch { _usersList.postValue(DataStatus.Error(it.message.toString())) }
            .collect { _usersList.postValue(DataStatus.Success(it)) }
    }

}