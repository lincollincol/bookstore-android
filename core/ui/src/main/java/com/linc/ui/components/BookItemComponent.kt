package com.linc.ui.components

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.linc.designsystem.extensions.ASPECT_RATIO_3_4
import com.linc.ui.model.DetailedBookItemUiState
import com.linc.ui.model.formattedPrice
import com.linc.ui.model.isAuthorsListed
import com.linc.ui.theme.icons

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LazyItemScope.DetailedBookItem(
    modifier: Modifier = Modifier,
    item: DetailedBookItemUiState,
    onBookClick: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .clickable(onClick = { onBookClick(item.id) })
            .padding(8.dp)
            .then(modifier),
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
            Text(
                text = item.title,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            if(item.isAuthorsListed) Text(text = item.authors)
            RatingBar(rating = item.averageRating.toInt())
            if(item.isAvailable) {
                Text(text = item.formattedPrice)
            } else {
                SimpleIcon(icon = MaterialTheme.icons.soldOut)
            }
        }
    }
}