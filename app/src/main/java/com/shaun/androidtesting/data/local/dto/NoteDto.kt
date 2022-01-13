package com.shaun.androidtesting.data.local.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.shaun.androidtesting.common.getDate
import com.shaun.androidtesting.domain.model.NoteItem

@Entity(tableName = "notes")
data class NoteDto(
    @PrimaryKey(autoGenerate = true) val uid: Long=0L,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "body") val body: String,
    @ColumnInfo(name = "isFav") val isFav: Boolean=false,
    @ColumnInfo(name = "isSynced") val isSynced: Boolean=false,
    @ColumnInfo(name = "updatedAt") val updatedAt: String= getDate()
)


fun NoteDto.toNotes(): NoteItem {
    return NoteItem(
        id = this.uid,
        title = this.title,
        body = this.body,
        lastUpdated = this.updatedAt,
    )
}