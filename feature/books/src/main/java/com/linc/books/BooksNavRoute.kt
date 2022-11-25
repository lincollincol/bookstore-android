package com.linc.books

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.linc.designsystem.icon.BookstoreIcons
import com.linc.designsystem.theme.BookstoreTheme

@Composable
internal fun BooksRoute() {
    BooksScreen()
}

@Composable
internal fun BooksScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Hello")
    }
}

@Preview
@Composable
private fun BooksScreenPreview() {
    BookstoreTheme {
        BooksScreen()
    }
}