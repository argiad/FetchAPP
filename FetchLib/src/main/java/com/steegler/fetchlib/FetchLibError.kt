package com.steegler.fetchlib

sealed class FetchLibError(text: String) : Error(text) {
    data class NoContext(val text: String = "Context did not provided during the building process") : FetchLibError(text)
    data class NetworkError(val text: String = "Undefined Network Error") :FetchLibError(text)
}