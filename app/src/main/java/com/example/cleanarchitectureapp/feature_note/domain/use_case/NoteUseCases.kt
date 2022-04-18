package com.example.cleanarchitectureapp.feature_note.domain.use_case

data class NoteUseCases(
    val getNotes:GetNotesUseCase,
    val deleteNoteCase: DeleteNoteCase,
    val addNoteCase:AddNote,
    val getNote:GetNote
)
