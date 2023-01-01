package com.linc.bookdetails

import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.*
import androidx.core.graphics.drawable.toBitmap
import androidx.core.text.parseAsHtml
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.palette.graphics.Palette
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.linc.bookdetails.navigation.BookDetailsNavigationState
import com.linc.ui.components.NothingFound
import com.linc.ui.components.SimpleIcon
import com.linc.ui.icon.BookstoreIcons
import com.linc.designsystem.extensions.getVibrantColor
import com.linc.navigation.NavigationState
import com.linc.navigation.observeNavigation
import com.linc.ui.extensions.toAnnotatedString
import com.linc.ui.theme.IconWrapper
import com.linc.ui.theme.icons
import com.linc.ui.theme.strings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*


@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun BookDetailsRoute(
    modifier: Modifier = Modifier,
    viewModel: BookDetailsViewModel = hiltViewModel(),
    navigateToCart: () -> Unit,
    navigateToChooser: (Intent) -> Unit,
    navigateBack: () -> Unit,
) {
    val bookUiState: BookUiState by viewModel.uiState.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()
    viewModel.observeNavigation {
        when(it) {
            is BookDetailsNavigationState.Cart -> navigateToCart()
            is BookDetailsNavigationState.Chooser -> navigateToChooser(it.intent)
            is NavigationState.Back -> navigateBack()
        }
    }
    BookDetailsScreen(
        modifier = modifier,
        coroutineScope = coroutineScope,
        bookUiState = bookUiState,
        onBackClick = viewModel::navigateBack,
        onAddToCartClick = viewModel::addToCart,
        onOrderPayClick = {},
        onBookmarkClick = viewModel::bookmarkBook,
        onShareClick = viewModel::shareBook,
        onIncCountClick = viewModel::increaseOrderCount,
        onDecCountClick = viewModel::decreaseOrderCount
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
internal fun BookDetailsScreen(
    modifier: Modifier = Modifier,
    coroutineScope: CoroutineScope,
    bookUiState: BookUiState,
    onAddToCartClick: () -> Unit,
    onOrderPayClick: () -> Unit,
    onBackClick: () -> Unit,
    onBookmarkClick: () -> Unit,
    onShareClick: () -> Unit,
    onIncCountClick: () -> Unit,
    onDecCountClick: () -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val bottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    var toolbarHeight by remember {
        mutableStateOf(0)
    }
    val showToolbarElevation by remember(scrollBehavior.state.contentOffset) {
        derivedStateOf { scrollBehavior.state.contentOffset < -toolbarHeight }
    }
    val toolbarElevation by animateDpAsState(
        targetValue = if(showToolbarElevation) 4.dp else 0.dp
    )
    BackHandler {
        if(bottomSheetState.isVisible) {
            coroutineScope.launch { bottomSheetState.hide() }
            return@BackHandler
        }
        onBackClick()
    }
    ModalBottomSheetLayout(
        modifier = Modifier
            .fillMaxSize()
            .then(modifier),
        sheetShape = MaterialTheme.shapes.large,
        sheetElevation = 8.dp,
        sheetContent = {
            OrderBuilderBottomSheet(
                formattedPrice = bookUiState.formattedPrice,
                formattedTotalPrice = bookUiState.formattedTotalOrderPrice,
                count = bookUiState.orderCount,
                onIncCountClick = onIncCountClick,
                onDecCountClick = onDecCountClick,
                onAddToCartClick = {
                    onAddToCartClick()
                    coroutineScope.launch { bottomSheetState.hide() }
                },
                onCancelClick = {
                    coroutineScope.launch { bottomSheetState.hide() }
                }
            )
        },
        sheetState = bottomSheetState
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection)
        ) {
            val (toolbar, content, buyButton) = createRefs()
            BookDetailsAppBar(
                modifier = Modifier
                    .constrainAs(toolbar) {
                        top.linkTo(parent.top)
                        centerHorizontallyTo(parent)
                    }
                    .onGloballyPositioned {
                        toolbarHeight = it.size.height
                    }
                    .shadow(toolbarElevation),
                scrollBehavior = scrollBehavior,
                isBookExist = bookUiState.isBookExist,
                isSoldOut = !bookUiState.availableForSale,
                isBookmarked = bookUiState.isBookmarked,
                onBackClick = onBackClick,
                onBookmarkClick = onBookmarkClick,
                onShareClick = onShareClick
            )
            BookContent(
                modifier = Modifier.constrainAs(content) {
                    top.linkTo(toolbar.top)
                    bottom.linkTo(parent.bottom)
                    centerHorizontallyTo(parent)
                    height = Dimension.fillToConstraints
                },
                book = bookUiState
            )
            if(bookUiState.canOrderBook) {
                AddToCartButton(
                    modifier = Modifier.constrainAs(buyButton) {
                        width = Dimension.fillToConstraints
                        bottom.linkTo(parent.bottom)
                        centerHorizontallyTo(parent)
                    },
                    formattedPrice = bookUiState.formattedPrice,
                    isOrdered = bookUiState.isOrdered,
                    onAddToCartClick = {
                        coroutineScope.launch {
                            bottomSheetState.show()
                        }
                    },
                    onOrderPayClick = onOrderPayClick
                )
            }
        }
    }
}

