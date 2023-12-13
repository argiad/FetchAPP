package com.steegler.fetcha.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.steegler.fetchlib.DataResponseItem

@Entity
data class DataItem(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "listId")
    val listId: Int,
    @ColumnInfo(name = "name")
    val name: String?
) {
    constructor(dataResponseItem: DataResponseItem) : this(dataResponseItem.id, dataResponseItem.listId, dataResponseItem.name)
}
