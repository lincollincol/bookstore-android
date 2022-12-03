package com.linc.bookdetails

import android.graphics.Typeface
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.view.MotionEvent
import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
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
import androidx.compose.ui.zIndex
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
import com.linc.designsystem.component.RatingBar
import com.linc.designsystem.component.SimpleIcon
import com.linc.designsystem.icon.BookstoreIcons
import com.linc.designsystem.icon.asIconWrapper
import com.linc.model.Book
import com.skydoves.cloudy.Cloudy
import soup.compose.material.motion.MaterialMotion
import soup.compose.material.motion.animation.*
import kotlin.math.abs

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun BookDetailsRoute(
    modifier: Modifier = Modifier,
    viewModel: BookDetailsViewModel = hiltViewModel(),
) {
    val bookUiState: BookUiState by viewModel.bookUiState.collectAsStateWithLifecycle()
    BookDetailsScreen(
        bookUiState = bookUiState,
        modifier = modifier
    )
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun BookDetailsScreen(
    bookUiState: BookUiState,
    modifier: Modifier = Modifier,
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
                BookDetails2(book = bookUiState)
            }
        }
    }
}

@Composable
internal fun BookDetails2(
    book: BookUiState
) {
    val lazyListState = rememberLazyListState()
    var scrolledY = 0f
    var previousOffset = 0
    val shape by animateDpAsState(
        targetValue = 16.dp
//        targetValue = 16.dp * abs(motionState.currentProgress - 1f)
    )
    println(scrolledY)
    LazyColumn(
        Modifier
            .fillMaxSize(),
        lazyListState,
    ) {
        item {
            Box(
                modifier = Modifier
                    .graphicsLayer {
                        scrolledY += lazyListState.firstVisibleItemScrollOffset - previousOffset
                        translationY = scrolledY * 0.5f /*+ 50*/
                        previousOffset = lazyListState.firstVisibleItemScrollOffset
                    }
                    .height(240.dp)
                    .fillMaxWidth()
            ) {
                AsyncImage(
                    modifier = Modifier.fillMaxSize().scale(1.0f, 1.2f),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(book.imageUrl)
                        .crossfade(500)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth
                )
                SimpleIcon(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .zIndex(1f)
                        .clip(CircleShape)
                        .background(Color.White)
                        .padding(8.dp),
                    icon = BookstoreIcons.ArrowBack.asIconWrapper()
                )
                SimpleIcon(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .zIndex(1f)
                        .clip(CircleShape)
                        .background(Color.White)
                        .padding(8.dp),
                    icon = BookstoreIcons.Cart.asIconWrapper()
                )
            }
        }
        item {
            Surface(
                modifier = Modifier
                    .layoutId(CONTENT_MOTION_ID),
                shape = RoundedCornerShape(topStart = shape, topEnd = shape)
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
                        text = book.author,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Row {
                        RatingBar(
                            maxRate = 5,
                            rating = book.averageRating
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
                }
            }
        }
    }
}

@OptIn(ExperimentalMotionApi::class, ExperimentalMaterial3Api::class)
@Composable
internal fun BookDetails(
    book: BookUiState
) {
    val motionState = rememberMotionLayoutState()
    val shape by animateDpAsState(
        targetValue = 16.dp * abs(motionState.currentProgress - 1f)
    )
    val buttonShapeRadius by animateDpAsState(
        targetValue = 16.dp * motionState.currentProgress.coerceAtLeast(0f)
    )
    val buttonShape = RoundedCornerShape(
        topStart = 16.dp,
        topEnd = buttonShapeRadius,
        bottomEnd = buttonShapeRadius,
        bottomStart = buttonShapeRadius
    )
    val scrollState = rememberScrollState()

    MotionLayout(
        modifier = Modifier
            .fillMaxSize(),
        motionScene = bookDetailsMotionScene(),
        motionLayoutState = motionState,
    ) {
        AsyncImage(
            modifier = Modifier.layoutId(IMAGE_MOTION_ID),
            model = ImageRequest.Builder(LocalContext.current)
                .data(book.imageUrl)
                .crossfade(500)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
        )
        // https://stackoverflow.com/questions/68855064/how-do-i-get-the-whole-offset-of-a-compose-component
        /*
        0    - 1f
        1137 - 0
         */
        Surface(
            modifier = Modifier
                .onGloballyPositioned {
                    val rect = it.boundsInRoot()
                    println(rect.topLeft)
                }
                .layoutId(CONTENT_MOTION_ID)
//                .verticalScroll(scroll)
                .pointerInput(Unit) {
                    detectVerticalDragGestures { change, dragAmount ->
                        println("${change.position}")
                    }
                }
                /*.verticalScroll(
                    state = scroll,
                    enabled = motionState.currentProgress >= 1
                )*/,
            color = Color.White,
            shape = RoundedCornerShape(topStart = shape, topEnd = shape)
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
                    text = book.author,
                    style = MaterialTheme.typography.bodyMedium
                )
                Row {
                    RatingBar(
                        maxRate = 5,
                        rating = book.averageRating
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
            }
        }
        Surface(
            modifier = Modifier
                .layoutId(BUY_BUTTON_MOTION_ID),
            color = MaterialTheme.colorScheme.secondary,
            onClick = book.onBuyClick,
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

fun Spanned.toAnnotatedString(): AnnotatedString = buildAnnotatedString {
    val spanned = this@toAnnotatedString
    append(spanned.toString())
    getSpans(0, spanned.length, Any::class.java).forEach { span ->
        val start = getSpanStart(span)
        val end = getSpanEnd(span)
        when (span) {
            is StyleSpan -> when (span.style) {
                Typeface.BOLD -> addStyle(SpanStyle(fontWeight = FontWeight.Bold), start, end)
                Typeface.ITALIC -> addStyle(SpanStyle(fontStyle = FontStyle.Italic), start, end)
                Typeface.BOLD_ITALIC -> addStyle(SpanStyle(fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic), start, end)
            }
            is UnderlineSpan -> addStyle(SpanStyle(textDecoration = TextDecoration.Underline), start, end)
            is ForegroundColorSpan -> addStyle(SpanStyle(color = Color(span.foregroundColor)), start, end)
        }
    }
}

@Preview
@Composable
private fun BookDetailsScreenPreview() {

}