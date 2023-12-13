package com.steegler.fetchlib

import com.android.volley.RequestQueue

internal interface NetworkService {
    val queue: RequestQueue

    suspend fun getData(url: String): Result<List<DataResponseItem>, FetchLibError>
}