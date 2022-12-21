package com.linc.bookdetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.linc.designsystem.component.SimpleIcon
import com.linc.designsystem.icon.BookstoreIcons
import com.linc.designsystem.icon.asIconWrapper
import com.linc.designsystem.theme.BookstoreTheme

@Preview
@Composable
private fun IconPreview() {
    BookstoreTheme {
        Box() {
            FloatingActionButton(
                modifier = Modifier
                    .size(48.dp),
                containerColor = Color.Cyan,
                onClick = { /*TODO*/ },
                shape = CircleShape
            ) {
                SimpleIcon(icon = BookstoreIcons.ArrowBack.asIconWrapper())
            }
        }
    }
}