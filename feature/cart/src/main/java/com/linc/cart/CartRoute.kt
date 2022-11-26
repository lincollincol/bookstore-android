package com.linc.cart

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CartRoute() {
    CartScreen()
}

@Composable
internal fun CartScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "Cart")
    }
}

@Preview
@Composable
private fun CartScreenPreview() {

}