package com.linc.cart

import android.widget.Space
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import java.lang.Float.min
import kotlin.math.abs


@Composable
fun CartRoute() {
    CartScreen()
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun CartScreen() {
    val items = (1..10).map { "Item $it" }
    val lazyListState = rememberLazyListState()
    var scrolledY = 0f
    var previousOffset = 0
    LazyColumn(
        Modifier
            .fillMaxSize()
            .background(Color.DarkGray),
        lazyListState,
    ) {
        stickyHeader {
            Text(
                modifier = Modifier
                    .padding(vertical = 24.dp),
                text = "Title"
            )
        }
        item {
            Surface(
                modifier = Modifier
                    .graphicsLayer {
                        scrolledY += lazyListState.firstVisibleItemScrollOffset - previousOffset
                        translationY = scrolledY * 0.3f /*+ 50*/
                        previousOffset = lazyListState.firstVisibleItemScrollOffset
                    }
                    .scale(1.5f)
                    .height(240.dp)
                    .fillMaxWidth(),
                color = Color.LightGray
            ) {}
        }
        items(items) {
            Text(
                text = it,
                Modifier
                    .padding(horizontal = 8.dp)
                    .background(Color.White)
                    .fillMaxWidth()
                    .padding(8.dp)
            )
        }
    }
}