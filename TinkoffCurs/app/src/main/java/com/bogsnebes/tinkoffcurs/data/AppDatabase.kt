package com.bogsnebes.tinkoffcurs.data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bogsnebes.tinkoffcurs.App
import com.bogsnebes.tinkoffcurs.data.dao.MessageDao
import com.bogsnebes.tinkoffcurs.data.dao.StreamDao
import com.bogsnebes.tinkoffcurs.data.dao.TopicDao
import com.bogsnebes.tinkoffcurs.data.dao.UserDao
import com.bogsnebes.tinkoffcurs.data.dto.Message
import com.bogsnebes.tinkoffcurs.data.dto.Stream
import com.bogsnebes.tinkoffcurs.data.dto.Topic
import com.bogsnebes.tinkoffcurs.data.dto.User

@Database(entities = [Stream::class, Message::class, Topic::class, User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun messageDao(): MessageDao
    abstract fun streamDao(): StreamDao
    abstract fun topicDao(): TopicDao
    abstract fun userDao(): UserDao

    companion object {
        private const val DATABASE_NAME = "Tinkoff.db"

        val instance: AppDatabase by lazy {
            Room.databaseBuilder(
                App.context,
                AppDatabase::class.java,
                DATABASE_NAME
            ).allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}