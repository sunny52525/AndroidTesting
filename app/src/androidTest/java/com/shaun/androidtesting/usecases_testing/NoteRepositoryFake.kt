package com.shaun.androidtesting.usecases_testing

import androidx.lifecycle.LiveData
import com.shaun.androidtesting.common.getDate
import com.shaun.androidtesting.data.local.dao.NoteDao
import com.shaun.androidtesting.data.local.dto.NoteDto
import com.shaun.androidtesting.domain.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NoteRepositoryFake(
    val dao: NoteDao
) : NoteRepository {
    override fun getAllNotes(): LiveData<List<NoteDto>> {
        return dao.getNotes()
    }

    override suspend fun getNote(id: Long): NoteDto {
        return withContext(Dispatchers.Default){
            dao.getNote(id)
        }
    }

    override suspend fun addNote(noteDto: NoteDto) {
       withContext(Dispatchers.Default){
           dao.insertNote(noteDto)
       }
    }

    override suspend fun deleteNote(uid: Long) {
        withContext(Dispatchers.Default){
            dao.deleteNote(uid)
        }
    }

    override suspend fun editNote(uid: Long, title: String, body: String) {
     withContext(Dispatchers.Default){
         dao.updateNote(uid = uid, title = title, body = body, lastUpdated = getDate())
     }
    }
}