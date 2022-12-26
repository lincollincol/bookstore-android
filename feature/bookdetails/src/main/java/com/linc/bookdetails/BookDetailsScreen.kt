package com.linc.bookdetails

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.ImageVector
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.palette.graphics.Palette
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.linc.bookdetails.navigation.BookDetailsNavigationState
import com.linc.designsystem.component.SimpleIcon
import com.linc.ui.icon.BookstoreIcons
import com.linc.ui.icon.asIconWrapper
import com.linc.designsystem.extensions.getVibrantColor
import com.linc.navigation.NavigationState
import com.linc.navigation.observeNavigation
import java.util.*


@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun BookDetailsRoute(
    modifier: Modifier = Modifier,
    viewModel: BookDetailsViewModel = hiltViewModel(),
    navigateToCart: () -> Unit,
    navigateBack: () -> Unit,
) {
    val bookUiState: BookUiState by viewModel.uiState.collectAsStateWithLifecycle()
    viewModel.observeNavigation {
        when(it) {
            BookDetailsNavigationState.Cart -> navigateToCart()
            NavigationState.Back -> navigateBack()
        }
    }
    BookDetailsScreen(
        modifier = modifier,
        bookUiState = bookUiState,
        onBackClick = viewModel::navigateBack,
        onCartClick = viewModel::addToCart,
        onBookmarkClick = viewModel::bookmarkBook,
        onShareClick = {}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun BookDetailsScreen(
    modifier: Modifier = Modifier,
    bookUiState: BookUiState,
    onCartClick: () -> Unit,
    onBackClick: () -> Unit,
    onBookmarkClick: () -> Unit,
    onShareClick: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .then(modifier)
    ) {
        val (toolbar, content, buyButton) = createRefs()
        BookDetailsAppBar(
            modifier = Modifier.constrainAs(toolbar) {
                top.linkTo(parent.top)
                centerHorizontallyTo(parent)
            },
            scrollBehavior = scrollBehavior,
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
        AddToCartButton(
            modifier = Modifier.constrainAs(buyButton) {
                bottom.linkTo(parent.bottom)
                centerHorizontallyTo(parent)
                width = Dimension.fillToConstraints
            },
            isOrdered = bookUiState.isOrdered,
            price = bookUiState.price.toString(),
            currency = "usd",
            onAddToCartClick = onCartClick
        )
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
            text = book.authors,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Medium
        )
        Text(
            modifier = Modifier.padding(top = 16.dp),
            text = book.description,
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
    val inf = rememberInfiniteTransition()
    val shadow by inf.animateValue(
        initialValue = 32.dp,
        targetValue = 64.dp,
        typeConverter = Dp.VectorConverter,
        animationSpec = infiniteRepeatable(
            animation = tween(2000),
            repeatMode = RepeatMode.Reverse
        )
    )
    var vibrant by remember { mutableStateOf(Color.Transparent) }
    Surface(
        modifier = Modifier
            .shadow(
                elevation = shadow,
                spotColor = vibrant,
                clip = false,
                shape = CircleShape
            )
            .then(modifier),
        shape = MaterialTheme.shapes.medium,
        shadowElevation = 4.dp
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxSize(0.8f),
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .allowHardware(false)
                .build(),
            contentDescription = null,
            onSuccess = {
                Palette.Builder(it.result.drawable.toBitmap()).generate { palette ->
                    vibrant = palette.getVibrantColor()
                }
            },
            contentScale = ContentScale.FillWidth
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BookDetailsAppBar(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior,
    isBookmarked: Boolean,
    onBackClick: () -> Unit,
    onBookmarkClick: () -> Unit,
    onShareClick: () -> Unit
) {
    var showMenu by remember {
        mutableStateOf(false)
    }
    val bookmarkIcon = remember(isBookmarked) {
        if(isBookmarked) BookstoreIcons.BookmarkAdded else BookstoreIcons.BookmarkAdd
    }
    TopAppBar(
        modifier = Modifier
            .zIndex(1f)
            .then(modifier),
        title = {},
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                SimpleIcon(icon = BookstoreIcons.ArrowBack.asIconWrapper())
            }
        },
        actions = {
            IconButton(onClick = onBookmarkClick) {
                SimpleIcon(icon = bookmarkIcon.asIconWrapper())
            }
            IconButton(onClick = { showMenu = true }) {
                SimpleIcon(icon = BookstoreIcons.MoreVertical.asIconWrapper())
            }
            DropdownMenu(
                expanded = showMenu,
                onDismissRequest = { showMenu = false }) {
                DropdownMenuItem(
                    leadingIcon = { SimpleIcon(icon = BookstoreIcons.Share.asIconWrapper()) },
                    text = { Text(text = stringResource(id = R.string.share)) },
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

@Composable
private fun AddToCartButton(
    modifier: Modifier = Modifier,
    price: String,
    currency: String,
    isOrdered: Boolean,
    onAddToCartClick: () -> Unit 
) {
    val cartButtonText = remember(isOrdered) {
        when {
            isOrdered -> R.string.go_to_cart
            else -> R.string.add_to_cart_with_price
        }
    }
    ElevatedButton(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .then(modifier),
        onClick = onAddToCartClick,
        elevation = ButtonDefaults.elevatedButtonElevation(
            defaultElevation = 4.dp
        )
    ) {
        Text(text = stringResource(id = cartButtonText, "12.8$"))
    }
}