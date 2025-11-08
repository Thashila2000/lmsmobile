package com.example.lmsmobile.ui.results

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.lmsmobile.data.repository.ResultRepository

class ResultViewModelFactory(private val repository: ResultRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ResultViewModel(repository) as T
    }
}