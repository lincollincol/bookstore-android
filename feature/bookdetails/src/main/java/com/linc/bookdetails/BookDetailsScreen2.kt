package com.linc.bookdetails

import android.graphics.ColorSpace
import android.graphics.RadialGradient
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.*
import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.*
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.linc.designsystem.component.SimpleIcon
import com.linc.designsystem.icon.BookstoreIcons
import com.linc.designsystem.icon.asIconWrapper
import com.linc.designsystem.theme.BookstoreTheme
import com.linc.ui.extensions.ASPECT_RATIO_3_2
import com.linc.ui.extensions.ASPECT_RATIO_4_5
import com.linc.ui.extensions.getVibrantColor
@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
internal fun BookDetailsScreen(
    modifier: Modifier = Modifier,
    bookUiState: BookUiState,
    onCart: (String) -> Unit,
    onBack: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    println(scrollBehavior.isPinned)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection)
    ) {
        LargeTopAppBar(
            title = {
                Text(
                    text = bookUiState.title,
                    overflow = TextOverflow.Ellipsis,
//                    maxLines = if(scrollBehavior.state.) 1 else 3
                )
            },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    SimpleIcon(icon = BookstoreIcons.ArrowBack.asIconWrapper())
                }
            },
            actions = {
                IconButton(onClick = onBack) {
                    SimpleIcon(icon = BookstoreIcons.OutlinedBookmark.asIconWrapper())
                }
            },
            scrollBehavior = scrollBehavior
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BookImage(
                imageUrl = bookUiState.imageUrl,
            )
            BookContent(
                book = bookUiState,
                modifier = Modifier
            )
        }
    }
}

@Composable
fun BookContent(
    modifier: Modifier = Modifier,
    book: BookUiState
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .then(modifier)
    ) {
//        Text(
//            text = book.title,
//            style = MaterialTheme.typography.titleLarge
//        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = book.authors.joinToString(),
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = book.description,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
@Composable
private fun BookImage(
    modifier: Modifier = Modifier,
    imageUrl: String
) {
    var vibrant by remember { mutableStateOf(Color.Transparent) }
    AsyncImage(
        modifier = Modifier
            .fillMaxSize(0.8f)
            .aspectRatio(ASPECT_RATIO_4_5)
            .shadow(
                elevation = 32.dp,
                spotColor = vibrant,
                clip = false,
                shape = CircleShape
            )
            .then(modifier),
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .allowHardware(false)
            .build(),
        contentDescription = null,
        onSuccess = {
            Palette.Builder(it.result.drawable.toBitmap()).generate { palette ->
                vibrant = palette.getVibrantColor()
            }
        }
    )
}

@Preview
@Composable
private fun ScreenPreview() {
    BookstoreTheme {
        BookDetailsScreen(
            bookUiState = BookUiState(),
            onCart = {},
            onBack = {}
        )
    }
}