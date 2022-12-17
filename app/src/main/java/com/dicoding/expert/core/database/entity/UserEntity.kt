package com.dicoding.expert.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserEntity(
    @PrimaryKey
    @field:ColumnInfo(name = "id")
    var id: Int?,

    @field:ColumnInfo(name = "username")
    var username: String?,

    @field:ColumnInfo(name = "full_name")
    var name: String?,

    @field:ColumnInfo(name = "git_url")
    var gitUrl: String?,

    @field:ColumnInfo(name = "city")
    var location: String?,

    @field:ColumnInfo(name = "company")
    var company: String?,

    @field:ColumnInfo(name = "avatarUrl")
    var avatarUrl: String?,

    @field:ColumnInfo(name = "user_type")
    var userType: String?,
)
