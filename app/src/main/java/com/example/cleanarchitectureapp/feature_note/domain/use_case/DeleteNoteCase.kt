package com.example.cleanarchitectureapp.feature_note.domain.use_case

import com.example.cleanarchitectureapp.feature_note.domain.model.Note
import com.example.cleanarchitectureapp.feature_note.domain.repository.NoteRepository

class DeleteNoteCase(
    private val repository:NoteRepository
) {

    suspend operator fun invoke(note: Note){
        repository.deleteNote(note)
    }

}