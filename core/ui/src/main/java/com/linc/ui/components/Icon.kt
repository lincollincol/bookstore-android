package com.linc.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.linc.ui.icon.IconWrapper

@Composable
fun SimpleIcon(
    modifier: Modifier = Modifier,
    icon: IconWrapper,
    contentDescription: String? = null,
    tint: Color = LocalContentColor.current
) {
    when(icon) {
        is IconWrapper.Vector -> Icon(icon.imageVector, contentDescription, modifier, tint)
        is IconWrapper.Drawable -> Icon(painterResource(id = icon.id), contentDescription, modifier, tint)
    }
}