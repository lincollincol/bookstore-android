package com.linc.bookdetails

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.rememberMotionLayoutState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.linc.designsystem.component.RatingBar
import com.linc.model.Book
import com.skydoves.cloudy.Cloudy
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

@Composable
internal fun BookDetailsScreen(
    bookUiState: BookUiState,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .then(modifier),
        contentAlignment = Alignment.Center
    ) {
        when(bookUiState) {
            is BookUiState.Success -> BookDetails(book = bookUiState.book)
            is BookUiState.Loading -> CircularProgressIndicator()
            else -> {}
        }
    }
}

@OptIn(ExperimentalMotionApi::class, ExperimentalMaterial3Api::class)
@Composable
internal fun BookDetails(
    book: Book
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
    MotionLayout(
        modifier = Modifier.fillMaxSize(),
        motionScene = bookDetailsMotionScene(),
        motionLayoutState = motionState
    ) {
        AsyncImage(
            modifier = Modifier.layoutId(IMAGE_MOTION_ID),
            model = book.image,
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )
        Surface(
            modifier = Modifier
                .layoutId(CONTENT_MOTION_ID),
            color = Color.White,
            shape = RoundedCornerShape(topStart = shape, topEnd = shape)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Text(text = book.title)
                Text(text = book.author)
                RatingBar(
                    maxRate = 5,
                    rating = book.averageRating
                )
                Text(text = "About the book")
                Text(text = book.subtitle)
            }
        }
        Surface(
            modifier = Modifier
                .layoutId(BUY_BUTTON_MOTION_ID),
            color = Color.Blue,
            onClick = { /*TODO*/ },
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