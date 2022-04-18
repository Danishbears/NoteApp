package com.example.cleanarchitectureapp.feature_note.domain.use_case

import com.example.cleanarchitectureapp.feature_note.domain.data.repository.FakeNoteRepository
import com.example.cleanarchitectureapp.feature_note.domain.model.InvalidNoteException
import com.example.cleanarchitectureapp.feature_note.domain.model.Note
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.hamcrest.core.IsEqual
import org.hamcrest.core.StringContains
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import java.lang.Exception

class AddNoteTest{
    private lateinit var addNotes:AddNote
    private lateinit var fakeRepository: FakeNoteRepository
    private lateinit var note: Note
    val expectedException  = ExpectedException.none()

    @Before
    fun setUp(){
        fakeRepository = FakeNoteRepository()
        addNotes = AddNote(fakeRepository)
        note = Note(
            title ="adad",
            content = "adada",
            timestamp = 123L,
            color = 12
        )
        expectedException.expect(InvalidNoteException::class.java)
        expectedException.expectCause(IsEqual.equalTo(""))
        expectedException.expectMessage("The title is blank, fill it!")
        runBlocking {
            note.apply { fakeRepository.insertNote(this) }
        }
    }

  //@Rule
  /*  fun exception(){
        expectedException.expect(InvalidNoteException::class.java)
        expectedException.expectCause(IsEqual.equalTo(""))
        expectedException.expectMessage("The title is blank, fill it!")
    }*/

    @Test(expected = InvalidNoteException::class)
    fun `check is title blank or not, exception message`() = runBlocking{
        //addNotes(note)
        addNotes.invoke(note)
        //assertThat(note.title).isEqualTo("")
        }
    @Test
    fun `check core`() = runBlocking {
        addNotes.invoke(note)
    }
}