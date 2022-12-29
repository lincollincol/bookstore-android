package com.linc.bookmarks

import android.widget.Space
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.BookmarkRemove
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.linc.bookmarks.navigation.BookmarksNavigationState
import com.linc.designsystem.extensions.ASPECT_RATIO_3_4
import com.linc.navigation.NavigationState
import com.linc.navigation.observeNavigation
import com.linc.ui.R
import com.linc.ui.components.SimpleIcon
import com.linc.ui.icon.BookstoreIcons
import com.linc.ui.icon.asIconWrapper
import okhttp3.internal.notifyAll

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun BookmarksRoute(
    viewModel: BookmarksViewModel = hiltViewModel(),
    navigateToBookDetails: (String) -> Unit,
    navigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    viewModel.observeNavigation {
        when(it) {
            is BookmarksNavigationState.BookDetails -> navigateToBookDetails(it.bookId)
            is NavigationState.Back -> navigateBack()
        }
    }
    BookmarksScreen(
        bookmarks = uiState.bookmarks,
        onBookmarkClick = viewModel::selectBookmark,
        onDeleteBookmarkClick = viewModel::deleteBookmark,
        onBackClick = viewModel::navigateBack
    )

}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun BookmarksScreen(
    modifier: Modifier = Modifier,
    bookmarks: List<BookmarkItemUiState>,
    onBookmarkClick: (String) -> Unit,
    onDeleteBookmarkClick: (String) -> Unit,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .then(modifier)
    ) {
        CenterAlignedTopAppBar(
            title = { Text(text = stringResource(id = R.string.bookmarks)) },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    SimpleIcon(icon = BookstoreIcons.ArrowBack.asIconWrapper())
                }
            }
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 32.dp)
        ) {
            items(
                items = bookmarks,
                key = { it.bookmarkId }
            ) {
                BookmarkedBookItem(
                    modifier = Modifier.animateItemPlacement(),
                    item = it,
                    onDeleteBookmarkClick = { onDeleteBookmarkClick(it.bookmarkId) },
                    onBookmarkClick = { onBookmarkClick(it.bookmarkId) }
                )
            }
        }
    }
}

@Composable
private fun LazyItemScope.BookmarkedBookItem(
    modifier: Modifier = Modifier,
    item: BookmarkItemUiState,
    onDeleteBookmarkClick: () -> Unit,
    onBookmarkClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .clickable(onClick = onBookmarkClick)
            .height(IntrinsicSize.Min)
            .padding(8.dp)
            .then(modifier),
        verticalAlignment = Alignment.Top
    ) {
        AsyncImage(
            modifier = Modifier
                .fillParentMaxWidth(0.3f)
                .aspectRatio(ASPECT_RATIO_3_4)
                .clip(MaterialTheme.shapes.medium),
            model = item.bookImageUrl,
            contentDescription = item.bookTitle,
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = item.bookTitle,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = item.formattedPrice,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            IconButton(
                modifier = Modifier.align(Alignment.End),
                onClick = onDeleteBookmarkClick
            ) {
                SimpleIcon(icon = BookstoreIcons.BookmarkRemove.asIconWrapper())
            }
        }
    }
}