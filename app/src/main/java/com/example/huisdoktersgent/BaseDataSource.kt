package com.example.huisdoktersgent

import android.util.Log
import com.example.huisdoktersgent.utils.Resource
import retrofit2.Response

abstract class BaseDataSource {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Resource.succes(body)
            }
            return error("${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }
    private fun <T> error(message: String): Resource<T> {
        Log.e("remoteDataSource", message)
        return Resource.error(data = null, message = "Network call has failed for the following reason: ${message}")
    }
}