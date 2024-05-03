package com.test.youdingandroidtest.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.test.youdingandroidtest.utils.Constants.USER_TABLE
import kotlinx.parcelize.Parcelize

@Entity(tableName = USER_TABLE)
@Parcelize
data class UserEntity(

    @PrimaryKey(autoGenerate = true) var userId: Int = 0,

    @ColumnInfo(name = "full_name") var fullName: String = "",

    @ColumnInfo(name = "email") var email: String = "",

    @ColumnInfo(name = "password_text") var passwrd: String = "",

    @ColumnInfo(name = "created_at") var createdAt: Long = 0
) : Parcelable