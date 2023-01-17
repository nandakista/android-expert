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

    @SerializedName("name")
    var name: String?,

    @SerializedName("location")
    var location: String?,

    @SerializedName("company")
    var company: String?,

    @SerializedName("type")
    var userType: String?,

    @SerializedName("html_url")
    var gitUrl: String?,

    @SerializedName("bio")
    var biodata: String?,

    @SerializedName("avatar_url")
    var avatarUrl: String?,

    @SerializedName("url")
    var repoUrl: String?,

    @SerializedName("public_repos")
    var repository: Int? = 0,

    @SerializedName("followers")
    var followers: Int? = 0,

    @SerializedName("following")
    var following: Int? = 0,
) : Parcelable
