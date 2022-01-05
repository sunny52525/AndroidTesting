package com.shaun.androidtesting.domain.use_case.get_note

import com.shaun.androidtesting.common.Resource
import com.shaun.androidtesting.data.local.dto.toNotes
import com.shaun.androidtesting.domain.model.NoteItem
import com.shaun.androidtesting.domain.repository.NoteRepository
import javax.inject.Inject

class GetNoteUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) {
    suspend operator fun invoke(): Resource<List<NoteItem>> {
        return try {
            val result = noteRepository.getAllNotes().map { it.toNotes() }
            Resource.Success(result)
        } catch (e: Exception) {
            e.message?.let {
                Resource.Error(it)
            } ?: kotlin.run {
                Resource.Error("Some Unknown error occurred")
            }
        }
    }
}