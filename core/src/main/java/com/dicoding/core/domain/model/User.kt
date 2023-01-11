package com.dicoding.core.domain.model

import  android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var id: Int?,
    var username: String?,
    var name: String?,
    var location: String?,
    var company: String?,
    var userType: String?,
    var gitUrl: String?,
    var biodata: String? = null,
    var avatarUrl: String?,
    var repoUrl: String? = null,
    var repository: Int? = 0,
    var followers: Int? = 0,
    var following: Int? = 0,
) : Parcelable
