package com.linc.bookstore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linc.data.repository.PreferencesRepository
import com.linc.data.repository.UsersRepository
import com.linc.ui.state.UiStateHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val preferencesRepository: PreferencesRepository,
    private val usersRepository: UsersRepository
) : ViewModel(), UiStateHolder<MainUiState> {

    override val uiState: StateFlow<MainUiState> = combine(
        usersRepository.getAuthStateStream(),
        preferencesRepository.getAppLocaleStringsStream().filter(Map<String, String>::isNotEmpty)
    ) { authState, localeStrings ->
        MainUiState(
            authState = authState,
            localeStrings = localeStrings
        )
    }
        .onStart { preferencesRepository.fetchLatestLocale() }
        .catch { it.printStackTrace() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            MainUiState()
        )

}