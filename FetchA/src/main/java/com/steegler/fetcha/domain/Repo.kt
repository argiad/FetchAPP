package com.steegler.fetcha.domain

import androidx.lifecycle.LiveData
import com.steegler.fetcha.data.entity.DataItem

interface Repo {
    suspend fun getDataItems(): List<DataItem>

    suspend fun getDataFromDB(): LiveData<List<DataItem>>
}