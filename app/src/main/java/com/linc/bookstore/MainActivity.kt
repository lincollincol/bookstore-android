package com.linc.bookstore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.linc.bookstore.ui.BookstoreApp
import com.linc.ui.theme.BookstoreTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            com.linc.ui.theme.BookstoreTheme {
                BookstoreApp()
            }
        }
    }
}
