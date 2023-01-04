package com.linc.subjectbooks

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.linc.navigation.NavigationState
import com.linc.ui.components.SimpleIcon
import com.linc.navigation.observeNavigation
import com.linc.subjectbooks.navigation.SubjectBooksNavigationState
import com.linc.ui.components.DetailedBookItem
import com.linc.ui.components.NothingFound
import com.linc.ui.model.DetailedBookItemUiState
import com.linc.ui.theme.icons
import com.linc.ui.theme.strings

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun SubjectBooksRoute(
    viewModel: SubjectBooksViewModel = hiltViewModel(),
    navigateToBookDetails: (String) -> Unit,
    navigateBack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val books = viewModel.booksUiState.collectAsLazyPagingItems()
    // TODO: move to navigation. Use "navigateTo(NavState)" callback instead
    viewModel.observeNavigation {
        when(it) {
            is SubjectBooksNavigationState.BookDetails -> navigateToBookDetails(it.bookId)
            is NavigationState.Back -> navigateBack()
        }
    }
    SubjectBooksScreen(
        title = uiState.title,
        books = books,
        onBookClick = viewModel::selectBook,
        onBackClick = viewModel::navigateBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubjectBooksScreen(
    title: String,
    books: LazyPagingItems<DetailedBookItemUiState>,
    onBookClick: (String) -> Unit,
    onBackClick: () -> Unit
) {
    Column(Modifier.fillMaxSize()) {
        CenterAlignedTopAppBar(
            modifier = Modifier
                .shadow(4.dp)
                .zIndex(2f),
            title = { Text(text = title) },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    SimpleIcon(icon = MaterialTheme.icons.arrowBack)
                }
            }
        )
        BooksList(
            books = books,
            onBookClick = onBookClick
        )
    }
}

@Composable
fun BooksList(
    modifier: Modifier = Modifier,
    books: LazyPagingItems<DetailedBookItemUiState>,
    onBookClick: (String) -> Unit
) {
    val loadState = books.loadState
    Box(
        modifier = Modifier
            .fillMaxSize()
            .then(modifier),
        contentAlignment = Alignment.Center
    ) {
        if(loadState.refresh is LoadState.Loading) {
            CircularProgressIndicator()
        } else if(loadState.refresh is LoadState.Error || books.itemCount == 0) {
            NothingFound(
                icon = MaterialTheme.icons.bookNotFound,
                message = MaterialTheme.strings.notFound
            )
        } else {
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
}
