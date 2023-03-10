package com.linc.books

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
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
import com.linc.navigation.observeNavigation
import com.linc.ui.components.DetailedBookItem
import com.linc.ui.components.NothingFound
import com.linc.designsystem.extensions.ASPECT_RATIO_3_4
import com.linc.ui.components.SimpleIcon
import com.linc.ui.model.DetailedBookItemUiState
import com.linc.ui.theme.icons
import com.linc.ui.theme.strings

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
    val booksListState = rememberLazyListState()
    val searchFieldIcon = when {
        isSearching -> MaterialTheme.icons.clear
        else -> MaterialTheme.icons.search
    }
    ConstraintLayout {
        val (searchField, list) = createRefs()
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
                listState = booksListState,
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
                listState = booksListState,
                searchBooks = searchBooks,
                onBookClick = onBookClick
            )
        }
        BookstoreTextField(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(start = 32.dp, end = 32.dp, top = 32.dp, bottom = 16.dp)
                .constrainAs(searchField) {
                    top.linkTo(parent.top)
                    linkTo(start = parent.start, end = parent.end)
                },
            hint = MaterialTheme.strings.searchBooksHint,
            value = searchQuery,
            onValueChange = onSearchValueChange,
            trailingIcon = searchFieldIcon,
            onTrailingIconClick = onSearchIconClick,
            onKeyboardDone = onKeyboardDoneClick
        )
    }

}

@Composable
private fun SearchResultBooks(
    modifier: Modifier,
    listState: LazyListState,
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
            NothingFound(
                icon = MaterialTheme.icons.search,
                message = MaterialTheme.strings.notFound
            )
        } else {
            LazyColumn(
                modifier = Modifier.animateContentSize(),
                state = listState
            ) {
                items(
                    items = searchBooks,
                    key = { it.id }
                ) {
                    if(it == null) return@items
                    DetailedBookItem(
                        modifier = Modifier.padding(horizontal = 32.dp),
                        item = it,
                        onBookClick = onBookClick
                    )
                }
            }
        }
    }
}

@Composable
private fun SubjectBooks(
    modifier: Modifier,
    listState: LazyListState,
    booksSections: List<BooksSectionItemUiState>,
    onBookClick: (String) -> Unit,
    onSeeAllClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .animateContentSize()
            .fillMaxSize()
            .then(modifier),
        state = listState
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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
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
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.8f),
                onClick = { onSeeAllClick(sectionItemUiState.subjectId) }
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                    text = MaterialTheme.strings.seeAll,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
        LazyRow(
            modifier = Modifier.padding(vertical = 16.dp),
            contentPadding = listPadding
        ) {
            items(
                items = sectionItemUiState.books,
                key = { it.id }
            ) {
                BookItem(
                    modifier = Modifier
                        .animateItemPlacement()
                        .padding(horizontal = 8.dp),
                    book = it,
                    onBookClick = { onBookClick(it.id) }
                )
            }
        }
    }
}

@Composable
private fun LazyItemScope.BookItem(
    modifier: Modifier = Modifier,
    book: BookItemUiState,
    onBookClick: () -> Unit
) {
    val rateShape = remember { RoundedCornerShape(0.dp, 8.dp, 0.dp, 8.dp) }
    val priceShape = remember { RoundedCornerShape(0.dp, 0.dp, 8.dp, 8.dp) }
    Column(
        modifier = Modifier
            .clip(MaterialTheme.shapes.medium)
            .clickable(onClick = onBookClick)
            .fillParentMaxWidth(0.35f)
            .then(modifier)
    ) {
        Box {
            AsyncImage(
                modifier = Modifier
                    .animateContentSize(tween(500))
                    .clip(MaterialTheme.shapes.medium)
                    .fillMaxWidth()
                    .aspectRatio(ASPECT_RATIO_3_4)
                    .background(Color.White),
                model = book.imageUrl,
                contentDescription = book.title,
                contentScale = ContentScale.Crop
            )
            Row(
                modifier = Modifier
                    .clip(rateShape)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(horizontal = 4.dp, vertical = 2.dp)
                    .align(Alignment.TopEnd),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = book.formattedRating,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Medium
                )
                SimpleIcon(
                    modifier = Modifier.size(12.dp),
                    icon = MaterialTheme.icons.starOutlined
                )
            }
            Box(
                modifier = Modifier
                    .clip(priceShape)
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(vertical = 4.dp)
                    .align(Alignment.BottomCenter),
                contentAlignment = Alignment.Center
            ) {
                if(book.availableForSale) {
                    Text(
                        text = book.formattedPrice,
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Medium,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                } else {
                    SimpleIcon(icon = MaterialTheme.icons.soldOut)
                }
            }
        }
        Text(
            text = book.title,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}