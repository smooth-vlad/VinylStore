package com.android.vinylstore.data.lastfm_api.classes

import com.google.gson.annotations.SerializedName

data class ArtistInfo(
    @SerializedName("name") var name: String,
    @SerializedName("mbid") var mbid: String,
    @SerializedName("url") var url: String,
    @SerializedName("image") var image: List<Image>,
    @SerializedName("streamable") var streamable: Boolean,
    @SerializedName("ontour") var onTour: Boolean,
    @SerializedName("stats") var stats: Stats,
    @SerializedName("similar") var similar: SimilarArtists,
    @SerializedName("tags") var tags: Tags,
    @SerializedName("bio") var bio: Bio
) {
    data class Stats(
        @SerializedName("listeners") var listeners: Int,
        @SerializedName("playcount") var playcount: Int
    )

    data class SimilarArtists(
        @SerializedName("artist") var artist: List<ArtistSimilar>
    ) {
        data class ArtistSimilar(
            @SerializedName("name") var name: String,
            @SerializedName("url") var url: String,
            @SerializedName("image") var image: List<Image>
        )
    }

    data class Tags(
        @SerializedName("tag") var tag: List<Tag>
    )

    data class Bio(
        @SerializedName("links") var links: Links,
        @SerializedName("published") var published: String,
        @SerializedName("summary") var summary: String,
        @SerializedName("content") var content: String
    ) {
        data class Links(
            @SerializedName("link") var link: Link
        ) {
            data class Link(
                @SerializedName("#text") var text: String,
                @SerializedName("rel") var rel: String,
                @SerializedName("href") var href: String
            )
        }
    }
}