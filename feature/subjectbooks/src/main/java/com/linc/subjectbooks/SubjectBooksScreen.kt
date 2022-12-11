package com.linc.subjectbooks

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import androidx.paging.compose.itemsIndexed
import coil.compose.AsyncImage
import com.linc.common.coroutines.state.UiState
import com.linc.model.SubjectBooks

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun SubjectBooksRoute(
    viewModel: SubjectBooksViewModel = hiltViewModel()
) {
    val books = viewModel.booksUiState.collectAsLazyPagingItems()
    SubjectBooksScreen(
        books = books
    )
}

@Composable
fun SubjectBooksScreen(
    books: LazyPagingItems<BookItemUiState>
) {
    LazyColumn {
        items(
            items = books,
//            key = { it.id }
        ) {
            if(it == null) {
                return@items
            }
            AsyncImage(
                modifier = Modifier
                    .fillParentMaxWidth(0.3f)
                    .aspectRatio(2f / 3f),
                model = it.imageUrl,
                contentDescription = it.title
            )
            Text(text = it.title)

//            item?.let {
//                Text(text = it.title)
//            }
        }
    }
}

