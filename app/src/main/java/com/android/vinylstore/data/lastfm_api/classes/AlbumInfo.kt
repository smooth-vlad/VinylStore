package com.android.vinylstore.data.lastfm_api.classes

import com.android.vinylstore.data.lastfm_api.classes.adapters.TagsOrEmptyStringAdapterFactory
import com.android.vinylstore.data.lastfm_api.classes.adapters.TagsOrTagAdapterFactory
import com.android.vinylstore.data.lastfm_api.classes.adapters.TracksOrTrackAdapterFactory
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName

data class AlbumInfo(
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

    data class Tracks(
        @JsonAdapter(TracksOrTrackAdapterFactory::class)
        @SerializedName("track") var track: List<Track>
    )

    data class Tags(
        @JsonAdapter(TagsOrTagAdapterFactory::class)
        @SerializedName("tag") var tag: List<Tag>
    ) {
        constructor(string: String) : this(listOf())
    }

    data class Wiki(
        @SerializedName("published") var published: String,
        @SerializedName("summary") var summary: String,
        @SerializedName("content") var content: String
    )
}