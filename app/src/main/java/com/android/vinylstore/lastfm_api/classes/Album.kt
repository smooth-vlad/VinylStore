package com.android.vinylstore.lastfm_api.classes

import com.google.gson.annotations.SerializedName

class Album(
    @SerializedName("name")
    var name: String,
    @SerializedName("playcount")
    var playcount: Int,
    @SerializedName("mbid")
    var mbid: String,
    @SerializedName("url")
    var url: String,
    @SerializedName("artist")
    var artist: Artist,
    @SerializedName("image")
    var image: List<Image>
)