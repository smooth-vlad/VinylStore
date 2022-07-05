package com.android.vinylstore.lastfm_api.classes

import com.google.gson.annotations.SerializedName

class Artist(
    @SerializedName("name")
    var name: String,
    @SerializedName("listeners")
    var listeners: Int,
    @SerializedName("mbid")
    var mbid: String,
    @SerializedName("url")
    var url: String,
    @SerializedName("streamable")
    var streamable: Boolean,
    @SerializedName("image")
    var image: List<Image>
) {
}