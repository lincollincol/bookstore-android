package com.linc.interests

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment
import com.linc.navigation.NavigationState
import com.linc.navigation.observeNavigation
import com.linc.ui.components.SimpleIcon
import com.linc.ui.theme.icons
import com.linc.ui.theme.strings

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun EditSubjectsRoute(
    viewModel: InterestsViewModel = hiltViewModel(),
    navigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    viewModel.observeNavigation {
        when(it) {
            NavigationState.Back -> navigateBack()
        }
    }
    InterestsScreen(
        sections = uiState.sections,
        formattedAvailableCount = uiState.formattedSubjectsCount,
        onSubjectClick = viewModel::selectSubject,
        onBackClick = viewModel::navigateBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InterestsScreen(
    formattedAvailableCount: String,
    sections: List<InterestSectionUiState>,
    onSubjectClick: (String) -> Unit,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        CenterAlignedTopAppBar(
            modifier = Modifier.shadow(4.dp),
            title = { Text(text = MaterialTheme.strings.interests) },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    SimpleIcon(icon = MaterialTheme.icons.arrowBack)
                }
            }
        )
        LazyColumn {
            items(
                items = sections,
                key = { it.isPrimary }
            ) {
                val title = with(MaterialTheme.strings) {
                    remember(formattedAvailableCount) {
                        when {
                            it.isPrimary -> selectedWithCount.format(formattedAvailableCount)
                            else -> available
                        }
                    }
                }
                SubjectsComponent(
                    modifier = Modifier.padding(vertical = 8.dp),
                    title = title,
                    subjects = it.items,
                    onSubjectClick = onSubjectClick
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubjectsComponent(
    modifier: Modifier = Modifier,
    title: String,
    subjects: List<InterestItemUiState>,
    onSubjectClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .then(modifier)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Medium
        )
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize(),
            mainAxisAlignment = MainAxisAlignment.SpaceEvenly,
            mainAxisSpacing = 8.dp
        ) {
            subjects.forEach {
                AssistChip(
                    label = { Text(text = it.name) },
                    onClick = { onSubjectClick(it.id) }
                )
            }
        }
    }
}