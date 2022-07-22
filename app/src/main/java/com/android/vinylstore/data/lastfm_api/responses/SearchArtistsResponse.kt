package com.android.vinylstore.data.lastfm_api.responses

import com.android.vinylstore.data.lastfm_api.classes.Artist
import com.google.gson.annotations.SerializedName

data class SearchArtistsResponse(@SerializedName("results") var results: Results) {
    data class Results(
        @SerializedName("opensearch:Query") var searchQuery: SearchQuery,
        @SerializedName("opensearch:totalResults") var totalResults: Int,
        @SerializedName("opensearch:startIndex") var startIndex: Int,
        @SerializedName("opensearch:itemsPerPage") var itemsPerPage: Int,
        @SerializedName("artistmatches") var artistsMatches: ArtistsMatches,
        @SerializedName("@attr") var attribute: Attribute,
    ) {
        data class SearchQuery(
            @SerializedName("#text") var text: String,
            @SerializedName("role") var role: String,
            @SerializedName("searchTerms") var searchTerms: String,
            @SerializedName("startPage") var startPage: Int,
        )

        data class ArtistsMatches(
            @SerializedName("artist") var artists: List<Artist>,
        )

        data class Attribute(
            @SerializedName("for") var query: String,
        )
    }
}