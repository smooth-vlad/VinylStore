package com.android.vinylstore.lastfm_api.classes

import com.google.gson.annotations.SerializedName

class AlbumInfo(
    @SerializedName("artist") var artist: String,
    @SerializedName("mbid") var mbid: String,
    @SerializedName("tags") var tags: Tags,
    @SerializedName("playcount") var playcount: Int,
    @SerializedName("image") var image: List<Image>,
    @SerializedName("tracks") var tracks: Tracks?,
    @SerializedName("url") var url: String,
    @SerializedName("name") var name: String,
    @SerializedName("listeners") var listeners: Int,
    @SerializedName("wiki") var wiki: Wiki
) {

    class Tracks(
        @SerializedName("track") var track: List<Track>
    )

    class Tags(
        @SerializedName("tag") var tag: List<Tag>
    )

    class Wiki(
        @SerializedName("published") var published: String,
        @SerializedName("summary") var summary: String,
        @SerializedName("content") var content: String
    )
}