package com.bogsnebes.tinkoffcurs.data.dao

import androidx.room.*
import com.bogsnebes.tinkoffcurs.data.dto.Message

@Dao
interface MessageDao {
    @Query("SELECT * FROM messages")
    fun getMessages(): List<Message>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(message: List<Message>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(message: Message)

//    Delete from database

    @Delete
    fun delete(message: Message)

    @Query("DELETE FROM messages")
    fun deleteAll()
}