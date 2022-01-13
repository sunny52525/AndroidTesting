package com.shaun.androidtesting

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.shaun.androidtesting.common.getDate
import com.shaun.androidtesting.data.local.dao.NoteDao
import com.shaun.androidtesting.data.local.database.NoteDatabase
import com.shaun.androidtesting.data.local.dto.NoteDto
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named


@HiltAndroidTest
class DatabaseTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    @Inject
    @Named("test_db")
    lateinit var database: NoteDatabase
    private lateinit var noteDao: NoteDao

    @Before
    fun setup() {
        hiltRule.inject()
        noteDao = database.getDao()
    }

    @After
    fun tearDown() {
        database.close()
    }
    @Test
     fun insertNote()= runBlockingTest{
         val note = NoteDto(
             title = "Note#1",
             body = "Body#2",
             uid = 2
         )
        noteDao.insertNote(note)
        val allNotes = noteDao.getNotes().getOrAwaitValue()
        assert(allNotes.contains(note))

     }

    @Test
    fun deleteNote()= runBlocking {
        val note = NoteDto(
            title = "Note#1",
            body = "Body#2",
            uid = 3
        )
        noteDao.insertNote(note)

        noteDao.deleteNote(3)
        val allNotes = noteDao.getNotes().getOrAwaitValue()

        assert(allNotes.contains(note).not())

    }
    @Test
    fun getAllNotesTest() = runBlockingTest {
        val notes = (1..5).map {
            NoteDto(
                title = "Note#1",
                body = "Body#2",
                uid = it.toLong()
            )
        }
        notes.forEach {
            noteDao.insertNote(it)
        }
        val allNotes = noteDao.getNotes().getOrAwaitValue()

        assert(
            notes.containsAll(allNotes)
        )

    }

    @Test
    fun getNote() = runBlocking {
        val note = NoteDto(
            title = "Note#1",
            body = "Body#2",
            uid = 3
        )
        noteDao.insertNote(note)

        val newNote = noteDao.getNote(note.uid)

        assert(note == newNote)
    }

    @Test
    fun editNote() = runBlocking {
        val note = NoteDto(
            title = "Note#1",
            body = "Body#2",
            uid = 3,
            updatedAt = getDate()
        )
        noteDao.insertNote(note)

        val newNote = note.copy(
            title = "Edited Note",
            body = "Edited Body",

        )

        noteDao.updateNote(uid = note.uid, title = newNote.title, body = newNote.body, lastUpdated = newNote.updatedAt)

        val newNoteFromDB = noteDao.getNote(uid = note.uid)

        assert(newNoteFromDB == newNote)
        assert(newNoteFromDB != note)
    }

}