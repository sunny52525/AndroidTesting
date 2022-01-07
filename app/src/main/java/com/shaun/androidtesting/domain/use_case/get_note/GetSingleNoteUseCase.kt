package com.shaun.androidtesting.domain.use_case.get_note

import com.shaun.androidtesting.common.Resource
import com.shaun.androidtesting.data.local.dto.toNotes
import com.shaun.androidtesting.domain.model.NoteItem
import com.shaun.androidtesting.domain.repository.NoteRepository
import javax.inject.Inject

class GetSingleNoteUseCase @Inject constructor(
    val noteRepository: NoteRepository
) {
    suspend operator fun invoke(uid: Long): Resource<NoteItem> {
        return try {
            val result = noteRepository.getNote(uid)
            Resource.Success(result.toNotes())
        } catch (e: Exception) {
            Resource.Error(e.message.toString())
        }
    }
}