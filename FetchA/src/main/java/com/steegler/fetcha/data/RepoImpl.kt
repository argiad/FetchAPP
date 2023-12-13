package com.steegler.fetcha.data

import androidx.lifecycle.LiveData
import com.steegler.fetcha.data.dao.DataItemDAO
import com.steegler.fetcha.data.entity.DataItem
import com.steegler.fetcha.domain.Repo
import com.steegler.fetchlib.FetchLib
import com.steegler.fetchlib.Result
import javax.inject.Inject

class RepoImpl @Inject constructor(
    private val lib: FetchLib,
    private val dao: DataItemDAO
) : Repo {
    override suspend fun getDataItems(): List<DataItem> {
        // TODO: Need to add Error handler
        return (lib.fetchData() as? Result.Success)?.value?.map { DataItem(it) } ?: emptyList()
    }

    override suspend fun getDataFromDB(): LiveData<List<DataItem>> {
        return dao.getAll()
    }


}