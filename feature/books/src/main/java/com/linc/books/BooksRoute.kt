package com.linc.books

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import coil.compose.AsyncImage
import com.linc.designsystem.component.BookstoreTextField
import com.linc.designsystem.icon.BookstoreIcons
import com.linc.designsystem.icon.asIconWrapper
import com.linc.designsystem.theme.BookstoreTheme
import com.linc.model.Book
import com.linc.model.mockBooks

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
internal fun BooksRoute(
    navigateToBookDetails: (String) -> Unit,
    viewModel: BooksViewModel = hiltViewModel()
) {
    val searchState by viewModel.searchState.collectAsStateWithLifecycle()
    BooksScreen(
        searchState,
        navigateToBookDetails,
        viewModel::updateSearchQuery
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun BooksScreen(
    searchFieldState: SearchFieldState,
    navigateToBookDetails: (String) -> Unit,
    onSearchValueChange: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            BookstoreTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 32.dp, end = 32.dp, top = 32.dp),
                hint = stringResource(id = R.string.search_books_hint),
                value = searchFieldState.query,
                onValueChange = onSearchValueChange,
                trailingIcon = BookstoreIcons.Search.asIconWrapper()
            )
            BooksSection(
                title = stringResource(id = R.string.new_books_title),
                books = mockBooks + mockBooks,
                onBookClick = {},
                titlePadding = PaddingValues(horizontal = 32.dp),
                listPadding = PaddingValues(horizontal = 32.dp)
            )
            BooksSection(
                title = stringResource(id = R.string.for_you_title),
                books = mockBooks,
                onBookClick = {},
                titlePadding = PaddingValues(horizontal = 32.dp),
                listPadding = PaddingValues(horizontal = 32.dp)
            )
            BooksSection(
                title = stringResource(id = R.string.for_you_title),
                books = mockBooks,
                onBookClick = {},
                titlePadding = PaddingValues(horizontal = 32.dp),
                listPadding = PaddingValues(horizontal = 32.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BooksSection(
    modifier: Modifier = Modifier,
    title: String,
    books: List<Book>,
    onBookClick: (Book) -> Unit,
    titlePadding: PaddingValues = PaddingValues(0.dp),
    listPadding: PaddingValues = PaddingValues(0.dp)
) {
    Column(modifier = Modifier.then(modifier)) {
        Text(
            modifier = Modifier.padding(titlePadding),
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        LazyRow(
            modifier = Modifier.padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = listPadding
        ) {
            // TODO: add item key
            items(
                items = books
            ) {
                BookItem(
                    modifier = Modifier.padding(8.dp),
                    book = it,
                    onBookClick = onBookClick
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BookItem(
    modifier: Modifier = Modifier,
    book: Book,
    onBookClick: (Book) -> Unit
) {
    Surface(
        modifier = Modifier
            .then(modifier),
        onClick = { onBookClick(book) },
        shape = MaterialTheme.shapes.medium
    ) {
        ConstraintLayout {
        val (image, title, rating, price) = createRefs()
            AsyncImage(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.medium)
                    .constrainAs(image) {
                        top.linkTo(parent.top)
                        linkTo(
                            start = parent.start,
                            end = parent.end
                        )
//                        width = Dimension.fillToConstraints
                    }
                    .width(128.dp)
                    .aspectRatio(2f/3f)
                    .background(Color.Green),
                model = book.image,
                contentDescription = book.title,
                contentScale = ContentScale.FillWidth,
                onError = {
                    it.result.throwable.printStackTrace()
                }
            )
            Text(
                modifier = Modifier
                    .constrainAs(title) {
                        top.linkTo(image.bottom)
                        linkTo(
                            start = parent.start,
                            end = parent.end
                        )
                        width = Dimension.fillToConstraints
                    },
                text = book.title,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Text(
                modifier = Modifier
                    .constrainAs(rating) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                    }
                    .clip(MaterialTheme.shapes.medium)
                    .background(Color.White),
                text = "${book.averageRating}(${book.ratingsCount})"
            )
            Text(
                modifier = Modifier
                    .constrainAs(price) {
                        top.linkTo(title.bottom)
                        start.linkTo(parent.start)
                    },
                text = book.price
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
            BooksSection(title = "New books", books = mockBooks, onBookClick = {})
//            BookItem(book = mockBooks.first())
        }
    }
}