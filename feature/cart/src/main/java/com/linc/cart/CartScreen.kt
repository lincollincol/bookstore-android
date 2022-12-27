package com.linc.cart

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*

import androidx.compose.runtime.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.linc.designsystem.component.RatingBar
import com.linc.designsystem.extensions.ASPECT_RATIO_3_4
import com.linc.ui.R

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun CartRoute(
    viewModel: CartViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    CartScreen(
        orders = uiState.orders,
        ordersCount = uiState.ordersCount,
        formattedTotalPrice = uiState.formattedTotalPrice
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun CartScreen(
    modifier: Modifier = Modifier,
    orders: List<OrderItemUiState>,
    ordersCount: Int,
    formattedTotalPrice: String,
) {
    val ordersListState = rememberLazyListState()
    val showToolbarElevation by remember {
        derivedStateOf { ordersListState.firstVisibleItemScrollOffset > 0 }
    }
    val toolbarElevation by animateDpAsState(
        targetValue = if(showToolbarElevation) 4.dp else 0.dp
    )
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .then(modifier)
    ) {
        val (toolbar, list, purchaseBar) = createRefs()
        OrdersList(
            modifier = Modifier
                .constrainAs(list) {
                    linkTo(
                        top = toolbar.bottom,
                        bottom = purchaseBar.top
                    )
                    centerHorizontallyTo(parent)
                    height = Dimension.fillToConstraints
                },
            orders = orders,
            listState = ordersListState
        )
        Text(
            modifier = Modifier
                .constrainAs(toolbar) {
                    top.linkTo(parent.top)
                    centerHorizontallyTo(parent)
                }
                .fillMaxWidth()
                .shadow(toolbarElevation)
                .background(MaterialTheme.colorScheme.surface)
                .padding(32.dp),
            text = stringResource(id = R.string.orders_with_count, ordersCount),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
        )
        CompletePurchaseBar(
            modifier = Modifier
                .constrainAs(purchaseBar) {
                    bottom.linkTo(parent.bottom)
                    centerHorizontallyTo(parent)
                },
            formattedTotalPrice = formattedTotalPrice,
            onPayClick = {}
        )
    }
}

@Composable
private fun CompletePurchaseBar(
    modifier: Modifier = Modifier,
    formattedTotalPrice: String,
    onPayClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp)
            .background(MaterialTheme.colorScheme.surface)
            .padding(horizontal = 8.dp)
            .then(modifier),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = stringResource(id = R.string.complete_purchase))
        TextButton(
            onClick = onPayClick
        ) {
            Text(text = stringResource(id = R.string.pay_with_price, formattedTotalPrice))
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun OrdersList(
    modifier: Modifier = Modifier,
    orders: List<OrderItemUiState>,
    listState: LazyListState,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .then(modifier),
        contentPadding = PaddingValues(horizontal = 32.dp),
        state = listState
    ) {
        items(
            items = orders,
            key = { it.id }
        ) {
            OrderItem(
                item = it,
                onClick = {}
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LazyItemScope.OrderItem(
    modifier: Modifier = Modifier,
    item: OrderItemUiState,
    onClick: (String) -> Unit
) {
    Surface(
        modifier = Modifier.then(modifier),
        onClick = { onClick(item.id) }
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
                model = item.bookImageUrl,
                contentDescription = item.bookTitle,
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(Modifier.fillMaxWidth()) {
                Text(text = item.bookTitle)
                Text(text = item.formattedPrice)
                Text(text = item.count.toString())
                Text(text = item.formattedTotalPrice)
            }
        }
    }
}