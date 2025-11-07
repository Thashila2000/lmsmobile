package com.example.lmsmobile.data.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lmsmobile.data.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginViewModel : ViewModel() {

    // Input fields
    private val _indexNumber = MutableStateFlow("")
    val indexNumber: StateFlow<String> = _indexNumber

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    // UI state
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage

    private val _loginSuccess = MutableStateFlow<LoginResponse?>(null)
    val loginSuccess: StateFlow<LoginResponse?> = _loginSuccess

    // Input handlers
    fun onIndexNumberChange(value: String) {
        _indexNumber.value = value
    }

    fun onPasswordChange(value: String) {
        _password.value = value
    }

    // Login logic
    fun login() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = ""
            _loginSuccess.value = null

            try {
                val request = LoginRequest(_indexNumber.value, _password.value)
                val response = RetrofitClient.api.login(request)

                if (response.isSuccessful) {
                    response.body()?.let {
                        _loginSuccess.value = it
                    } ?: run {
                        _errorMessage.value = "Empty response from server"
                    }
                } else {
                    _errorMessage.value = "Invalid credentials"
                }

            } catch (e: HttpException) {
                _errorMessage.value = "Server error: ${e.code()}"
            } catch (e: Exception) {
                _errorMessage.value = "Network error: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Clear success state after navigation
    fun clearLoginSuccess() {
        _loginSuccess.value = null
    }
}