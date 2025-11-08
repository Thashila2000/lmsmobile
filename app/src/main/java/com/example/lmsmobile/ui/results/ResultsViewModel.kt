package com.example.lmsmobile.ui.results

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lmsmobile.data.model.Result
import com.example.lmsmobile.data.repository.ResultRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ResultViewModel(private val repository: ResultRepository) : ViewModel() {
    private val _results = MutableStateFlow<List<Result>>(emptyList())
    val results: StateFlow<List<Result>> = _results

    fun loadResults(indexNumber: String) {
        viewModelScope.launch {
            try {
                val resultList = repository.fetchResults(indexNumber)
                _results.value = resultList
                Log.d("ResultViewModel", "Loaded ${resultList.size} results")
            } catch (e: Exception) {
                Log.e("ResultViewModel", "Error loading results", e)
            }
        }
    }
}