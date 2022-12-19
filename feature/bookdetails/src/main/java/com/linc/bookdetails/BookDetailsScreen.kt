package com.linc.bookdetails

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.*
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.flowlayout.FlowRow
import com.linc.designsystem.component.RatingBar
import com.linc.navigation.NavigationState
import com.linc.navigation.observeNavigation
import com.linc.ui.extensions.animateAlignmentAsState
import com.linc.ui.extensions.heightInPx
import com.linc.ui.extensions.toAnnotatedString
import soup.compose.material.motion.MaterialMotion
import soup.compose.material.motion.animation.*

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun BookDetailsRoute(
    modifier: Modifier = Modifier,
    viewModel: BookDetailsViewModel = hiltViewModel(),
    navigateBack: () -> Unit
) {
    val bookUiState: BookUiState by viewModel.bookUiState.collectAsStateWithLifecycle()
    viewModel.observeNavigation {
        when(it) {
            NavigationState.Back -> navigateBack()
        }
    }
    BookDetailsScreen(
        modifier = modifier,
        bookUiState = bookUiState,
        onBack = viewModel::navigateBack,
        onCart = viewModel::addToCart
    )
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun BookDetailsScreen(
    modifier: Modifier = Modifier,
    bookUiState: BookUiState,
    onCart: (String) -> Unit,
    onBack: () -> Unit
) {
    MaterialMotion(
        targetState = bookUiState,
        transitionSpec = {
            slideInVertically(tween(500)) { it } with slideOutVertically { it }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .then(modifier),
            contentAlignment = Alignment.Center
        ) {
            AnimatedVisibility(bookUiState.isLoading) {
                CircularProgressIndicator()
            }
            AnimatedVisibility(!bookUiState.isLoading) {
                BookDetails(
                    book = bookUiState,
                    onBack = onBack,
                    onCart = onCart
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun BookDetails(
    book: BookUiState,
    onCart: (String) -> Unit,
    onBack: () -> Unit
) {
    val lazyListState = rememberLazyListState()
    val buttonAlignment by animateAlignmentAsState(
        targetAlignment = when {
            lazyListState.firstVisibleItemIndex > 0 -> Alignment.BottomCenter
            else -> Alignment.BottomEnd
        }
    )
    val buttonCornersRadius by animateDpAsState(
        targetValue = if(lazyListState.firstVisibleItemIndex > 0) 16.dp else 0.dp
    )
    val buttonShape = RoundedCornerShape(
        topStart = 16.dp,
        topEnd = buttonCornersRadius,
        bottomEnd = buttonCornersRadius,
        bottomStart = buttonCornersRadius
    )
    val buttonBottomPadding by animateDpAsState(
        targetValue = if(lazyListState.firstVisibleItemIndex > 0) 16.dp else 0.dp
    )
    Box {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = lazyListState,
        ) {
            item {
                BookImageDetails(
                    imageUrl = book.imageUrl,
                    lazyListState = lazyListState
                )
            }
            item { BookTextDetails(book = book) }
        }
        Surface(
            modifier = Modifier
                .align(buttonAlignment)
                .padding(bottom = buttonBottomPadding),
            color = MaterialTheme.colorScheme.secondary,
            onClick = { onCart(book.id) },
            shape = buttonShape
        ) {
            Text(
                modifier = Modifier
                    .padding(vertical = 24.dp, horizontal = 64.dp),
                text = stringResource(R.string.buy_now),
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun LazyItemScope.BookImageDetails(
    modifier: Modifier = Modifier,
    imageUrl: String,
    lazyListState: LazyListState
) {
    var scrolledY by remember { mutableStateOf(0f) }
    var previousOffset by remember { mutableStateOf(0) }
    Box(
        modifier = Modifier
            .graphicsLayer {
                scrolledY += lazyListState.firstVisibleItemScrollOffset - previousOffset
                translationY = scrolledY * 0.3f
                previousOffset = lazyListState.firstVisibleItemScrollOffset
            }
            .fillParentMaxHeight(0.5f)
            .fillMaxWidth()
            .then(modifier)
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxSize()
                .scale(1.0f, 1.2f),
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(500)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookTextDetails(
    modifier: Modifier = Modifier,
    book: BookUiState
) {
    var parentBottom by remember { mutableStateOf(0f) }
    var componentBottom by remember { mutableStateOf(0f) }
    val fillSpaceHeight by remember(componentBottom, parentBottom) {
        derivedStateOf {
            (parentBottom - componentBottom).coerceAtLeast(parentBottom / 4f)
        }
    }

    println(fillSpaceHeight)
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .onGloballyPositioned {
                parentBottom = it.parentLayoutCoordinates?.boundsInRoot()?.bottom ?: 0f
            }
            .then(modifier),
        shape = MaterialTheme.shapes.medium,
        shadowElevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .padding(start = 24.dp, end = 24.dp, top = 24.dp),
        ) {
            Text(
                text = book.title,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = book.authors.joinToString(),
                style = MaterialTheme.typography.bodyMedium
            )
            Row {
                RatingBar(rating = book.averageRating.toInt())
                Text(
                    text = "(${book.ratingsCount})",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Text(
                text = stringResource(id = R.string.about_book),
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = HtmlCompat.fromHtml(
                    book.description,
                    HtmlCompat.FROM_HTML_MODE_LEGACY
                ).toAnnotatedString(),
                style = MaterialTheme.typography.bodyMedium
            )
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned {
                        componentBottom = it.boundsInParent().bottom
                    }
            ) {
                book.categories.forEach {
                    AssistChip(
                        onClick = {},
                        label = { Text(text = it) }
                    )
                }
            }
            Spacer(modifier = Modifier.heightInPx(fillSpaceHeight))
        }
    }

}

@Preview
@Composable
private fun BookDetailsScreenPreview() {

}