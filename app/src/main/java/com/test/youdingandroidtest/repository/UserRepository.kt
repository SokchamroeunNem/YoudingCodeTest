package com.test.youdingandroidtest.repository

import com.test.youdingandroidtest.database.UserDao
import com.test.youdingandroidtest.database.UserEntity
import javax.inject.Inject

class UserRepository @Inject constructor(private val dao: UserDao) {
    suspend fun saveUser(entity: UserEntity) = dao.saveUser(entity)
    suspend fun updateUser(entity: UserEntity) = dao.updateUser(entity)
    suspend fun deleteUser(entity: UserEntity) = dao.deleteUser(entity)
    fun getAllUsers() = dao.getAllNotes()

    suspend fun getUserByEmailAndPassword(email: String, password: String) =
        dao.getUserByEmailAndPassword(email, password)
}