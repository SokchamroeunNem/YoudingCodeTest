package com.test.youdingandroidtest.di

import android.content.Context
import androidx.room.Room
import com.test.youdingandroidtest.database.UserDB
import com.test.youdingandroidtest.database.UserEntity
import com.test.youdingandroidtest.utils.Constants.USER_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, UserDB::class.java, USER_DATABASE
    ).allowMainThreadQueries().fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideDao(db: UserDB) = db.noteDao()

    @Provides
    fun provideEntity() = UserEntity()

}