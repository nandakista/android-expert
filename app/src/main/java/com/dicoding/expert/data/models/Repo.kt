package com.dicoding.expert.data.models

import com.google.gson.annotations.SerializedName

data class Repo(
    @SerializedName("full_name")
    var name: String,

    @SerializedName("owner")
    var repoAuthor: User,

    @SerializedName("description")
    var desc: String?,

    @SerializedName("language")
    var progLang: String?,

    @SerializedName("watchers_count")
    var totalWatch: Int?,

    @SerializedName("forks_count")
    var totalFork: Int?,

    @SerializedName("stargazers_count")
    var totalStar: Int?,
)
