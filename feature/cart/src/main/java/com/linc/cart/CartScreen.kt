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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.linc.cart.navigation.CartNavigationState
import com.linc.designsystem.extensions.ASPECT_RATIO_3_4
import com.linc.navigation.observeNavigation
import com.linc.ui.R
import com.linc.ui.theme.strings

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun CartRoute(
    viewModel: CartViewModel = hiltViewModel(),
    navigateToBookDetails: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    viewModel.observeNavigation {
        when(it) {
            is CartNavigationState.BookDetails -> navigateToBookDetails(it.bookId)
        }
    }
    CartScreen(
        orders = uiState.orders,
        ordersCount = uiState.ordersCount,
        formattedTotalPrice = uiState.formattedTotalPrice,
        onPurchaseAllClick = viewModel::purchaseAllOrders,
        onOrderClick = viewModel::selectOrder,
        onCancelOrderClick = viewModel::cancelOrder,
        onPayOrderClick = viewModel::purchaseOrder
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun CartScreen(
    modifier: Modifier = Modifier,
    orders: List<OrderItemUiState>,
    ordersCount: Int,
    formattedTotalPrice: String,
    onOrderClick: (String) -> Unit,
    onPurchaseAllClick: () -> Unit,
    onCancelOrderClick: (String) -> Unit,
    onPayOrderClick: (String) -> Unit,
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
            listState = ordersListState,
            onOrderClick = onOrderClick,
            onCancelOrderClick = onCancelOrderClick,
            onPayOrderClick = onPayOrderClick
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
            text = MaterialTheme.strings.ordersWithCount.format(ordersCount),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.SemiBold,
        )
        CompletePurchaseBar(
            modifier = Modifier
                .constrainAs(purchaseBar) {
                    bottom.linkTo(parent.bottom)
                    centerHorizontallyTo(parent)
                },
            formattedTotalPrice = formattedTotalPrice,
            onPurchaseAllClick = onPurchaseAllClick
        )
    }
}

@Composable
private fun CompletePurchaseBar(
    modifier: Modifier = Modifier,
    formattedTotalPrice: String,
    onPurchaseAllClick: () -> Unit
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
        Text(text = MaterialTheme.strings.purchaseAllOrders)
        TextButton(onClick = onPurchaseAllClick) {
            Text(text = MaterialTheme.strings.payWithPrice.format(formattedTotalPrice))
        }
    }
}

@Composable
private fun OrdersList(
    modifier: Modifier = Modifier,
    listState: LazyListState,
    orders: List<OrderItemUiState>,
    onOrderClick: (String) -> Unit,
    onCancelOrderClick: (String) -> Unit,
    onPayOrderClick: (String) -> Unit,
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
            key = { it.orderId }
        ) {
            OrderItem(
                item = it,
                onClick = onOrderClick,
                onCancelClick = onCancelOrderClick,
                onPayClick = onPayOrderClick
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LazyItemScope.OrderItem(
    modifier: Modifier = Modifier,
    item: OrderItemUiState,
    onClick: (String) -> Unit,
    onCancelClick: (String) -> Unit,
    onPayClick: (String) -> Unit,
) {
    Surface(
        modifier = Modifier.then(modifier),
        onClick = { onClick(item.orderId) }
    ) {
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .padding(8.dp),
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
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
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
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.End
                ) {
                    OutlinedButton(
                        modifier = Modifier
                            .defaultMinSize(minHeight = 24.dp)
                            .padding(horizontal = 4.dp),
                        contentPadding = PaddingValues(),
                        onClick = { onCancelClick(item.orderId) }
                    ) {
                        Text(
                            text = MaterialTheme.strings.cancel,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    ElevatedButton(
                        modifier = Modifier.defaultMinSize(minHeight = 24.dp),
                        contentPadding = PaddingValues(),
                        onClick = { onPayClick(item.orderId) }
                    ) {
                        Text(
                            text = MaterialTheme.strings.pay,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

            }
        }
    }
}