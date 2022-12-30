package com.linc.bookstore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.lifecycleScope
import com.linc.bookstore.ui.BookstoreApp
import com.linc.ui.theme.BookstoreTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        Locale.setDefault(Locale.ENGLISH)

        var uiState: MainUiState by mutableStateOf(MainUiState())

        lifecycleScope.launchWhenStarted {
            viewModel.uiState
                .onEach {
                    uiState = it
                }
                .catch { it.printStackTrace() }
                .collect()
        }

        setContent {
            BookstoreTheme(
                locale = uiState.locale
            ) {
                BookstoreApp()
            }
        }
    }
}
