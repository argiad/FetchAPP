package com.steegler.fetcha.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.steegler.fetcha.data.dao.DataItemDAO
import com.steegler.fetcha.data.entity.DataItem
import com.steegler.fetcha.domain.FetchData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject


@HiltViewModel
class ListViewModel @Inject constructor(
    private var fetchData: FetchData,
    private val dao: DataItemDAO
) : ViewModel() {


    val items: LiveData<List<DataItem>> = dao.getAll()

    init {
        fetch()
    }

    fun fetch() {
        fetchData().launchIn(viewModelScope)
    }
}


data class ResultState(

    val isLoading: Boolean = true,
    val itemsList: List<DataItem> = emptyList(),
    val error: String = "",
)