@Composable
fun OrderBuilderBottomSheet(
    modifier: Modifier = Modifier,
    formattedPrice: String,
    formattedTotalPrice: String,
    count: Int,
    onIncCountClick: () -> Unit,
    onDecCountClick: () -> Unit,
    onAddToCartClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    Column(modifier = Modifier
        .padding(horizontal = 24.dp)
        .then(modifier)
    ) {
        Text(
            modifier = Modifier
                .padding(vertical = 16.dp),
            text = MaterialTheme.strings.createOrder,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold
        )
        Row(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = MaterialTheme.strings.bookPriceLabel)
            Text(text = formattedPrice)
        }
        Row(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = MaterialTheme.strings.quantityLabel)
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CircleButton(
                    icon = MaterialTheme.icons.remove,
                    onClick = onDecCountClick
                )
                Text(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    text = count.toString()
                )
                CircleButton(
                    icon = MaterialTheme.icons.add,
                    onClick = onIncCountClick
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Divider()
        Row(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = MaterialTheme.strings.totalPriceLabel,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = formattedTotalPrice,
                fontWeight = FontWeight.SemiBold
            )
        }
        Divider()
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            OutlinedButton(
                modifier = Modifier.padding(horizontal = 4.dp),
                onClick = onCancelClick
            ) {
                Text(text = MaterialTheme.strings.cancel)
            }
            ElevatedButton(
                onClick = onAddToCartClick
            ) {
                Text(text = MaterialTheme.strings.addToCart)
            }
        }
    }
}

@Composable
private fun BookContent(
    modifier: Modifier = Modifier,
    book: BookUiState
) {
    val imageTopMargin = remember {
        56.dp + 24.dp
    }
    val bottomPadding = remember {
        ButtonDefaults.MinHeight + 36.dp
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .then(modifier),
        contentAlignment = Alignment.Center
    ) {
        if(book.isLoading) {
            CircularProgressIndicator()
        } else if(!book.isBookExist) {
            NothingFound(
                icon = MaterialTheme.icons.bookNotFound,
                message = MaterialTheme.strings.bookNotFound
            )
        } else {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .then(modifier),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                BookImage(
                    modifier = Modifier.padding(top = imageTopMargin),
                    imageUrl = book.imageUrl,
                )
                BookDescription(
                    modifier = Modifier.padding(
                        top = 24.dp,
                        bottom = bottomPadding,
                        start = 24.dp,
                        end = 24.dp
                    ),
                    book = book
                )
            }
        }
    }
}

@Composable
fun BookDescription(
    modifier: Modifier = Modifier,
    book: BookUiState
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .then(modifier)
    ) {
        Text(
            modifier = Modifier.padding(top = 16.dp),
            text = book.title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = book.formattedAuthors,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Medium
        )
        Text(
            modifier = Modifier.padding(top = 16.dp),
            text = book.description.parseAsHtml().toAnnotatedString(),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Justify
        )
    }
}

