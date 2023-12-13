package com.steegler.fetchlib

import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import kotlinx.serialization.json.Json
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

internal class NetworkServiceImpl(override val queue: RequestQueue) : NetworkService {
    override suspend fun getData(url: String): Result<List<DataResponseItem>, FetchLibError> = suspendCoroutine { cont ->


        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                try {
                    val responseData = Json.decodeFromString<List<DataResponseItem>>(response.toString().trim())
                    cont.resume(Result.Success(responseData))
                } catch (e: Exception) {
                    e.printStackTrace()
                    cont.resume(Result.Failure(FetchLibError.NetworkError(e.localizedMessage)))
                }
            },
            { error ->
                cont.resume(Result.Failure(FetchLibError.NetworkError(error.localizedMessage)))
            }
        )

        queue.add(stringRequest)
    }

}