package com.shaun.androidtesting.presentation.note_list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shaun.androidtesting.data.local.dto.NoteDto
import com.shaun.androidtesting.domain.use_case.get_note.DeleteNoteUseCase
import com.shaun.androidtesting.domain.use_case.get_note.GetNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val getNoteUseCase: GetNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase
) : ViewModel() {


    private var _allNotes: LiveData<List<NoteDto>>? = null
    val allNotes get() = _allNotes

    private val _showErrorMessage = MutableLiveData<Boolean>(false)
    val showErrorMessage = _showErrorMessage

    private var _errorMessage:String? = ""
    val errorMessage get() = _errorMessage


    init {
        getAllNotes()
    }
    private fun getAllNotes() {
        try {
            _allNotes = getNoteUseCase()
        } catch (e: Exception) {
            Log.d(TAG, "getAllNotes: ${e.message}")
            setErrorState(e.message)

        }
    }



    fun deleteNote(uid: Long) {
        viewModelScope.launch {
            try {
                deleteNoteUseCase(uid)
            } catch (e: Exception) {
                setErrorState(e.message)
            }

        }
    }

    private fun setErrorState(message: String?) {
        _errorMessage = message
        _showErrorMessage.value = true
    }

    private fun clearErrorState() {
        _showErrorMessage.value = false
    }

    override fun onCleared() {
        super.onCleared()
        clearErrorState()

    }

    companion object {
        private const val TAG = "NoteListViewModel"
    }
}