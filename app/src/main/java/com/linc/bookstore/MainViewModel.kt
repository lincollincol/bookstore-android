package com.linc.bookstore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linc.data.repository.BooksRepository
import com.linc.data.repository.PreferencesRepository
import com.linc.ui.state.UiStateHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val preferencesRepository: PreferencesRepository
) : ViewModel(), UiStateHolder<MainUiState> {

    override val uiState: StateFlow<MainUiState> = preferencesRepository.getLocaleStream()
        .catch { it.printStackTrace() }
        .filterNotNull()
        .map {
            MainUiState(locale = it)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            MainUiState()
        )

}