package com.example.lmsmobile.data.repository

import com.example.lmsmobile.data.model.Result
import com.example.lmsmobile.data.network.ApiService

class ResultRepository(private val api: ApiService) {
    suspend fun fetchResults(indexNumber: String): List<Result> {
        return api.getResults(indexNumber)
    }
}