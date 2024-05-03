package com.test.youdingandroidtest.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class UserDB : RoomDatabase() {
    abstract fun noteDao(): UserDao
}