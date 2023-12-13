package com.steegler.fetchlib


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DataResponseItem(
    @SerialName("id")
    val id: Int,
    @SerialName("listId")
    val listId: Int,
    @SerialName("name")
    val name: String?
)