package com.android.vinylstore.data.lastfm_api.classes

import com.google.gson.annotations.SerializedName

data class Track(
    @SerializedName("streamable") var streamable: Streamable,
    @SerializedName("duration") var duration: Int,
    @SerializedName("url") var url: String,
    @SerializedName("name") var name: String,
    @SerializedName("@attr") var attribute: Attribute,
    @SerializedName("artist") var artist: Artist
) {
    data class Streamable(
        @SerializedName("fulltrack") var fullTrack: String,
        @SerializedName("#text") var text: String
    )

    data class Attribute(
        @SerializedName("rank") var rank: Int
    )

    data class Artist(
        @SerializedName("url") var url: String,
        @SerializedName("name") var name: String,
        @SerializedName("mbid") var mbid: String
    )
}