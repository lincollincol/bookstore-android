package com.linc.subjectbooks

import android.widget.Space
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.SaveableStateHolder
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import androidx.paging.compose.itemsIndexed
import coil.compose.AsyncImage
import com.linc.ui.state.UiState
import com.linc.designsystem.component.RatingBar
import com.linc.designsystem.component.SimpleIcon
import com.linc.designsystem.icon.BookstoreIcons
import com.linc.designsystem.icon.asIconWrapper
import com.linc.model.SubjectBooks
import com.linc.navigation.observeNavigation
import com.linc.subjectbooks.navigation.SubjectBooksNavigationState
import com.linc.ui.components.DetailedBookItem
import com.linc.ui.extensions.ASPECT_RATIO_3_4
import com.linc.ui.model.DetailedBookItemUiState

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun SubjectBooksRoute(
    viewModel: SubjectBooksViewModel = hiltViewModel(),
    navigateToBookDetails: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val books = viewModel.booksUiState.collectAsLazyPagingItems()
    // TODO: move to navigation. Use "navigateTo(NavState)" callback instead
    viewModel.observeNavigation {
        when(it) {
            is SubjectBooksNavigationState.BookDetails -> navigateToBookDetails(it.bookId)
        }
    }
    SubjectBooksScreen(
        title = uiState.title,
        books = books,
        onBookClick = viewModel::selectBook
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubjectBooksScreen(
    title: String,
    books: LazyPagingItems<DetailedBookItemUiState>,
    onBookClick: (String) -> Unit,
) {
    Column(Modifier.fillMaxSize()) {
        CenterAlignedTopAppBar(
            modifier = Modifier.shadow(4.dp).zIndex(2f),
            title = { Text(text = title) },
            navigationIcon = {
                IconButton(onClick = {  }) {
                    SimpleIcon(icon = BookstoreIcons.ArrowBack.asIconWrapper())
                }
            }
        )
        LazyColumn {
            items(items = books) {
                if(it == null) {
                    return@items
                }
                DetailedBookItem(
                    item = it,
                    onBookClick = onBookClick
                )
            }
        }
    }
}
