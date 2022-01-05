package com.shaun.androidtesting.presentation.note_detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shaun.androidtesting.common.Resource
import com.shaun.androidtesting.common.Status
import com.shaun.androidtesting.data.local.dto.NoteDto
import com.shaun.androidtesting.domain.use_case.get_note.AddNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteEditViewModel @Inject constructor(
    val addNoteUseCase: AddNoteUseCase
) : ViewModel() {
    private val _showErrorMessage = MutableLiveData<Boolean>(false)
    val showErrorMessage = _showErrorMessage

    val noteAddStatus = MutableLiveData<Resource<Status>>(Resource.Idle())
    private var _errorMessage: String? = ""
    val errorMessage get() = _errorMessage
    fun addNote(title: String, body: String) {
        viewModelScope.launch {
            val note = NoteDto(
                title = title,
                body = body,
            )

            when (val result = addNoteUseCase(notes = note)) {
                is Resource.Success -> {
                    noteAddStatus.value=Resource.Success(Status(success = true))
                }
                is Resource.Error -> {
                    noteAddStatus.value=Resource.Error(result.message.toString())
                }
                else -> {

                }
            }


        }
    }




    companion object {
        private const val TAG = "NoteEditViewModel"
    }
}