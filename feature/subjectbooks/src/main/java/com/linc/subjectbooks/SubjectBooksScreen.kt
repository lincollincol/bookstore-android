package com.linc.subjectbooks

import android.widget.Space
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import androidx.paging.compose.itemsIndexed
import coil.compose.AsyncImage
import com.linc.common.coroutines.state.UiState
import com.linc.designsystem.component.RatingBar
import com.linc.designsystem.component.SimpleIcon
import com.linc.designsystem.icon.BookstoreIcons
import com.linc.designsystem.icon.asIconWrapper
import com.linc.model.SubjectBooks
import com.linc.navigation.observeNavigation
import com.linc.subjectbooks.navigation.SubjectBooksNavigationState

@Composable
fun SubjectBooksRoute(
    viewModel: SubjectBooksViewModel = hiltViewModel(),
    navigateToBookDetails: (String) -> Unit
) {
    val books = viewModel.booksUiState.collectAsLazyPagingItems()
    // TODO: move to navigation. Use "navigateTo(NavState)" callback instead
    viewModel.observeNavigation {
        when(it) {
            is SubjectBooksNavigationState.BookDetails -> navigateToBookDetails(it.bookId)
        }
    }
    SubjectBooksScreen(
        books = books,
        onBookClick = viewModel::selectBook
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun SubjectBooksScreen(
    books: LazyPagingItems<BookItemUiState>,
    onBookClick: (String) -> Unit
) {
    LazyColumn {
        stickyHeader {
            CenterAlignedTopAppBar(
                title = { Text(text = "Subject") },
                navigationIcon = {
                    IconButton(onClick = { onBookClick("mNGNEAAAQBAJ") }) {
                        SimpleIcon(icon = BookstoreIcons.ArrowBack.asIconWrapper())
                    }
                }
            )
        }
        items(items = books) {
            if(it == null) {
                return@items
            }
            Surface(
                onClick = { onBookClick(it.id) }
            ) {
                Row(
                    modifier = Modifier.padding(8.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .fillParentMaxWidth(0.4f)
                            // TODO: move to common consts: ASPECT_RATION_2_3
                            .aspectRatio(2f / 3f)
                            .clip(MaterialTheme.shapes.medium),
                        model = it.imageUrl,
                        contentDescription = it.title,
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(Modifier.fillMaxWidth()) {
                        Text(text = it.title)
                        Text(text = it.authors)
                        // TODO: move to common consts: MAX_RATING
                        RatingBar(maxRate = 5, rating = it.averageRating.toInt())
                        Text(text = it.price.toString())
                    }
                }
            }
        }
    }
}

