package com.linc.books

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.linc.designsystem.theme.BookstoreTheme

@Composable
internal fun BooksRoute(
    navigateToBookDetails: (String) -> Unit
) {
    BooksScreen(navigateToBookDetails)
}

@Composable
internal fun BooksScreen(
    navigateToBookDetails: (String) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = { navigateToBookDetails("123") }) {
            Text("Hello")
        }
    }
}

@Preview
@Composable
private fun BooksScreenPreview() {
    BookstoreTheme {
        BooksScreen({})
    }
}