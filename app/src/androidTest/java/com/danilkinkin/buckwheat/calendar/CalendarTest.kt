package com.danilkinkin.buckwheat.calendar

import com.danilkinkin.buckwheat.home.MainActivity
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasScrollToKeyAction
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToKey
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate

@HiltAndroidTest
class CalendarTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private val currentYear = LocalDate.now().year

    @Before
    fun setUp() {
        hiltRule.inject()
        composeTestRule.onNodeWithText("Select Dates").performClick()
    }

    @Test
    fun scrollsToTheBottom() {

        composeTestRule.onNodeWithText("January 1 $currentYear").assertIsDisplayed()
        composeTestRule.onNode(hasScrollToKeyAction()).performScrollToKey("$currentYear/12/4")
        composeTestRule.onNodeWithText("December 31 $currentYear").performClick()

        val datesSelected = composeTestRule.onDateNodes(true)
        datesSelected[0].assertTextEquals("December 31 $currentYear")
    }

    @Test
    fun onDaySelected() {
        composeTestRule.onNodeWithText("January 1 $currentYear").assertIsDisplayed()
        composeTestRule.onNodeWithText("January 2 $currentYear")
            .assertIsDisplayed().performClick()
        composeTestRule.onNodeWithText("January 3 $currentYear").assertIsDisplayed()

        val datesNoSelected = composeTestRule.onDateNodes(false)
        datesNoSelected[0].assertTextEquals("January 1 $currentYear")
        datesNoSelected[1].assertTextEquals("January 3 $currentYear")

        composeTestRule.onDateNode(true).assertTextEquals("January 2 $currentYear")
    }

    @Test
    fun twoDaysSelected() {
        composeTestRule.onNodeWithText("January 2 $currentYear")
            .assertIsDisplayed().performClick()

        val datesNoSelectedOneClick = composeTestRule.onDateNodes(false)
        datesNoSelectedOneClick[0].assertTextEquals("January 1 $currentYear")
        datesNoSelectedOneClick[1].assertTextEquals("January 3 $currentYear")

        composeTestRule.onNodeWithText("January 4 $currentYear")
            .assertIsDisplayed().performClick()

        val selected = composeTestRule.onDateNodes(true)
        selected[0].assertTextEquals("January 2 $currentYear")
        selected[1].assertTextEquals("January 3 $currentYear")
        selected[2].assertTextEquals("January 4 $currentYear")

        val datesNoSelected = composeTestRule.onDateNodes(false)
        datesNoSelected[0].assertTextEquals("January 1 $currentYear")
        datesNoSelected[1].assertTextEquals("January 5 $currentYear")
    }
}

private fun ComposeTestRule.onDateNode(selected: Boolean) = onNode(
    SemanticsMatcher.expectValue(DayStatusKey, selected)
)

private fun ComposeTestRule.onDateNodes(selected: Boolean) = onAllNodes(
    SemanticsMatcher.expectValue(DayStatusKey, selected)
)
