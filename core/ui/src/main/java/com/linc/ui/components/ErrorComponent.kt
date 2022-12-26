package com.linc.designsystem.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.linc.ui.components.SimpleIcon
import com.linc.ui.icon.BookstoreIcons
import com.linc.ui.icon.asIconWrapper

@Composable
fun SearchNotFound(
    modifier: Modifier = Modifier,
    message: String
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SimpleIcon(
            modifier = Modifier.size(48.dp),
            icon = BookstoreIcons.Search.asIconWrapper()
        )
        Text(text = message)
    }
}