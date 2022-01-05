package com.shaun.androidtesting.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.shaun.androidtesting.data.local.dto.NoteDto

@Dao
interface NoteDao {
    @Query("SELECT * from notes")
    suspend fun getNotes(): List<NoteDto>

    @Insert
    suspend fun insertNote(noteDto: NoteDto)
}