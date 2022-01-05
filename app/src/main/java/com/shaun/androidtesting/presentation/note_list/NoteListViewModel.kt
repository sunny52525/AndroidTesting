package com.shaun.androidtesting.presentation.note_list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shaun.androidtesting.common.Resource
import com.shaun.androidtesting.data.local.dto.NoteDto
import com.shaun.androidtesting.domain.model.NoteItem
import com.shaun.androidtesting.domain.use_case.get_note.AddNoteUseCase
import com.shaun.androidtesting.domain.use_case.get_note.GetNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val getNoteUseCase: GetNoteUseCase,
    private val addNoteUseCase: AddNoteUseCase
) : ViewModel() {


    private val _allNotes = MutableLiveData<Resource<List<NoteItem>>?>(Resource.Idle())
    val allNotes = _allNotes

    private val _showErrorMessage = MutableLiveData<Boolean>(false)
    val showErrorMessage = _showErrorMessage

    private var _errorMessage = ""
    val errorMessage get() = _errorMessage


    init {
        getAllNotes()
    }
    private fun getAllNotes() {
        _allNotes.value = Resource.Loading()
        viewModelScope.launch {
            _allNotes.value = getNoteUseCase()
        }
    }


    fun addNote(title: String, body: String) {
        viewModelScope.launch {
            val note = NoteDto(
                title = title,
                body = body,
            )

            when (val result = addNoteUseCase(notes = note)) {
                is Resource.Success -> {

                }
                is Resource.Error -> {
                    result.message?.let { setErrorState(it) }
                }
                else -> {

                }
            }


        }
    }

    private fun setErrorState(message: String) {
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