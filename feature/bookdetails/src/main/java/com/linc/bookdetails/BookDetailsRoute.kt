package com.linc.bookdetails

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
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.rememberMotionLayoutState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.linc.model.Book
import com.skydoves.cloudy.Cloudy
import kotlin.math.abs

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun BookDetailsRoute(
    modifier: Modifier = Modifier,
    viewModel: BookDetailsViewModel = hiltViewModel()
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

@OptIn(ExperimentalMotionApi::class)
@Composable
internal fun BookDetails(
    book: Book
) {
    val motionState = rememberMotionLayoutState()
    val shape by animateDpAsState(
        targetValue = 16.dp * abs(motionState.currentProgress - 1f)
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
            ) {
                repeat(100) {
                    Text(
                        text = "Hello",
                        fontStyle = MaterialTheme.typography.titleLarge.fontStyle
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun BookDetailsScreenPreview() {

}