package com.shaun.androidtesting.domain.use_case.get_note

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.shaun.androidtesting.common.Resource
import com.shaun.androidtesting.common.equal
import com.shaun.androidtesting.data.local.dao.NoteDao
import com.shaun.androidtesting.data.local.database.NoteDatabase
import com.shaun.androidtesting.data.local.dto.NoteDto
import com.shaun.androidtesting.data.local.dto.toNotes
import com.shaun.androidtesting.domain.repository.NoteRepository
import com.shaun.androidtesting.getOrAwaitValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@HiltAndroidTest
class UseCasesTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var database: NoteDatabase

    @Inject
    @Named("fake_dao")
    lateinit var noteDao: NoteDao

    @Inject
    @Named("fake_repo")
    lateinit var repository: NoteRepository
    private lateinit var noteUseCase: AddNoteUseCase
    private lateinit var deleteNoteUseCase: DeleteNoteUseCase
    private lateinit var getNoteUseTestCase: GetNoteUseCase
    private lateinit var editNoteUseCaseTest: EditNoteUseCase
    private lateinit var getSingleNoteUseCase: GetSingleNoteUseCase

    @Before
    fun setup() {
        hiltRule.inject()
        noteUseCase = AddNoteUseCase(repository)
        deleteNoteUseCase = DeleteNoteUseCase(repository)
        getNoteUseTestCase = GetNoteUseCase(repository)
        editNoteUseCaseTest = EditNoteUseCase(repository)
        getSingleNoteUseCase = GetSingleNoteUseCase(repository)
    }

    @After
    fun tearDown() {
        database.close()
    }


    @Test
    fun testUseCaseSuccessAdd() = runBlocking {

        val note = NoteDto(
            title = "Note Use Case",
            body = "Note body",
            uid = 2
        )
        val addNote = noteUseCase(note)
        val expectedResult = Resource.Success<String?>(null)

        assert(addNote.equal(expectedResult))
        val allNotes = noteDao.getNotes().getOrAwaitValue()

        assert(allNotes.contains(note))

    }

    @Test
    fun deleteNoteUseCase() = runBlocking {
        val noteDto = NoteDto(
            title = "New note",
            body = "new body",
            uid = 10
        )

        noteUseCase(noteDto)

        deleteNoteUseCase(noteDto.uid)

        val allNotes = noteDao.getNotes().getOrAwaitValue()

        assert(allNotes.contains(noteDto).not())


    }


    @Test
    fun getNotesUseCaseTest() = runBlocking {

        val notes = (1..10).map {
            val note = NoteDto(
                title = "note $it",
                body = "note body $it",
                uid = it.toLong()
            ).also { note ->
                noteUseCase(note)
            }
            note
        }
        val allNotes = noteDao.getNotes().getOrAwaitValue()

        assert(
            notes.containsAll(allNotes)
        )


    }


    @Test
    fun editUseCaseTest() = runBlocking {
        val noteDto = NoteDto(
            title = "New note",
            body = "new body",
            uid = 40
        )

        noteDao.insertNote(noteDto)

        val newNote = noteDto.copy(
            title = "Edited Note",
            body = "Edited Body"
        )


        editNoteUseCaseTest(
            noteId = noteDto.uid, title = newNote.title, body = newNote.body
        )

        val newNoteFromDB = noteDao.getNotes().getOrAwaitValue().firstOrNull {
            noteDto.uid == it.uid
        }

        assert(newNoteFromDB != null)
        assert(newNoteFromDB == newNote)
        assert(newNoteFromDB != noteDto)

    }

    @Test
    fun getSingleNoteUse() = runBlocking {
        val noteDto = NoteDto(
            title = "New note",
            body = "new body",
            uid = 40
        )

        noteDao.insertNote(noteDto)

        val noteFromDB = getSingleNoteUseCase(noteDto.uid)

        assert(noteFromDB is Resource.Success)
        assert(noteFromDB.data == noteDto.toNotes())


    }

}