@Composable
private fun BookImage(
    modifier: Modifier = Modifier,
    imageUrl: String
) {
    val infinity = rememberInfiniteTransition()
    val imageModel = ImageRequest.Builder(LocalContext.current)
        .data(imageUrl)
        .crossfade(true)
        .allowHardware(false)
        .size(coil.size.Size.ORIGINAL)
        .build()
    var shadowAmbientColor by remember { mutableStateOf(Color.Transparent) }
    val animatedShadowAmbientColor by animateColorAsState(targetValue = shadowAmbientColor)
    var minShadowAmbientElevation by remember { mutableStateOf(0.dp) }
    var maxShadowAmbientElevation by remember { mutableStateOf(0.dp) }
    var bookElevation by remember { mutableStateOf(0.dp) }
    val animateBookElevation by animateDpAsState(targetValue = bookElevation)
    val painter = rememberAsyncImagePainter(
        model = imageModel,
        onSuccess = {
            minShadowAmbientElevation = 32.dp
            maxShadowAmbientElevation = 64.dp
            bookElevation = 6.dp
            Palette.Builder(it.result.drawable.toBitmap()).generate { palette ->
                shadowAmbientColor = palette.getVibrantColor()
            }
        }
    )
    val ambientShadowElevation by infinity.animateValue(
        initialValue = minShadowAmbientElevation,
        targetValue = maxShadowAmbientElevation,
        typeConverter = Dp.VectorConverter,
        animationSpec = infiniteRepeatable(
            animation = tween(2000),
            repeatMode = RepeatMode.Reverse
        )
    )
    Box(
        modifier = Modifier
            .shadow(
                elevation = ambientShadowElevation,
                spotColor = animatedShadowAmbientColor,
                clip = false,
                shape = CircleShape
            )
            .then(modifier),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(painter.state is AsyncImagePainter.State.Loading) {
            CircularProgressIndicator()
        }
        Surface(
            shape = MaterialTheme.shapes.medium,
            shadowElevation = animateBookElevation
        ) {
            Image(
                modifier = Modifier
                    .animateContentSize(tween(500))
                    .fillMaxSize(0.8f),
                painter = painter,
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            )
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BookDetailsAppBar(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior,
    isBookExist: Boolean,
    isSoldOut: Boolean,
    isBookmarked: Boolean,
    onBackClick: () -> Unit,
    onBookmarkClick: () -> Unit,
    onShareClick: () -> Unit
) {
    var showMenu by remember {
        mutableStateOf(false)
    }
    val bookmarkIcon = with(MaterialTheme.icons) {
        remember(isBookmarked) { if(isBookmarked) bookmarkAdded else bookmarkAdd }
    }

    TopAppBar(
        modifier = Modifier
            .zIndex(1f)
            .then(modifier),
        title = {},
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                SimpleIcon(icon = MaterialTheme.icons.arrowBack)
            }
        },
        actions = {
            if(isSoldOut) {
                SimpleIcon(icon = MaterialTheme.icons.soldOut)
            }
            if(isBookExist) {
                IconButton(onClick = onBookmarkClick) {
                    SimpleIcon(icon = bookmarkIcon)
                }
                IconButton(onClick = { showMenu = true }) {
                    SimpleIcon(icon = MaterialTheme.icons.moreVertical)
                }
            }
            DropdownMenu(
                expanded = showMenu,
                onDismissRequest = { showMenu = false }) {
                DropdownMenuItem(
                    leadingIcon = { SimpleIcon(icon = MaterialTheme.icons.share) },
                    text = { Text(text = MaterialTheme.strings.share) },
                    onClick = onShareClick
                )
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = Color.Transparent,
            scrolledContainerColor = MaterialTheme.colorScheme.surface
        ),
        scrollBehavior = scrollBehavior
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CircleButton(
    modifier: Modifier = Modifier,
    icon: IconWrapper,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier.size(24.dp),
        color = MaterialTheme.colorScheme.surface,
        shape = CircleShape,
        shadowElevation = 2.dp,
        onClick = onClick
    ) {
        SimpleIcon(
            modifier = Modifier
                .size(24.dp)
                .padding(4.dp),
            icon = icon
        )
    }
}

@Composable
private fun AddToCartButton(
    modifier: Modifier = Modifier,
    isOrdered: Boolean,
    formattedPrice: String,
    onAddToCartClick: () -> Unit,
    onOrderPayClick: () -> Unit,
) {
    val cartButtonText = with(MaterialTheme.strings) {
        remember(isOrdered, formattedPrice) {
            if (isOrdered) payForOrder
            else buyWithPrice.format(formattedPrice)
        }
    }
    ElevatedButton(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .then(modifier),
        onClick = if(isOrdered) onOrderPayClick else onAddToCartClick,
        elevation = ButtonDefaults.elevatedButtonElevation(
            defaultElevation = 4.dp
        )
    ) {
        Text(text = cartButtonText)
    }
}