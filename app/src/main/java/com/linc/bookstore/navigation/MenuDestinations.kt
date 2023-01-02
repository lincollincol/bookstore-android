package com.linc.bookstore.navigation

import android.icu.text.CaseMap.Title
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.linc.books.navigation.booksRoute
import com.linc.cart.navigation.cartRoute
import com.linc.ui.icon.BookstoreIcons
import com.linc.preferences.navigation.preferencesRoute
import com.linc.ui.theme.IconWrapper
import com.linc.ui.theme.icons
import com.linc.ui.theme.strings

enum class MenuDestinations(
    val icon: @Composable () -> IconWrapper,
    val route: String
) {
    Books(
        icon = { MaterialTheme.icons.searchBooks },
        route = booksRoute
    ),
    Cart(
        icon = { MaterialTheme.icons.cart },
        route = cartRoute
    ),
    Preferences(
        icon = { MaterialTheme.icons.starOutlined },
        route = preferencesRoute
    );
    companion object {
        @JvmStatic
        fun routes(): Array<String> = values().map(MenuDestinations::route).toTypedArray()
        @JvmStatic
        fun fromRoute(route: String?): MenuDestinations? =
            route?.let { values().find { dest -> dest.route.equals(it, true) } }
    }
}
