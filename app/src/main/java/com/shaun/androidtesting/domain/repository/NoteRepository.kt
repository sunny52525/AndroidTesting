package com.shaun.androidtesting.domain.repository

import com.shaun.androidtesting.data.local.dto.NoteDto

interface NoteRepository {
    suspend fun getAllNotes(): List<NoteDto>
    suspend fun getNote(id: Long): NoteDto
    suspend fun addNote(noteDto: NoteDto)
}