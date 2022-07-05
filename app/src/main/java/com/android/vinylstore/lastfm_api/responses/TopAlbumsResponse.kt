package com.android.vinylstore.lastfm_api.responses

import com.android.vinylstore.lastfm_api.classes.Album
import com.google.gson.annotations.SerializedName

class TopAlbumsResponse(@SerializedName("topalbums") var topAlbums: Albums) {
    class Albums(
        @SerializedName("album")
        var album: List<Album>,
        @SerializedName("@attr")
        var attr: Attributes
    ) {
        class Attributes(
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