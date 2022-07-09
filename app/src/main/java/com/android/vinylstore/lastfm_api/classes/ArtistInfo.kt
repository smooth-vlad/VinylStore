package com.android.vinylstore.lastfm_api.classes

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import retrofit2.converter.gson.GsonConverterFactory

class ArtistInfo(
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
    class Stats(
        @SerializedName("listeners") var listeners: Int,
        @SerializedName("playcount") var playcount: Int
    )

    class SimilarArtists(
        @SerializedName("artist") var artist: List<ArtistSimilar>
    ) {
        class ArtistSimilar(
            @SerializedName("name") var name: String,
            @SerializedName("url") var url: String,
            @SerializedName("image") var image: List<Image>
        )
    }

    class Tags(
        @SerializedName("tag") var tag: List<Tag>
    )

    class Bio(
        @SerializedName("links") var links: Links,
        @SerializedName("published") var published: String,
        @SerializedName("summary") var summary: String,
        @SerializedName("content") var content: String
    ) {
        class Links(
            @SerializedName("link") var link: Link
        ) {
            class Link(
                @SerializedName("#text") var text: String,
                @SerializedName("rel") var rel: String,
                @SerializedName("href") var href: String
            )
        }
    }
}