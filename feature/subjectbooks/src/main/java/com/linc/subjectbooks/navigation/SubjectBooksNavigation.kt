package com.linc.subjectbooks.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.linc.subjectbooks.SubjectBooksRoute
import javax.inject.Inject

const val subjectBooksRoute = "subjectbooks_route"
internal const val subjectIdArg = "subject_id_arg"

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

fun NavGraphBuilder.subjectBooksScreen() {
    composable(
        "$subjectBooksRoute/{$subjectIdArg}",
        arguments = listOf(
            navArgument(subjectIdArg) { type = NavType.StringType }
        )
    ) {
        SubjectBooksRoute()
    }
}