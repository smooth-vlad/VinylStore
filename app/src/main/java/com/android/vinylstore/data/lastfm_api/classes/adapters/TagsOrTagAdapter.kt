package com.android.vinylstore.data.lastfm_api.classes.adapters

import com.android.vinylstore.data.lastfm_api.classes.Tag
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter

class TagsOrTagAdapter(private val gson: Gson) : TypeAdapter<List<Tag>>() {

    override fun read(jsonReader: JsonReader): List<Tag> {
        return when (jsonReader.peek()) {
            JsonToken.BEGIN_ARRAY -> gson.fromJson(
                jsonReader,
                object : TypeToken<List<Tag>>() {}.type
            )
            JsonToken.BEGIN_OBJECT -> listOf(
                gson.fromJson(
                    jsonReader,
                    Tag::class.java
                )
            )
            else -> throw com.google.gson.JsonSyntaxException("Expected string or object")
        }
    }

    override fun write(out: JsonWriter?, value: List<Tag>?) {
        TODO("Not yet implemented")
    }

}

class TagsOrTagAdapterFactory : TypeAdapterFactory {
    override fun <T : Any?> create(gson: Gson?, type: TypeToken<T>?): TypeAdapter<T> {
        return TagsOrTagAdapter(gson!!) as (TypeAdapter<T>)
    }

}