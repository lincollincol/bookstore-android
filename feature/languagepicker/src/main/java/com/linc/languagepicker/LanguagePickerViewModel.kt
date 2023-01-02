package com.linc.languagepicker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linc.data.repository.PreferencesRepository
import com.linc.navigation.DefaultRouteNavigator
import com.linc.navigation.RouteNavigator
import com.linc.ui.extensions.languagesEquals
import com.linc.ui.extensions.tagsEquals
import com.linc.ui.state.UiStateHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class LanguagePickerViewModel @Inject constructor(
    defaultRouteNavigator: DefaultRouteNavigator,
    private val preferencesRepository: PreferencesRepository
) : ViewModel(), UiStateHolder<LanguagePickerUiState>, RouteNavigator by defaultRouteNavigator {

    override val uiState: StateFlow<LanguagePickerUiState> = combine(
        preferencesRepository.getAvailableLocalesStream(),
        preferencesRepository.getAppLocaleStream()
    ) { availableLocales, appLocale ->
        LanguagePickerUiState(
            locale = appLocale?.language.orEmpty(),
            languages = availableLocales.map { it.toUiState(it.languagesEquals(appLocale)) }
        )
    }
        /*preferencesRepository.getAvailableLocalesStream().map { availableLocales ->
            Locale.ENGLISH
            LanguagePickerUiState(availableLocales.map { it.toUiState(false) })
        }*/
        .catch { it.printStackTrace() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = LanguagePickerUiState()
        )

    fun selectLanguage(code: String) {
        viewModelScope.launch {
            try {
                preferencesRepository.saveLocale(code)
                preferencesRepository.fetchLatestLocale()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}