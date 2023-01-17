package com.dicoding.core.data.sources.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserResponse(
    @SerializedName("id")
    var id: Int,

    @SerializedName("login")
    var username: String,

    var name: String?,
    var location: String?,
    var company: String?,

    @SerializedName("type")
    var userType: String?,

    @SerializedName("html_url")
    var gitUrl: String?,

    @SerializedName("bio")
    var biodata: String? = null,

    @SerializedName("avatar_url")
    var avatarUrl: String?,

    @SerializedName("url")
    var repoUrl: String? = null,

    @SerializedName("public_repos")
    var repository: Int? = 0,

    var followers: Int? = 0,
    var following: Int? = 0,
) : Parcelable
