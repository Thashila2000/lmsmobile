package com.example.lmsmobile.ui.dashboard

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lmsmobile.data.model.TaskDto
import com.example.lmsmobile.data.repository.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TaskViewModel(private val repository: TaskRepository) : ViewModel() {
    private val _tasks = MutableStateFlow<List<TaskDto>>(emptyList())
    val tasks: StateFlow<List<TaskDto>> = _tasks

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage

    fun loadTasks(degreeId: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = ""
            try {
                val result = repository.fetchTasks(degreeId)
                _tasks.value = result
                Log.d("TaskViewModel", "Received tasks: ${result.size}") // ✅ Correct variable
            } catch (e: Exception) {
                _errorMessage.value = "Failed to load tasks: ${e.localizedMessage ?: "Unknown error"}"
                Log.e("TaskViewModel", "Error loading tasks", e)
            } finally {
                _isLoading.value = false
            }
        }
    }
}