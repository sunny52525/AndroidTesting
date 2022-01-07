package com.shaun.androidtesting.usecases_testing

import androidx.lifecycle.LiveData
import com.shaun.androidtesting.data.local.dao.NoteDao
import com.shaun.androidtesting.data.local.dto.NoteDto
import com.shaun.androidtesting.domain.repository.NoteRepository

class NoteRepositoryFake(
    val dao: NoteDao
) : NoteRepository {
    override fun getAllNotes(): LiveData<List<NoteDto>> {
        return dao.getNotes()
    }

    override suspend fun getNote(id: Long): NoteDto {
     TODO()
    }

    override suspend fun addNote(noteDto: NoteDto) {
        dao.insertNote(noteDto)
    }

    override suspend fun deleteNote(uid: Long) {
         dao.deleteNote(uid)
    }
}