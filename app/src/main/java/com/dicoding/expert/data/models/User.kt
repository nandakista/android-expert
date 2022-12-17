package com.dicoding.expert.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.io.Serializable

data class SearchResponse(
    @SerializedName("total_count")
    var totalCount: Int,

    @SerializedName("incomplete_results")
    var incompleteResult: Boolean,

    @SerializedName("items")
    var data : List<User>,
)

@Parcelize
data class User(
    @SerializedName("id")
    var userId: Int? = null,

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
