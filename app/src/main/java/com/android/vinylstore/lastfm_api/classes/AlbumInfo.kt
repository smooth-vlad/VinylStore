package com.android.vinylstore.lastfm_api.classes

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter

class AlbumInfo(
    @SerializedName("artist") var artist: String,
    @SerializedName("mbid") var mbid: String,
    @JsonAdapter(TagsAdapterFactory::class)
    @SerializedName("tags") var tags: Tags?,
    @SerializedName("playcount") var playcount: Int,
    @SerializedName("image") var image: List<Image>,
    @JsonAdapter(TracksAdapterFactory::class)
    @SerializedName("tracks") var tracks: Tracks?,
    @SerializedName("url") var url: String,
    @SerializedName("name") var name: String,
    @SerializedName("listeners") var listeners: Int,
    @SerializedName("wiki") var wiki: Wiki?
) {

    class Tracks(
        @SerializedName("track") var track: List<Track>
    )

    class Tags(
        @SerializedName("tag") var tag: List<Tag>
    ) {
        constructor(string: String) : this(listOf()) {
        }
    }

    class Wiki(
        @SerializedName("published") var published: String,
        @SerializedName("summary") var summary: String,
        @SerializedName("content") var content: String
    )

    class TagsAdapterFactory : TypeAdapterFactory {
        override fun <T : Any?> create(gson: Gson?, type: TypeToken<T>?): TypeAdapter<T> {
            return TagsAdapter(gson!!) as (TypeAdapter<T>)
        }

    }

    class TagsAdapter(private val gson: Gson) : TypeAdapter<Tags>() {
        override fun write(jsonWriter: JsonWriter?, value: Tags?) {
            TODO("Not yet implemented")
        }

        override fun read(jsonReader: JsonReader): Tags {
            return when (jsonReader.peek()) {
                JsonToken.STRING -> Tags(jsonReader.nextString())
                JsonToken.BEGIN_OBJECT -> gson.fromJson(jsonReader, Tags::class.java)
                else -> throw com.google.gson.JsonSyntaxException("Expected string or object")
            }
        }

    }


    class TracksAdapterFactory : TypeAdapterFactory {
        override fun <T : Any?> create(gson: Gson?, type: TypeToken<T>?): TypeAdapter<T> {
            return TracksAdapter(gson!!) as (TypeAdapter<T>)
        }

    }

    class TracksAdapter(private val gson: Gson) : TypeAdapter<Tracks>() {
        override fun write(jsonWriter: JsonWriter?, value: Tracks?) {
            TODO("Not yet implemented")
        }

        override fun read(jsonReader: JsonReader): Tracks {
            return when (jsonReader.peek()) {
                JsonToken.BEGIN_ARRAY -> gson.fromJson(jsonReader, Tracks::class.java)
                JsonToken.BEGIN_OBJECT -> Tracks(listOf(gson.fromJson(jsonReader, Track::class.java)))
                else -> throw com.google.gson.JsonSyntaxException("Expected string or object")
            }
        }

    }
}