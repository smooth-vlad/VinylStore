package com.android.vinylstore.data.lastfm_api.responses

import com.android.vinylstore.data.lastfm_api.classes.Artist
import com.google.gson.annotations.SerializedName

class TopArtistResponse(@SerializedName("artists") var artists: Artists) {
    data class Artists(
        @SerializedName("artist")
        var artist: List<Artist>,
        @SerializedName("@attr")
        var attr: Attributes
    ) {
        data class Attributes(
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