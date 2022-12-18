package com.dicoding.expert.data.models.base

sealed class ApiResponse<out R> private constructor() {
    data class Success<out T>(val data: T) : ApiResponse<T>()
    data class Error(val errMsg: String) : ApiResponse<Nothing>()
}