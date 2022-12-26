package com.linc.books

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
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
import com.linc.ui.icon.BookstoreIcons
import com.linc.ui.icon.asIconWrapper
import com.linc.ui.theme.BookstoreTheme
import com.linc.navigation.observeNavigation
import com.linc.ui.components.DetailedBookItem
import com.linc.designsystem.component.SearchNotFound
import com.linc.designsystem.extensions.ASPECT_RATIO_3_4
import com.linc.ui.components.SimpleIcon
import com.linc.ui.model.DetailedBookItemUiState

@OptIn(ExperimentalLifecycleComposeApi::class, ExperimentalComposeUiApi::class)
@Composable
internal fun BooksRoute(
    navigateToBookDetails: (String) -> Unit,
    navigateToSubjectBooks: (String) -> Unit,
    viewModel: BooksViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val searchBooks = viewModel.searchBooksUiState.collectAsLazyPagingItems()
    val keyboardController = LocalSoftwareKeyboardController.current

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
        onSeeAllClick = viewModel::selectSubject,
        onSearchIconClick = viewModel::clearSearchQuery,
        onKeyboardDoneClick = { keyboardController?.hide() }
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
    onSeeAllClick: (String) -> Unit,
    onSearchIconClick: () -> Unit,
    onKeyboardDoneClick: () -> Unit
) {
    val searchFieldIcon = when {
        isSearching -> BookstoreIcons.Clear
        else -> BookstoreIcons.Search
    }.asIconWrapper()
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
            trailingIcon = searchFieldIcon,
            onTrailingIconClick = onSearchIconClick,
            onKeyboardDone = onKeyboardDoneClick
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
    Box(
        modifier = Modifier
            .fillMaxSize()
            .then(modifier),
        contentAlignment = Alignment.Center
    ) {
        val loadState = searchBooks.loadState
        if(loadState.refresh is LoadState.Loading) {
            CircularProgressIndicator()
        } else if(loadState.refresh is LoadState.Error || searchBooks.itemCount == 0) {
            SearchNotFound(
                message = stringResource(R.string.not_found)
            )
        } else {
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 32.dp)
            ) {
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
}

@Composable
private fun SubjectBooks(
    modifier: Modifier,
    booksSections: List<BooksSectionItemUiState>,
    onBookClick: (String) -> Unit,
    onSeeAllClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .then(modifier)
    ) {
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
            if(book.availableForSale) {
                Text(text = book.price.toString())
            } else {
                SimpleIcon(icon = BookstoreIcons.SoldOut.asIconWrapper())
            }
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