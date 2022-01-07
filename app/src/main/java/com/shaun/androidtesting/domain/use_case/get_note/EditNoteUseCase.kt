package com.shaun.androidtesting.domain.use_case.get_note

import com.shaun.androidtesting.domain.repository.NoteRepository
import javax.inject.Inject

class EditNoteUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) {

    suspend operator fun invoke(noteId: Long, title: String, body: String) {
        noteRepository.editNote(uid = noteId, title = title, body = body)
    }
}