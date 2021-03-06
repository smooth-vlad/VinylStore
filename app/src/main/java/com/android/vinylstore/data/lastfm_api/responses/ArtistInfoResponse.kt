package com.android.vinylstore.data.lastfm_api.responses

import com.android.vinylstore.data.lastfm_api.classes.ArtistInfo
import com.google.gson.annotations.SerializedName

class ArtistInfoResponse(@SerializedName("artist") var artist: ArtistInfo)