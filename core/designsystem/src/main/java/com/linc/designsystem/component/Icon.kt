package com.linc.designsystem.component

import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.linc.designsystem.icon.IconWrapper

@Composable
fun SimpleIcon(
    icon: IconWrapper,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    tint: Color = LocalContentColor.current
) {
    when(icon) {
        is IconWrapper.Vector -> Icon(icon.imageVector, contentDescription, modifier, tint)
        is IconWrapper.Drawable -> Icon(painterResource(id = icon.id), contentDescription, modifier, tint)
    }
}