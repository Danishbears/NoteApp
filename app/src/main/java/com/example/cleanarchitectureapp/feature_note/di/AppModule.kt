package com.example.cleanarchitectureapp.feature_note.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cleanarchitectureapp.feature_note.data.data_source.NoteDatabase
import com.example.cleanarchitectureapp.feature_note.data.repository.NoteRepositoryImplementation
import com.example.cleanarchitectureapp.feature_note.domain.repository.NoteRepository
import com.example.cleanarchitectureapp.feature_note.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app:Application): NoteDatabase {
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            NoteDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db:NoteDatabase):NoteRepository{
        return NoteRepositoryImplementation(db.noteDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository:NoteRepository):NoteUseCases{
       return NoteUseCases(
           getNotes = GetNotesUseCase(repository = repository),
           deleteNoteCase = DeleteNoteCase(repository),
           addNoteCase = AddNote(repository),
           getNote = GetNote(repository)
       )
    }

}