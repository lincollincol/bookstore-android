package com.linc.languagepicker

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.linc.navigation.NavigationState
import com.linc.navigation.observeNavigation
import com.linc.ui.components.SimpleIcon
import com.linc.ui.theme.icons
import com.linc.ui.theme.strings


@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun LanguagePickerRoute(
    viewModel: LanguagePickerViewModel = hiltViewModel(),
    navigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    viewModel.observeNavigation {
        when(it) {
            NavigationState.Back -> navigateBack()
        }
    }
    LanguagePickerScreen(
        languages = uiState.languages,
        onLanguageClick = viewModel::selectLanguage,
        onBackClick = viewModel::navigateBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LanguagePickerScreen(
    languages: List<LanguageItemUiState>,
    onLanguageClick: (String) -> Unit,
    onBackClick: () -> Unit
) {

    Column(modifier = Modifier.fillMaxSize()) {
        CenterAlignedTopAppBar(
            modifier = Modifier.shadow(4.dp),
            title = { Text(text = MaterialTheme.strings.language) },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    SimpleIcon(icon = MaterialTheme.icons.arrowBack)
                }
            }
        )
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(
                items = languages,
                key = { it.name }
            ) {
                LanguageItemUiState(
                    modifier = Modifier.padding(horizontal = 32.dp),
                    item = it,
                    onClick = { onLanguageClick(it.code) }
                )
            }
        }
    }


}

@Composable
private fun LanguageItemUiState(
    modifier: Modifier = Modifier,
    item: LanguageItemUiState,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .clickable(onClick = onClick)
            .fillMaxWidth()
            .padding(16.dp)
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = item.flagCode)
            Text(
                modifier = Modifier.padding(horizontal = 8.dp),
                text = item.name,
                style = MaterialTheme.typography.titleMedium,
                fontSize = 18.sp
            )
        }
        AnimatedVisibility(visible = item.isSelected) {
            SimpleIcon(icon = MaterialTheme.icons.check)
        }
    }
}
