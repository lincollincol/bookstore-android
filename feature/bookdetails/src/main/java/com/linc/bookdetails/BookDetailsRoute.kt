package com.linc.bookdetails

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.view.MotionEvent
import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.rememberMotionLayoutState
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.google.accompanist.flowlayout.FlowRow
import com.linc.common.coroutines.extension.toAnnotatedString
import com.linc.designsystem.component.RatingBar
import com.linc.designsystem.component.SimpleIcon
import com.linc.designsystem.icon.BookstoreIcons
import com.linc.designsystem.icon.asIconWrapper
import com.linc.model.Book
import com.linc.navigation.NavigationState
import com.linc.navigation.observeNavigation
import com.skydoves.cloudy.Cloudy
import soup.compose.material.motion.MaterialMotion
import soup.compose.material.motion.animation.*
import javax.annotation.Untainted
import kotlin.math.abs

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

@SuppressLint("UnrememberedMutableState")
@Composable
fun animateAlignmentAsState(
    targetAlignment: Alignment,
): State<Alignment> {
    val biased = targetAlignment as BiasAlignment
    val horizontal by animateFloatAsState(biased.horizontalBias)
    val vertical by animateFloatAsState(biased.verticalBias)
    return derivedStateOf { BiasAlignment(horizontal, vertical) }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun BookDetails(
    book: BookUiState,
    onCart: (String) -> Unit,
    onBack: () -> Unit
) {
    val lazyListState = rememberLazyListState()
    var scrolledY by remember { mutableStateOf(0f) }
    var previousOffset by remember { mutableStateOf(0) }
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
    println()
    Box {
        LazyColumn(
            Modifier.fillMaxSize(),
            lazyListState,
        ) {
            item {
                Box(
                    modifier = Modifier
                        .graphicsLayer {
                            scrolledY += lazyListState.firstVisibleItemScrollOffset - previousOffset
                            translationY = scrolledY * 0.3f /*+ 50*/
                            previousOffset = lazyListState.firstVisibleItemScrollOffset
                        }
                        .fillParentMaxHeight(0.5f)
                        .fillMaxWidth()
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxSize()
                            .scale(1.0f, 1.2f),
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(book.imageUrl)
                            .crossfade(500)
                            .build(),
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth
                    )
                }
            }
            item {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
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
                            RatingBar(
                                maxRate = 5,
                                rating = book.averageRating.toInt()
                            )
                            Text(
                                text = "(${book.ratingsCount})",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                        Text(
                            text = "About the book",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = HtmlCompat.fromHtml(
                                book.description,
                                HtmlCompat.FROM_HTML_MODE_LEGACY
                            ).toAnnotatedString(),
                            style = MaterialTheme.typography.bodyMedium
                        )
                        FlowRow(modifier = Modifier.fillMaxWidth()) {
                            book.categories.forEach {
                                AssistChip(
                                    onClick = { /*TODO*/ },
                                    label = { Text(text = it) }
                                )
                            }
                        }
                        Spacer(modifier = Modifier.fillParentMaxSize(0.5f))
                    }
                }
            }
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
                text = "Buy now",
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
    }
}



@Preview
@Composable
private fun BookDetailsScreenPreview() {

}