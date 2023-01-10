package com.linc.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.linc.ui.icon.BookstoreIcons
import com.linc.ui.theme.BookstoreTheme
import com.linc.ui.theme.icons

private const val DEFAULT_MAX_RATING = 5

@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    rating: Int,
    maxRate: Int = DEFAULT_MAX_RATING,
    selectedColor: Color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
    color: Color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
) {
    Row(
        modifier = modifier
    ) {
        repeat(maxRate) {
            SimpleIcon(
                icon = MaterialTheme.icons.star,
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