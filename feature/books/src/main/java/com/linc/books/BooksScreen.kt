package com.linc.books

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintLayoutScope
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import com.linc.books.navigation.BooksNavigationState
import com.linc.designsystem.component.BookstoreTextField
import com.linc.designsystem.icon.BookstoreIcons
import com.linc.designsystem.icon.asIconWrapper
import com.linc.designsystem.theme.BookstoreTheme
import com.linc.navigation.observeNavigation
import com.linc.ui.components.DetailedBookItem
import com.linc.ui.extensions.ASPECT_RATIO_3_4
import com.linc.ui.model.DetailedBookItemUiState

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
internal fun BooksRoute(
    navigateToBookDetails: (String) -> Unit,
    navigateToSubjectBooks: (String) -> Unit,
    viewModel: BooksViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val searchBooks = viewModel.searchBooksUiState.collectAsLazyPagingItems()
    viewModel.observeNavigation {
        when(it) {
            is BooksNavigationState.BookDetails -> navigateToBookDetails(it.bookId)
            is BooksNavigationState.SubjectBooks -> navigateToSubjectBooks(it.subjectId)
        }
    }
    BooksScreen(
        searchQuery = uiState.searchQuery,
        isSearching = uiState.isSearching,
        booksSections = uiState.books,
        searchBooks = searchBooks,
        onSearchValueChange = viewModel::updateSearchQuery,
        onBookClick = viewModel::selectBook,
        onSeeAllClick = viewModel::selectSubject
    )
}

@Composable
internal fun BooksScreen(
    searchQuery: String,
    isSearching: Boolean,
    booksSections: List<BooksSectionItemUiState>,
    searchBooks: LazyPagingItems<DetailedBookItemUiState>,
    onSearchValueChange: (String) -> Unit,
    onBookClick: (String) -> Unit,
    onSeeAllClick: (String) -> Unit
) {
    ConstraintLayout {
        val (searchField, list) = createRefs()
        BookstoreTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 32.dp, end = 32.dp, top = 32.dp, bottom = 16.dp)
                .constrainAs(searchField) {
                    top.linkTo(parent.top)
                    linkTo(start = parent.start, end = parent.end)
                },
            hint = stringResource(id = R.string.search_books_hint),
            value = searchQuery,
            onValueChange = onSearchValueChange,
            trailingIcon = BookstoreIcons.Search.asIconWrapper()
        )
        if(!isSearching) {
            SubjectBooks(
                modifier = Modifier
                    .constrainAs(list) {
                        linkTo(
                            top = searchField.bottom,
                            bottom = parent.bottom,
                            start = parent.start,
                            end = parent.end
                        )
                        height = Dimension.preferredWrapContent
                    },
                booksSections = booksSections,
                onBookClick = onBookClick,
                onSeeAllClick = onSeeAllClick
            )
        } else {
            SearchResultBooks(
                modifier = Modifier
                    .constrainAs(list) {
                        linkTo(
                            top = searchField.bottom,
                            bottom = parent.bottom,
                            start = parent.start,
                            end = parent.end
                        )
                        height = Dimension.preferredWrapContent
                    },
                searchBooks = searchBooks,
                onBookClick = onBookClick
            )
        }
    }

}

@Composable
private fun SearchResultBooks(
    modifier: Modifier,
    searchBooks: LazyPagingItems<DetailedBookItemUiState>,
    onBookClick: (String) -> Unit
) {
    val loadState = searchBooks.loadState

    /*progressBar.isVisible = loadState.refresh is LoadState.Loading
    retry.isVisible = loadState.refresh !is LoadState.Loading
    errorMsg.isVisible = loadState.refresh is LoadState.Error*/
    if(loadState.refresh is LoadState.Loading) {
        CircularProgressIndicator(modifier)
    } else {
        LazyColumn(modifier = modifier) {
            items(
                items = searchBooks,
                key = { it.id }
            ) {
                if(it == null) return@items
                DetailedBookItem(item = it, onBookClick = onBookClick)
            }
        }
    }
}

@Composable
private fun SubjectBooks(
    modifier: Modifier,
    booksSections: List<BooksSectionItemUiState>,
    onBookClick: (String) -> Unit,
    onSeeAllClick: (String) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(
            items = booksSections,
            key = { it.title }
        ) {
            BooksSection(
                sectionItemUiState = it,
                titlePadding = PaddingValues(horizontal = 32.dp),
                listPadding = PaddingValues(horizontal = 32.dp),
                onBookClick = onBookClick,
                onSeeAllClick = onSeeAllClick
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BooksSection(
    modifier: Modifier = Modifier,
    sectionItemUiState: BooksSectionItemUiState,
    titlePadding: PaddingValues = PaddingValues(0.dp),
    listPadding: PaddingValues = PaddingValues(0.dp),
    onBookClick: (String) -> Unit,
    onSeeAllClick: (String) -> Unit
) {
    Column(modifier = Modifier.then(modifier)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.padding(titlePadding),
                text = sectionItemUiState.title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Surface(
                modifier = Modifier.padding(titlePadding),
                shape = MaterialTheme.shapes.large,
                color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f),
                onClick = { onSeeAllClick(sectionItemUiState.subjectId) }
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                    text = stringResource(R.string.see_all),
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
        LazyRow(
            modifier = Modifier.padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = listPadding
        ) {
            items(
                items = sectionItemUiState.books,
                key = { it.id }
            ) {
                BookItem(
                    book = it,
                    onBook = onBookClick
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LazyItemScope.BookItem(
    modifier: Modifier = Modifier,
    book: BookItemUiState,
    onBook: (String) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillParentMaxWidth(0.35f)
            .then(modifier),
        onClick = { onBook(book.id) },
        shape = MaterialTheme.shapes.medium
    ) {
        Column {
            Box {
                AsyncImage(
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.medium)
                        .fillMaxWidth()
                        .aspectRatio(ASPECT_RATIO_3_4)
                        .background(Color.White),
                    model = book.imageUrl,
                    contentDescription = book.title,
                    contentScale = ContentScale.Crop
                )
                Text(
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.medium)
                        .background(Color.White)
                        .align(Alignment.TopEnd),
                    text = "${book.averageRating}(${book.ratingsCount})"
                )
            }
            Text(
                text = book.title,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Text(
                text = book.price.toString()
            )
        }
    }
}


@Preview
@Composable
private fun BooksScreenPreview() {
    BookstoreTheme {
        Column {
//            BooksScreen({})
//            BooksSection(title = "New books", books = mockBooks, onBookClick = {})
//            BookItem(book = mockBooks.first())
        }
    }
}