package com.shaun.androidtesting.data.repository

import com.shaun.androidtesting.data.local.dao.NoteDao
import com.shaun.androidtesting.data.local.dto.NoteDto
import com.shaun.androidtesting.domain.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NoteRepositoryImpl(
    private val noteDao: NoteDao
) : NoteRepository {
    override suspend fun getAllNotes(): List<NoteDto> {
        return withContext(Dispatchers.IO) {
            noteDao.getNotes()
        }
    }

    override suspend fun getNote(id: Long): NoteDto {
        TODO("Not yet implemented")
    }

    override suspend fun addNote(noteDto: NoteDto) {
        withContext(Dispatchers.IO) {
            noteDao.insertNote(noteDto)
        }
    }
}