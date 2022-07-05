package com.android.vinylstore.lastfm_api.classes

import com.google.gson.annotations.SerializedName

class Image(
    @SerializedName("#text")
    var path: String,
    @SerializedName("size")
    var size: String
)