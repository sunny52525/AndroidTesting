package com.shaun.androidtesting.domain.use_case.get_note

import androidx.lifecycle.LiveData
import com.shaun.androidtesting.data.local.dto.NoteDto
import com.shaun.androidtesting.domain.repository.NoteRepository
import javax.inject.Inject

class GetNoteUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) {
    operator fun invoke(): LiveData<List<NoteDto>> {

        return noteRepository.getAllNotes()
    }
}