package com.linc.bookstore

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.linc.designsystem.theme.BookstoreTheme

@Composable
fun BookstoreComposeApp() {
    BookstoreTheme {

    }
    BookstoreTheme {
//        val viewModel: MainViewModel = viewModel()
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
        }
    }
}