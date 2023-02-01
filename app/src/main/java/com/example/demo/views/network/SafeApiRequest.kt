package com.example.demo.views.network

import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response
import java.net.HttpURLConnection

object SafeApiRequest {

    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): T {

        try {
            val response = call.invoke()

            if (response.isSuccessful && response.code() == HttpURLConnection.HTTP_OK) {
                return response.body()!!
            } else {
                val error = response.errorBody()?.string()
                val message = StringBuilder()
                error?.let {
                    try {
                        message.append(JSONObject(it).getString("message"))
                    } catch (e: JSONException) {
                    }
                    message.append("\n")
                }
                throw ApiException(response.code(), message.toString())
            }

        } catch (e: Exception) {
            e.printStackTrace()
            throw ApiException(message = e.message ?: "Unknown Error!")
        }
    }
}