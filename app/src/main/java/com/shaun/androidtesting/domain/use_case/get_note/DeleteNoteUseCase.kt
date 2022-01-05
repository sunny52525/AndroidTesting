package com.shaun.androidtesting.domain.use_case.get_note

import com.shaun.androidtesting.domain.repository.NoteRepository
import javax.inject.Inject

class DeleteNoteUseCase @Inject constructor(
    val repository: NoteRepository
) {

    suspend operator fun invoke(uid:Long){
        repository.deleteNote(uid)
    }
}