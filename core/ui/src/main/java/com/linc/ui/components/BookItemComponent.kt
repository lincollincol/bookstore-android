package com.linc.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.linc.designsystem.component.RatingBar
import com.linc.designsystem.extensions.ASPECT_RATIO_3_4
import com.linc.ui.model.DetailedBookItemUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LazyItemScope.DetailedBookItem(
    item: DetailedBookItemUiState,
    onBookClick: (String) -> Unit
) {
    Surface(
        onClick = { onBookClick(item.id) }
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.Top
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillParentMaxWidth(0.3f)
                    .aspectRatio(ASPECT_RATIO_3_4)
                    .clip(MaterialTheme.shapes.medium),
                model = item.imageUrl,
                contentDescription = item.title,
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(Modifier.fillMaxWidth()) {
                Text(text = item.title)
                Text(text = item.authors)
                RatingBar(maxRate = 5, rating = item.averageRating.toInt())
                Text(text = item.price.toString())
            }
        }
    }
}