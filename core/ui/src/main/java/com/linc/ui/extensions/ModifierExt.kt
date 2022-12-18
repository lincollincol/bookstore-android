package com.linc.ui.extensions

import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.unit.Dp

@Composable
fun Modifier.heightInPx(height: Float) = this.then(
    height(with(LocalDensity.current) { height.toDp() })
)