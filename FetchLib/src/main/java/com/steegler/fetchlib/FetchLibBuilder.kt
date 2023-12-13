package com.steegler.fetchlib

import android.content.Context
import com.android.volley.toolbox.HurlStack
import com.android.volley.toolbox.Volley


sealed class FetchLibBuilder {

    private class FetchLibImpl(var baseUrl: String) : FetchLib {


        private var networkService: NetworkService? = null

        fun initVolley(context: Context) {
            val queue = Volley.newRequestQueue(context, object : HurlStack() {}) // Temporary solution !!!!
            networkService = NetworkServiceImpl(queue)
        }

        override suspend fun fetchData(route: String): Result<List<DataResponseItem>, FetchLibError> {
            return networkService?.getData("$baseUrl$route") ?: run {
                throw FetchLibError.NoContext()
            }
        }

    }

    companion object {
        private val entity: FetchLibImpl = FetchLibImpl(Constants.BASE_URL)

        fun withBaseUrl(url: String) = apply { entity.baseUrl = url }
        fun withContext(context: Context) = apply { entity.initVolley(context) }
        fun build(): FetchLib = entity
    }
}
