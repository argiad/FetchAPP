package com.steegler.fetchb.domain

import com.steegler.fetchlib.DataResponseItem

sealed interface FetchState {
    data class Load(val text: String = "Loading") : FetchState
    data class Error(val e: Throwable) : FetchState
    data class Content(val data: List<DataResponseItem> = emptyList()) : FetchState

}

sealed interface FetchAction {
    data object RetryLoad : FetchAction

}