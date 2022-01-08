package com.shaun.androidtesting.presentation.note_detail

import androidx.lifecycle.*
import com.shaun.androidtesting.common.Resource
import com.shaun.androidtesting.common.Status
import com.shaun.androidtesting.data.local.dto.NoteDto
import com.shaun.androidtesting.domain.model.NoteItem
import com.shaun.androidtesting.domain.use_case.get_note.AddNoteUseCase
import com.shaun.androidtesting.domain.use_case.get_note.EditNoteUseCase
import com.shaun.androidtesting.domain.use_case.get_note.GetSingleNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class NoteEditViewModel @Inject constructor(
    val addNoteUseCase: AddNoteUseCase,
    val getSingleNoteUseCase: GetSingleNoteUseCase,
    val editNoteUseCase: EditNoteUseCase
) : ViewModel() {
    private val _showErrorMessage = MutableLiveData<Boolean>(false)
    val showErrorMessage = _showErrorMessage


    val id: MutableLiveData<Long?> = MutableLiveData(null)

    val note = Transformations.switchMap(id) {
        it?.let {
            liveData<Resource<NoteItem>> {

                val result = getSingleNoteUseCase(it)
                withContext(Dispatchers.Main) {
                    emit(result)
                }
            }.distinctUntilChanged()
        }

    }


    fun setId(uid: Long) {


        id.value = uid
    }


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

    fun editNote(title: String, body: String, noteId: Long) {
        noteAddStatus.value = Resource.Loading()
        viewModelScope.launch {
            try {
                editNoteUseCase(title = title, body = body, noteId = noteId)
                noteAddStatus.value = Resource.Success(Status(success = true))
            } catch (e: Exception) {
                noteAddStatus.value = Resource.Error(e.message.toString())

            }
        }
    }


    companion object {
        private const val TAG = "NoteEditViewModel"
    }
}