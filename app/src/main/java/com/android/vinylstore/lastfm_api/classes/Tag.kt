package com.android.vinylstore.lastfm_api.classes

import com.google.gson.annotations.SerializedName

class Tag(
    @SerializedName("name") var name: String,
    @SerializedName("url") var url: String
)