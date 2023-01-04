package com.linc.payments

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.linc.ui.components.SimpleIcon
import com.linc.ui.theme.icons

@Composable
fun CardsRoute(
    viewModel: PaymentsViewModel = hiltViewModel()
) {
    CardsScreen(
        onAddCardClick = viewModel::addCard
    )
}

@Composable
private fun CardsScreen(
    onAddCardClick: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        FloatingActionButton(onClick = onAddCardClick) {
            SimpleIcon(icon = MaterialTheme.icons.add)
        }
    }
}