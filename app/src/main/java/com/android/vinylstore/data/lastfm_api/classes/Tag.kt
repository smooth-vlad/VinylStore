package com.android.vinylstore.data.lastfm_api.classes

import com.google.gson.annotations.SerializedName

data class Tag(
    @SerializedName("name") var name: String,
    @SerializedName("url") var url: String
)