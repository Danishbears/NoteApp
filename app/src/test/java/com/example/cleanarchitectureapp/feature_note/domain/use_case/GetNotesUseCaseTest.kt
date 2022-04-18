package com.example.cleanarchitectureapp.feature_note.domain.use_case

import com.example.cleanarchitectureapp.feature_note.data.repository.NoteRepositoryImplementation
import com.example.cleanarchitectureapp.feature_note.domain.data.repository.FakeNoteRepository
import com.example.cleanarchitectureapp.feature_note.domain.model.Note
import com.example.cleanarchitectureapp.feature_note.domain.util.NoteOrder
import com.example.cleanarchitectureapp.feature_note.domain.util.OrderType
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetNotesUseCaseTest{
    private lateinit var getNotes:GetNotesUseCase
    private lateinit var fakeRepository:FakeNoteRepository

    @Before
    fun setUp(){
        fakeRepository = FakeNoteRepository()
        getNotes = GetNotesUseCase(fakeRepository)

        val notesToInsert = mutableListOf<Note>()
        ('a'..'z').forEachIndexed{
            index, c->
            notesToInsert.add(
                Note(
                    title = c.toString(),
                    content = c.toString(),
                    timestamp = index.toLong(),
                    color = index
                )
            )
        }
        notesToInsert.shuffle()
        runBlocking {
            notesToInsert.forEach { fakeRepository.insertNote(it) }
        }
    }

    @Test
    fun `Order notes by title Descending, correct order`() = runBlocking{
        val notes = getNotes(NoteOrder.Title(OrderType.Descending)).first()

        for(i in 0..notes.size - 2){
            assertThat(notes[i].title).isGreaterThan(notes[i+1].title)
        }
    }

    @Test
    fun `Order notes by date Ascending, correct order`() = runBlocking{
        val notes = getNotes(NoteOrder.Date(OrderType.Ascending)).first()

        for(i in 0..notes.size - 2){
            assertThat(notes[i].timestamp).isLessThan(notes[i+1].timestamp)
        }
    }

    @Test
    fun `Order notes by date Descending, correct order`() = runBlocking{
        val notes = getNotes(NoteOrder.Date(OrderType.Descending)).first()

        for(i in 0..notes.size - 2){
            assertThat(notes[i].timestamp).isGreaterThan(notes[i+1].timestamp)
        }
    }
    @Test
    fun `Order notes by color Ascending, correct order`() = runBlocking{
        val notes = getNotes(NoteOrder.Color(OrderType.Ascending)).first()

        for(i in 0..notes.size - 2){
            assertThat(notes[i].color).isLessThan(notes[i+1].color)
        }
    }
    @Test
    fun `Order notes by color Descending, correct order`() = runBlocking{
        val notes = getNotes(NoteOrder.Title(OrderType.Descending)).first()

        for(i in 0..notes.size - 2){
            assertThat(notes[i].color).isGreaterThan(notes[i+1].color)
        }
    }
    @Test
    fun `Order notes by title Ascending, correct order`() = runBlocking{
        val notes = getNotes(NoteOrder.Title(OrderType.Ascending)).first()

        for(i in 0..notes.size - 2){
            assertThat(notes[i].title).isLessThan(notes[i+1].title)
        }
    }
}