package com.android.vinylstore.lastfm_api.responses

import com.android.vinylstore.lastfm_api.classes.AlbumInfo
import com.google.gson.annotations.SerializedName

class AlbumInfoResponse(@SerializedName("album") var album: AlbumInfo)