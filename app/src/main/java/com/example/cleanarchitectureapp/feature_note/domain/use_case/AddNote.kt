package com.example.cleanarchitectureapp.feature_note.domain.use_case

import com.example.cleanarchitectureapp.feature_note.domain.model.InvalidNoteException
import com.example.cleanarchitectureapp.feature_note.domain.model.Note
import com.example.cleanarchitectureapp.feature_note.domain.repository.NoteRepository
import kotlin.jvm.Throws

class AddNote(
 private val repository: NoteRepository
) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note){
        if(note.title.isBlank()){
            throw InvalidNoteException("The title is blank, fill it!")
        }
        if(note.content.isBlank()){
            throw InvalidNoteException("The content is blank, fill it!")
        }
        repository.insertNote(note)
    }
}