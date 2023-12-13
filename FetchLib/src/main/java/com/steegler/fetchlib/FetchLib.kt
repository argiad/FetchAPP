package com.steegler.fetchlib

interface FetchLib {

    @Throws
    suspend fun fetchData(route: String = Constants.DATA_FILE_ROUTE): Result<List<DataResponseItem>, FetchLibError>
}