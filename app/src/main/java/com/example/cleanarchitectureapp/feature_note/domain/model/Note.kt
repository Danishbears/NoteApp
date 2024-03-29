package com.example.cleanarchitectureapp.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.cleanarchitectureapp.ui.theme.*
import java.lang.Exception

@Entity
data class Note(
    val title:String,
    val content:String,
    val timestamp:Long,
    val color:Int,
    @PrimaryKey val id:Int? = null
){
    companion object{
        val noteColors = listOf(RedOrange, LightGreen, BabyBlue, RedPink, Violet)
    }
}


class InvalidNoteException(message:String):Exception(message)