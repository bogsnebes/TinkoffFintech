package com.bogsnebes.tinkoffcurs.data.dao

import androidx.room.*
import com.bogsnebes.tinkoffcurs.data.dto.Stream

@Dao
interface StreamDao {
    @Query("SELECT * FROM streams")
    fun getMessages(): List<Stream>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(stream: List<Stream>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(stream: Stream)

//    Delete from database

    @Delete
    fun delete(stream: Stream)

    @Query("DELETE FROM streams")
    fun deleteAll()
}