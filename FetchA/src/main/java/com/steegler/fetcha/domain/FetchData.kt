package com.steegler.fetcha.domain

import com.steegler.fetcha.Resource
import com.steegler.fetcha.data.dao.DataItemDAO
import com.steegler.fetcha.data.entity.DataItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class FetchData @Inject constructor(
    private val repository: Repo,
    private val dao: DataItemDAO
) {
    operator fun invoke(): Flow<Resource<List<DataItem>>> = flow {
        try {
            emit(Resource.Loading())
            val list = repository.getDataItems()
            if (list.isNotEmpty()) {
                withContext(Dispatchers.IO) {
                    dao.cleanTable()
                    dao.insertAll(*list.filter { !it.name.isNullOrEmpty() }.toTypedArray())
                }
            }
            emit(Resource.Success(list))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }
}