package com.bogsnebes.tinkoffcurs.data.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bogsnebes.tinkoffcurs.data.dto.Topic

interface TopicDao {
    @Query("SELECT * FROM topics")
    fun getMessages(): List<Topic>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(topic: List<Topic>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(topic: Topic)

//    Delete from database

    @Delete
    fun delete(topic: Topic)

    @Query("DELETE FROM topics")
    fun deleteAll()
}