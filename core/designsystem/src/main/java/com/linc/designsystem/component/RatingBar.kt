package com.linc.designsystem.component

import android.widget.RatingBar
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.linc.designsystem.icon.BookstoreIcons
import com.linc.designsystem.icon.asIconWrapper
import com.linc.designsystem.theme.BookstoreTheme

@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    maxRate: Int,
    rating: Int,
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