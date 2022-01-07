package com.shaun.androidtesting

import android.content.Context
import androidx.room.Room
import com.shaun.androidtesting.data.local.dao.NoteDao
import com.shaun.androidtesting.data.local.database.NoteDatabase
import com.shaun.androidtesting.domain.repository.NoteRepository
import com.shaun.androidtesting.usecases_testing.NoteRepositoryFake
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestModule {

    @Provides
    @Named("test_db")
    fun provideInMemoryDb(@ApplicationContext context: Context): NoteDatabase =
        Room.inMemoryDatabaseBuilder(
            context, NoteDatabase::class.java,
        ).allowMainThreadQueries()
            .build()

    @Provides
    @Singleton
    @Named("fake_dao")
    fun provideNoteDao(
        @Named("test_db")
        db: NoteDatabase
    ): NoteDao = db.getDao()

    @Provides
    @Named("fake_repo")
    fun provideFakeRepository(
        @Named("fake_dao")
        dao: NoteDao
    ): NoteRepository = NoteRepositoryFake(dao)
}