package com.example.cleanarchitectureapp.feature_note.presentation.notes.components

import AddEditNoteScreen
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cleanarchitectureapp.MainActivity
import com.example.cleanarchitectureapp.core.util.TestTags
import com.example.cleanarchitectureapp.feature_note.di.AppModule
import com.example.cleanarchitectureapp.feature_note.presentation.util.Screen
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class NotesEndToEndTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @ExperimentalAnimationApi
    @Before
    fun setUp(){
        hiltRule.inject()
        composeRule.setContent {
            val navController = rememberNavController()
            NavHost(navController = navController,
                startDestination = Screen.NotesScreen.route){
                composable(Screen.NotesScreen.route){
                    NotesScreen(navController = navController)
                }
                composable(
                    Screen.AddEditNoteScreen.route +
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

    @Test
    fun saveNewNote_editAfterwards(){
        //Click on FAB to get to add note screen
        composeRule.onNodeWithContentDescription("Add note").performClick()
        //Enter texts in title and content test fields
        composeRule.onNodeWithTag(TestTags.TITLE_TEXT_FIELD)
            .performTextInput("test-title")
        composeRule.onNodeWithTag(TestTags.CONTENT_TEXT_FIELD)
            .performTextInput("test-content")
        //Save the new
        composeRule.onNodeWithContentDescription("Save note").performClick()
        //Make sure there is a note it the list with our title and content
        composeRule.onNodeWithText("test-title").assertIsDisplayed()
        composeRule.onNodeWithText("test-title").performClick()

        composeRule.onNodeWithTag(TestTags.TITLE_TEXT_FIELD).assertTextEquals("test-title")
        composeRule.onNodeWithTag(TestTags.CONTENT_TEXT_FIELD).assertTextEquals("test-content")
        composeRule.onNodeWithTag(TestTags.TITLE_TEXT_FIELD)
            .performTextInput("2")
        composeRule.onNodeWithContentDescription("Save note").performClick()

        composeRule.onNodeWithText("test-title2").assertIsDisplayed()
    }
    @Test
    fun saveNewNotes_orderByTitleDescending(){
        for(i in 1..3){
            //Click on FAB to get to add note screen
            composeRule.onNodeWithContentDescription("Add note").performClick()
            //Enter texts in title and content test fields
            composeRule.onNodeWithTag(TestTags.TITLE_TEXT_FIELD)
                .performTextInput(i.toString())
            composeRule.onNodeWithTag(TestTags.CONTENT_TEXT_FIELD)
                .performTextInput(i.toString())
            //Save the new
            composeRule.onNodeWithContentDescription("Save note").performClick()
        }
        composeRule.onNodeWithText("1").assertIsDisplayed()
        composeRule.onNodeWithText("2").assertIsDisplayed()
        composeRule.onNodeWithText("3").assertIsDisplayed()

        composeRule.onNodeWithContentDescription("Sort")
            .performClick()
        composeRule.onNodeWithContentDescription("Title")
            .performClick()
        composeRule.onNodeWithContentDescription("Descending")
            .performClick()

        composeRule.onAllNodesWithTag(TestTags.NOTE_ITEM)[0]
            .assertTextContains("3")
        composeRule.onAllNodesWithTag(TestTags.NOTE_ITEM)[1]
            .assertTextContains("2")
        composeRule.onAllNodesWithTag(TestTags.NOTE_ITEM)[2]
            .assertTextContains("1")
    }
}