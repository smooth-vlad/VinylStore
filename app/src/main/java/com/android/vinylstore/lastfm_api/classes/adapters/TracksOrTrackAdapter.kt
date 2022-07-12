package com.android.vinylstore.lastfm_api.classes.adapters

import com.android.vinylstore.lastfm_api.classes.AlbumInfo
import com.android.vinylstore.lastfm_api.classes.Track
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter

class TracksOrTrackAdapter(private val gson: Gson) : TypeAdapter<List<Track>>() {
    override fun write(jsonWriter: JsonWriter?, value: List<Track>?) {
        TODO("Not yet implemented")
    }

    override fun read(jsonReader: JsonReader): List<Track> {
        return when (jsonReader.peek()) {
            JsonToken.BEGIN_ARRAY -> gson.fromJson(
                jsonReader,
                object : TypeToken<List<Track>>() {}.type
            )
            JsonToken.BEGIN_OBJECT -> listOf(
                gson.fromJson(
                    jsonReader,
                    Track::class.java
                )
            )
            else -> throw com.google.gson.JsonSyntaxException("Expected string or object")
        }
    }

}

class TracksOrTrackAdapterFactory : TypeAdapterFactory {
    override fun <T : Any?> create(gson: Gson?, type: TypeToken<T>?): TypeAdapter<T> {
        return TracksOrTrackAdapter(gson!!) as (TypeAdapter<T>)
    }

}