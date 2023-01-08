package com.linc.preferences

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.flowlayout.FlowRow
import com.linc.ui.components.SimpleIcon
import com.linc.ui.icon.BookstoreIcons
import com.linc.navigation.observeNavigation
import com.linc.preferences.navigation.PreferenceNavigationState
import com.linc.ui.theme.IconWrapper
import com.linc.ui.theme.icons
import com.linc.ui.theme.strings
import java.util.*

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun PreferencesRoute(
    viewModel: PreferencesViewModel = hiltViewModel(),
    navigateToBookmarks: () -> Unit,
    navigateToSubjectsEditor: () -> Unit,
    navigateToLanguagePicker: () -> Unit,
    navigateToPayments: () -> Unit,
    navigateToAuth: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    viewModel.observeNavigation {
        when(it) {
            PreferenceNavigationState.SubjectsEditor -> navigateToSubjectsEditor()
            PreferenceNavigationState.Bookmarks -> navigateToBookmarks()
            PreferenceNavigationState.LanguagePicker -> navigateToLanguagePicker()
            PreferenceNavigationState.Payments -> navigateToPayments()
            PreferenceNavigationState.Auth -> navigateToAuth()
        }
    }
    PreferencesScreen(
        options = uiState.options,
        onOptionClick = viewModel::selectOption
    )
}

@Composable
internal fun PreferencesScreen(
    options: List<OptionItemUiState>,
    onOptionClick: (OptionItemUiState) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            modifier = Modifier.padding(32.dp),
            text = MaterialTheme.strings.preferences,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.SemiBold
        )
        LazyColumn {
            items(
                items = options,
                key = { it.name }
            ) {
                SettingsOption(
                    modifier = Modifier.padding(horizontal = 32.dp),
                    icon = it.icon(),
                    text = it.title(),
                    onClick = { onOptionClick(it) }
                )
            }
        }
    }
}

@Composable
fun SettingsOption(
    modifier: Modifier = Modifier,
    icon: IconWrapper,
    text: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .clickable(onClick = onClick)
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            SimpleIcon(icon = icon)
            Text(
                modifier = Modifier.padding(horizontal = 8.dp),
                text = text,
                style = MaterialTheme.typography.titleMedium,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
        }
        SimpleIcon(icon = MaterialTheme.icons.arrowForwardIos)
    }
}