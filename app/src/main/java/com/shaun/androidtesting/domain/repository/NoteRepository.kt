package com.shaun.androidtesting.domain.repository

import androidx.lifecycle.LiveData
import com.shaun.androidtesting.data.local.dto.NoteDto

interface NoteRepository {
    fun getAllNotes(): LiveData<List<NoteDto>>
    suspend fun getNote(id: Long): NoteDto
    suspend fun addNote(noteDto: NoteDto)
    suspend fun deleteNote(uid:Long)
}