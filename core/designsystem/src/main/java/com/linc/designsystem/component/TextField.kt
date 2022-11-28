package com.linc.designsystem.component

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.linc.designsystem.icon.BookstoreIcons
import com.linc.designsystem.icon.IconWrapper
import com.linc.designsystem.icon.asIconWrapper
import com.linc.designsystem.theme.BookstoreTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookstoreTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    hint: String = "",
    trailingIcon: IconWrapper? = null,
    leadingIcon: IconWrapper? = null,
) {
    TextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        shape = MaterialTheme.shapes.medium,
        placeholder = { Text(text = hint) },
        trailingIcon = trailingIcon?.let{ { SimpleIcon(icon = it) } },
        leadingIcon = leadingIcon?.let{ { SimpleIcon(icon = it) } },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}

@Preview
@Composable
private fun BookmarksTextFieldPreview() {
    BookstoreTheme {
        BookstoreTextField(value = "", onValueChange = {})
    }
}