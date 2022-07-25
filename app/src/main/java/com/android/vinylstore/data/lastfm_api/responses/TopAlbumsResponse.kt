package com.android.vinylstore.data.lastfm_api.responses

import com.android.vinylstore.data.lastfm_api.classes.Album
import com.google.gson.annotations.SerializedName

data class TopAlbumsResponse(@SerializedName("topalbums") var topAlbums: Albums) {
    data class Albums(
        @SerializedName("album")
        var album: List<Album>,
        @SerializedName("@attr")
        var attr: Attributes
    ) {
        data class Attributes(
            @SerializedName("artist")
            var artist: String,
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