package com.example.cleanarchitectureapp

import AddEditNoteScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cleanarchitectureapp.feature_note.presentation.notes.components.NotesScreen
import com.example.cleanarchitectureapp.feature_note.presentation.util.Screen
import com.example.cleanarchitectureapp.ui.theme.CleanArchitectureAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CleanArchitectureAppTheme {
                Surface(
                    color = MaterialTheme.colors.background
                ){
                    val navController = rememberNavController()
                    NavHost(navController = navController,
                        startDestination = Screen.NotesScreen.route){
                        composable(Screen.NotesScreen.route){
                            NotesScreen(navController = navController)
                        }
                        composable(Screen.AddEditNoteScreen.route +
                                "?noteId={noteId}&noteColor={noteColor}",
                        listOf(
                            navArgument(
                                name = "noteId"
                            ){
                                type = NavType.IntType
                                defaultValue =-1
                            },
                            navArgument(
                                name = "noteColor"
                            ){
                                type = NavType.IntType
                                defaultValue =-1
                            }
                        )
                        ){
                            val color = it.arguments?.getInt("noteColor") ?:-1
                            AddEditNoteScreen(
                                navController = navController, noteColor = color)

                        }
                    }
                }
            }
        }
    }
}
