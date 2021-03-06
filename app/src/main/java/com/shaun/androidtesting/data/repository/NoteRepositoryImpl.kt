package com.shaun.androidtesting.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.shaun.androidtesting.common.getDate
import com.shaun.androidtesting.data.local.dao.NoteDao
import com.shaun.androidtesting.data.local.dto.NoteDto
import com.shaun.androidtesting.domain.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NoteRepositoryImpl(
    private val noteDao: NoteDao
) : NoteRepository {
    override fun getAllNotes(): LiveData<List<NoteDto>> {
        Log.d(TAG, "getAllNotes: Called")
        val result = noteDao.getNotes()
        result.observeForever {
            Log.d(TAG, "getAllNotes: $it")

        }
        result.value?.forEach {
            Log.d(TAG, "getAllNotes: $it")
        }
        return result
    }

    override suspend fun deleteNote(uid: Long) {
        noteDao.deleteNote(uid)
    }

    override suspend fun editNote(uid: Long, title: String, body: String) {
        noteDao.updateNote(uid = uid, title = title, body = body, lastUpdated = getDate())
    }

    override suspend fun getNote(id: Long): NoteDto {
        return noteDao.getNote(id)
    }

    override suspend fun addNote(noteDto: NoteDto) {
        withContext(Dispatchers.IO) {
            noteDao.insertNote(noteDto)
        }
    }

    companion object {
        private const val TAG = "NoteRepositoryImpl"
    }
}