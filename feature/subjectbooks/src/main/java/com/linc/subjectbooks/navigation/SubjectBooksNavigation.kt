package com.linc.subjectbooks.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.linc.navigation.NavigationState
import com.linc.navigation.defaultAxisZEnterTransition
import com.linc.navigation.defaultAxisZExitTransition
import com.linc.subjectbooks.SubjectBooksRoute
import soup.compose.material.motion.animation.*
import soup.compose.material.motion.navigation.composable
import javax.inject.Inject

const val subjectBooksRoute = "subjectbooks_route"
internal const val subjectIdArg = "subject_id_arg"

sealed interface SubjectBooksNavigationState : NavigationState {
    data class BookDetails(val bookId: String) : SubjectBooksNavigationState
}

internal class SubjectBooksArgs(val subjectId: String) {
    constructor(savedStateHandle: SavedStateHandle)
            : this(checkNotNull(savedStateHandle.get<String>(subjectIdArg)))
}

fun NavController.navigateToSubjectBooks(
    subjectId: String,
    navOptions: NavOptions? = null
) {
    navigate("$subjectBooksRoute/$subjectId", navOptions)
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.subjectBooksScreen(
    navigateToBookDetails: (String) -> Unit,
    navigateBack: () -> Unit
) {
    composable(
        route = "$subjectBooksRoute/{$subjectIdArg}",
        arguments = listOf(
            navArgument(subjectIdArg) { type = NavType.StringType }
        ),
        enterTransition = defaultAxisZEnterTransition,
        exitTransition = defaultAxisZExitTransition,
    ) {
        SubjectBooksRoute(
            navigateToBookDetails = navigateToBookDetails,
            navigateBack = navigateBack
        )
    }
}