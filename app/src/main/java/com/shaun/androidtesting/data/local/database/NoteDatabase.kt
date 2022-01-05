package com.shaun.androidtesting.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shaun.androidtesting.data.local.dao.NoteDao
import com.shaun.androidtesting.data.local.dto.NoteDto

@Database(
    entities = [NoteDto::class],
    version = 1
)
abstract class NoteDatabase:RoomDatabase(){
    abstract fun getDao():NoteDao
}