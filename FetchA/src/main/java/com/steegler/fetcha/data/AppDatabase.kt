package com.steegler.fetcha.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.steegler.fetcha.data.dao.DataItemDAO
import com.steegler.fetcha.data.entity.DataItem

@Database(
    entities = [
        DataItem::class
    ],
    version = 1
)


abstract class AppDatabase : RoomDatabase() {
    abstract fun dataItemDAO(): DataItemDAO
}
