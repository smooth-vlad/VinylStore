package com.android.vinylstore.lastfm_api.classes

import com.android.vinylstore.lastfm_api.classes.adapters.*
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName

class AlbumInfo(
    @SerializedName("artist") var artist: String,
    @SerializedName("mbid") var mbid: String,
    @JsonAdapter(TagsOrEmptyStringAdapterFactory::class)
    @SerializedName("tags") var tags: Tags?,
    @SerializedName("playcount") var playcount: Int,
    @SerializedName("image") var image: List<Image>,
    @SerializedName("tracks") var tracks: Tracks?,
    @SerializedName("url") var url: String,
    @SerializedName("name") var name: String,
    @SerializedName("listeners") var listeners: Int,
    @SerializedName("wiki") var wiki: Wiki?
) {

    class Tracks(
        @JsonAdapter(TracksOrTrackAdapterFactory::class)
        @SerializedName("track") var track: TracksInner
    ) {
        class TracksInner(
            var tracks: List<Track>
        )
    }

    class Tags(
        @JsonAdapter(TagsOrTagAdapterFactory::class)
        @SerializedName("tag") var tag: List<Tag>
    ) {
        constructor(string: String) : this(listOf())
    }

    class Wiki(
        @SerializedName("published") var published: String,
        @SerializedName("summary") var summary: String,
        @SerializedName("content") var content: String
    )
}