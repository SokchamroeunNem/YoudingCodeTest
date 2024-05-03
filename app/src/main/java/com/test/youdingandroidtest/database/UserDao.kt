package com.test.youdingandroidtest.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.test.youdingandroidtest.utils.Constants.USER_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUser(notesEntity: UserEntity)

    @Update
    suspend fun updateUser(notesEntity: UserEntity)

    @Delete
    suspend fun deleteUser(notesEntity: UserEntity)

    @Query("SELECT * FROM $USER_TABLE ORDER BY created_at DESC")
    fun getAllNotes(): Flow<MutableList<UserEntity>>

    @Query("DELETE FROM $USER_TABLE")
    fun deleteAllNotes()

    // Assuming you have a method to get a user by email and password
    @Query("SELECT * FROM $USER_TABLE WHERE email = :email AND password_text = :password")
    suspend fun getUserByEmailAndPassword(email: String, password: String): UserEntity?

}