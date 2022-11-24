package com.linc.bookstore

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.linc.bookstore.ui.theme.BookstoreTheme

@Composable
fun BookstoreComposeApp() {
    BookstoreTheme {
        val viewModel: MainViewModel = viewModel()
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
        }
    }
}