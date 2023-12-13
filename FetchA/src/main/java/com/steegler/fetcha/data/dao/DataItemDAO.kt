package com.steegler.fetcha.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.steegler.fetcha.data.entity.DataItem

@Dao
interface DataItemDAO {
    @Query("SELECT * FROM dataitem ORDER by listId ASC")
    fun getAll(): LiveData<List<DataItem>>

    @Query("DELETE FROM dataitem")
    fun cleanTable()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg messages: DataItem)
}