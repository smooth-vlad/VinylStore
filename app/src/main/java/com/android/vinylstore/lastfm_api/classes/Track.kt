package com.android.vinylstore.lastfm_api.classes

import com.google.gson.annotations.SerializedName

class Track(
    @SerializedName("streamable") var streamable: Streamable,
    @SerializedName("duration") var duration: Int,
    @SerializedName("url") var url: String,
    @SerializedName("name") var name: String,
    @SerializedName("@attr") var attribute: Attribute,
    @SerializedName("artist") var artist: Artist
) {
    class Streamable(
        @SerializedName("fulltrack") var fullTrack: String,
        @SerializedName("#text") var text: String
    )

    class Attribute(
        @SerializedName("rank") var rank: Int
    )

    class Artist(
        @SerializedName("url") var url: String,
        @SerializedName("name") var name: String,
        @SerializedName("mbid") var mbid: String
    )
}