package com.linc.designsystem.component

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.linc.ui.components.SimpleIcon
import com.linc.ui.icon.BookstoreIcons
import com.linc.ui.icon.asIconWrapper
import com.linc.ui.theme.BookstoreTheme

private const val DEFAULT_MAX_RATING = 5

@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    rating: Int,
    maxRate: Int = DEFAULT_MAX_RATING,
    selectedColor: Color = Color.Yellow,
    color: Color = Color.LightGray
) {
    Row(
        modifier = modifier
    ) {
        repeat(maxRate) {
            SimpleIcon(
                icon = BookstoreIcons.Star.asIconWrapper(),
                tint = if(it < rating) selectedColor else color
            )
        }
    }
}

@Preview
@Composable
private fun RatingBarPreview() {
    BookstoreTheme {
        RatingBar(maxRate = 5, rating = 3)
    }
}