package com.linc.payments

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.linc.designsystem.extensions.ASPECT_RATIO_3_4
import com.linc.navigation.NavigationState
import com.linc.navigation.observeNavigation
import com.linc.ui.components.NothingFound
import com.linc.ui.components.SimpleIcon
import com.linc.ui.theme.icons
import com.linc.ui.theme.strings

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun PaymentsRoute(
    viewModel: PaymentsViewModel = hiltViewModel(),
    navigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    viewModel.observeNavigation {
        when(it) {
            NavigationState.Back -> navigateBack()
        }
    }
    PaymentsScreen(
        orders = uiState.orders,
        onBackClick = viewModel::navigateBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PaymentsScreen(
    orders: List<OrderItemUiState>,
    onBackClick: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        CenterAlignedTopAppBar(
            modifier = Modifier.shadow(4.dp),
            title = { Text(text = "Payments") },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    SimpleIcon(icon = MaterialTheme.icons.arrowBack)
                }
            }
        )
        PaymentsList(orders = orders)
    }
}

@Composable
private fun PaymentsList(
    modifier: Modifier = Modifier,
    orders: List<OrderItemUiState>
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .then(modifier),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(visible = orders.isEmpty()) {
            NothingFound(
                icon = MaterialTheme.icons.payments,
                message = "No payments yet"
            )
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 32.dp)
        ) {
            items(
                items = orders,
                key = { it.orderId },
            ) {
                OrderItem(item = it)
            }
        }
    }
}

@Composable
private fun LazyItemScope.OrderItem(
    modifier: Modifier = Modifier,
    item: OrderItemUiState
) {
    Row(
        modifier = Modifier
            .height(IntrinsicSize.Min)
            .padding(8.dp)
            .then(modifier),
        verticalAlignment = Alignment.Top
    ) {
        AsyncImage(
            modifier = Modifier
                .fillParentMaxWidth(0.3f)
                .aspectRatio(ASPECT_RATIO_3_4)
                .clip(MaterialTheme.shapes.medium),
            model = item.bookImageUrl,
            contentDescription = item.bookTitle,
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                text = item.bookTitle,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "${item.formattedTotalPrice} (x${item.count})",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = item.formattedLastModifiedDate,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }

}