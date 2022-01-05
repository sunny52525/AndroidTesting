package com.shaun.androidtesting.presentation.note_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shaun.androidtesting.data.local.dto.NoteDto
import com.shaun.androidtesting.data.local.dto.toNotes
import com.shaun.androidtesting.domain.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val repository: NoteRepository
) : ViewModel() {


    fun getAllNotes() {
        viewModelScope.launch {
            val notes = repository.getAllNotes()

            notes.forEach {
                print(it.toNotes())
            }
        }
    }

    fun addNote() {
        viewModelScope.launch {
            val note = NoteDto(
                title = "Hello",
                body = "WOrld",

                )


            try {
                repository.addNote(noteDto = note)
                Log.d(TAG, "addNote: notes added")
            } catch (e: Exception) {
                Log.d(TAG, "addNote: ${e.message}")
            }

        }
    }
    companion object{
        private const val TAG = "NoteListViewModel"
    }
}