package com.example.demo.views.db.history

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.demo.views.models.Source
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "history_table")
@Parcelize
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true)
    @Json(name = "id") val id: Int = 0,
    @Json(name = "value") var value: String
): Parcelable