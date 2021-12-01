package com.bogsnebes.tinkoffcurs.data.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bogsnebes.tinkoffcurs.data.dto.User

interface UserDao {
    @Query("SELECT * FROM users")
    fun getMessages(): List<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(user: List<User>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

//    Delete from database

    @Delete
    fun delete(user: User)

    @Query("DELETE FROM users")
    fun deleteAll()
}