package com.example.demo.views.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Root(
    @Json(name = "status") val status: String?,
    @Json(name = "articles") val articles: List<Articles>?
): Parcelable

@Entity(tableName = "articles_table")
@Parcelize
data class Articles(
    @Embedded
    @Json(name = "source") val source: Source?,
    @PrimaryKey
    @Json(name = "title") val title: String,
    @Json(name = "isBookmarked") var isBookmarked: Boolean = false,
    @Json(name = "urlToImage") val urlToImage: String?,
    @Json(name = "description") val description: String?,
    @Json(name = "url") val url: String?,
    @Json(name = "publishedAt") val publishedAt: String?,
    @Json(name = "content") val content: String?
    ): Parcelable

@Parcelize
data class Source(
    @PrimaryKey(autoGenerate = false)
    @Json(name = "id") val id: String?,
    @Json(name = "name") val name: String?
): Parcelable




