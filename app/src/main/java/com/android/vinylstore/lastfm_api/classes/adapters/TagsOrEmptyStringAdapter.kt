package com.android.vinylstore.lastfm_api.classes.adapters

import com.android.vinylstore.lastfm_api.classes.AlbumInfo
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter

class TagsOrEmptyStringAdapter(private val gson: Gson) : TypeAdapter<AlbumInfo.Tags>() {
    override fun write(jsonWriter: JsonWriter?, value: AlbumInfo.Tags?) {
        TODO("Not yet implemented")
    }

    override fun read(jsonReader: JsonReader): AlbumInfo.Tags {
        return when (jsonReader.peek()) {
            JsonToken.STRING -> AlbumInfo.Tags(jsonReader.nextString())
            JsonToken.BEGIN_OBJECT -> gson.fromJson(jsonReader, AlbumInfo.Tags::class.java)
            else -> throw com.google.gson.JsonSyntaxException("Expected string or object")
        }
    }

}

class TagsOrEmptyStringAdapterFactory : TypeAdapterFactory {
    override fun <T : Any?> create(gson: Gson?, type: TypeToken<T>?): TypeAdapter<T> {
        return TagsOrEmptyStringAdapter(gson!!) as (TypeAdapter<T>)
    }

}