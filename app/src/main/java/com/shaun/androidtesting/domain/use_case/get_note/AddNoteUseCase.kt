package com.shaun.androidtesting.domain.use_case.get_note

import com.shaun.androidtesting.common.Resource
import com.shaun.androidtesting.data.local.dto.NoteDto
import com.shaun.androidtesting.domain.repository.NoteRepository
import javax.inject.Inject

class AddNoteUseCase @Inject constructor(
    private val repository: NoteRepository
) {

    suspend operator fun invoke(notes: NoteDto): Resource<String?> {
        return try {
            repository.addNote(notes)
            Resource.Success(null)
        } catch (e: Exception) {
            Resource.Error(e.message.toString())
        }

    }
}