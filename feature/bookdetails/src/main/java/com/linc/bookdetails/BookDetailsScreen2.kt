package com.linc.bookdetails

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
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
import androidx.palette.graphics.Palette
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.linc.designsystem.component.SimpleIcon
import com.linc.ui.icon.BookstoreIcons
import com.linc.ui.icon.asIconWrapper
import com.linc.designsystem.extensions.getVibrantColor
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun BookDetailsScreen(
    modifier: Modifier = Modifier,
    bookUiState: BookUiState,
    onCartClick: (String) -> Unit,
    onBackClick: () -> Unit,
    onBookmarkClick: () -> Unit,
    onShareClick: () -> Unit
) {
    var showMenu by remember {
        mutableStateOf(false)
    }
    val scroll = TopAppBarDefaults.pinnedScrollBehavior()
    val imageTopMargin = remember {
        56.dp + 24.dp
    }
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scroll.nestedScrollConnection)
    ) {
        val (toolbar, content, buyButton) = createRefs()
        TopAppBar(
            modifier = Modifier
                .zIndex(1f)
                .constrainAs(toolbar) {
                    top.linkTo(parent.top)
                    centerHorizontallyTo(parent)
                },
            title = {},
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    SimpleIcon(icon = BookstoreIcons.ArrowBack.asIconWrapper())
                }
            },
            actions = {
                IconButton(onClick = onBookmarkClick) {
                    SimpleIcon(icon = BookstoreIcons.OutlinedBookmark.asIconWrapper())
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
            scrollBehavior = scroll
        )
        Column(
            modifier = Modifier
                .constrainAs(content) {
                    top.linkTo(toolbar.top)
                    bottom.linkTo(parent.bottom)
                    centerHorizontallyTo(parent)
                    height = Dimension.fillToConstraints
                }
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BookImage(
                modifier = Modifier.padding(top = imageTopMargin),
                imageUrl = bookUiState.imageUrl,
            )
            BookContent(
                modifier = Modifier.padding(
                    top = 24.dp,
                    bottom = 56.dp,
                    start = 24.dp,
                    end = 24.dp
                ),
                book = bookUiState
            )
        }
        ElevatedButton(
            modifier = Modifier
                .padding(8.dp)
                .constrainAs(buyButton) {
                    bottom.linkTo(parent.bottom)
                    centerHorizontallyTo(parent)
                    width = Dimension.fillToConstraints
                },
            onClick = { onCartClick(bookUiState.id) },
            elevation = ButtonDefaults.elevatedButtonElevation(
                defaultElevation = 4.dp
            )
        ) {
            Text(text = stringResource(id = R.string.add_to_cart_with_price, "12.8$"))
        }
    }
}

@Composable
fun BookContent(
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
    val elevation by inf.animateValue(
        initialValue = 16.dp,
        targetValue = 32.dp,
        typeConverter = Dp.VectorConverter,
        animationSpec = infiniteRepeatable(
            animation = tween(2000),
            repeatMode = RepeatMode.Reverse
        )
    )
    var vibrant by remember { mutableStateOf(Color.Transparent) }
    Surface(
        modifier = Modifier
//            .fillMaxSize(0.8f)
//            .aspectRatio(ASPECT_RATIO_4_5)
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

/*

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
internal fun BookDetailsScreen(
    modifier: Modifier = Modifier,
    bookUiState: BookUiState,
    onCart: (String) -> Unit,
    onBack: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    println(scrollBehavior.isPinned)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection)
    ) {
        LargeTopAppBar(
            title = {
                Text(
                    text = bookUiState.title,
                    overflow = TextOverflow.Ellipsis,
//                    maxLines = if(scrollBehavior.state.) 1 else 3
                )
            },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    SimpleIcon(icon = BookstoreIcons.ArrowBack.asIconWrapper())
                }
            },
            actions = {
                IconButton(onClick = onBack) {
                    SimpleIcon(icon = BookstoreIcons.OutlinedBookmark.asIconWrapper())
                }
            },
            scrollBehavior = scrollBehavior
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BookImage(
                imageUrl = bookUiState.imageUrl,
            )
            BookContent(
                book = bookUiState,
                modifier = Modifier
            )
        }
    }
}
 */