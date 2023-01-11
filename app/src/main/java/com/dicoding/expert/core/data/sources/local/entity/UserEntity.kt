package com.dicoding.expert.core.data.sources.local.entity

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

    @field:ColumnInfo(name = "avatar_url")
    var avatarUrl: String?,

    @field:ColumnInfo(name = "user_type")
    var userType: String?,

    @field:ColumnInfo(name = "biodata")
    var biodata: String?,

    @field:ColumnInfo(name = "repo_url")
    var repoUrl: String?,

    @field:ColumnInfo(name = "repository")
    var repository: Int? = 0,

    @field:ColumnInfo(name = "followers")
    var followers: Int? = 0,

    @field:ColumnInfo(name = "following")
    var following: Int? = 0,
)
