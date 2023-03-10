package com.linc.designsystem.component

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.linc.ui.components.SimpleIcon
import com.linc.ui.theme.BookstoreTheme
import com.linc.ui.theme.IconWrapper

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookstoreTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    hint: String = "",
    maxLines: Int = 1,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    imeAction: ImeAction = ImeAction.Done,
    keyboardType: KeyboardType = KeyboardType.Text,
    capitalization: KeyboardCapitalization = KeyboardCapitalization.Sentences,
    trailingIcon: IconWrapper? = null,
    leadingIcon: IconWrapper? = null,
    onKeyboardDone: () -> Unit = {},
    onKeyboardNext: () -> Unit = {},
    onTrailingIconClick: () -> Unit = {},
    onLeadingIconClick: () -> Unit = {},
) {
    TextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        shape = MaterialTheme.shapes.medium,
        placeholder = { Text(text = hint, maxLines = maxLines, overflow = overflow,) },
        trailingIcon = trailingIcon.buildComposable {
            IconButton(onClick = onTrailingIconClick) {
                SimpleIcon(icon = it)
            }
        },
        leadingIcon = leadingIcon.buildComposable {
            IconButton(onClick = onLeadingIconClick) {
                SimpleIcon(icon = it)
            }
        },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = imeAction,
            keyboardType = keyboardType,
            capitalization = capitalization
        ),
        keyboardActions = KeyboardActions(
            onDone = { onKeyboardDone() },
            onNext = { onKeyboardNext() }
        ),
        maxLines = maxLines
    )
}

private fun IconWrapper?.buildComposable(
    icon: @Composable (IconWrapper) -> Unit
) : @Composable (() -> Unit)? = this?.let { { icon(it) } }

@Preview
@Composable
private fun BookmarksTextFieldPreview() {
    BookstoreTheme {
        BookstoreTextField(value = "", onValueChange = {})
    }
}