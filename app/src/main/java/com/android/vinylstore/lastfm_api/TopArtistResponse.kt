package com.android.vinylstore.lastfm_api

import com.google.gson.annotations.SerializedName

class TopArtistResponse(@SerializedName("artists") var artists: Artists) {
    class Artists(
        @SerializedName("artist")
        var artist: List<Artist>,
        @SerializedName("@attr")
        var attr: Attributes
    ) {
        class Attributes(
            @SerializedName("page")
            var page: Int,
            @SerializedName("perPage")
            var perPage: Int,
            @SerializedName("totalPages")
            var totalPages: Int,
            @SerializedName("total")
            var total: Int
        )
    }
}