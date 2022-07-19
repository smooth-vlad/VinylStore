package com.android.vinylstore.data.lastfm_api.classes

import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("#text")
    var path: String,
    @SerializedName("size")
    var size: String
)