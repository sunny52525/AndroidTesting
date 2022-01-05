package com.shaun.androidtesting.di

import android.content.Context
import androidx.room.Room
import com.shaun.androidtesting.data.local.dao.NoteDao
import com.shaun.androidtesting.data.local.database.NoteDatabase
import com.shaun.androidtesting.data.repository.NoteRepositoryImpl
import com.shaun.androidtesting.domain.use_case.get_note.GetNoteUseCase
import com.shaun.androidtesting.domain.repository.NoteRepository
import com.shaun.androidtesting.domain.use_case.get_note.AddNoteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesDatabase(
        @ApplicationContext context: Context
    ): NoteDatabase = Room.databaseBuilder(context, NoteDatabase::class.java, "note_db").build()


    @Provides
    @Singleton
    fun provideNoteDao(
        db: NoteDatabase
    ): NoteDao = db.getDao()

    @Provides
    @Singleton
    fun provideNoteRepository(
        dao: NoteDao
    ): NoteRepository = NoteRepositoryImpl(dao)


}