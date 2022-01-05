package com.shaun.androidtesting.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.shaun.androidtesting.data.local.dto.NoteDto

@Dao
interface NoteDao {
    @Query("SELECT * from notes order by uid desc")
    fun getNotes():LiveData<List<NoteDto>>

    @Insert
    suspend fun insertNote(noteDto: NoteDto)

    @Query("Delete from notes where uid=:uid")
    suspend fun deleteNote(uid:Long)
}