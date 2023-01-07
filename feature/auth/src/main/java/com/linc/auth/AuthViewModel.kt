package com.linc.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linc.auth.navigation.AuthNavigationState
import com.linc.common.coroutines.extensions.EMPTY
import com.linc.data.repository.PaymentsRepository
import com.linc.data.repository.SubjectsRepository
import com.linc.data.repository.UsersRepository
import com.linc.model.Subject
import com.linc.navigation.DefaultRouteNavigator
import com.linc.navigation.RouteNavigator
import com.linc.ui.state.UiStateHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    defaultRouteNavigator: DefaultRouteNavigator,
    subjectsRepository: SubjectsRepository,
    private val usersRepository: UsersRepository,
    private val paymentsRepository: PaymentsRepository
) : ViewModel(), UiStateHolder<AuthUiState>, RouteNavigator by defaultRouteNavigator {

    private val loadingState = MutableStateFlow(false)

    private val nameState = MutableStateFlow(String.EMPTY)

    override val uiState: StateFlow<AuthUiState> = combine(
        loadingState,
        nameState,
        subjectsRepository.getPrimarySubjectsStream()
    ) { isLoading, name, interests ->
        AuthUiState(
            name = name,
            interests = interests.map(Subject::name),
            isLoading = isLoading
        )
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = AuthUiState()
        )

    fun finishAuth() {
        viewModelScope.launch {
            try {
                loadingState.update { true }
                usersRepository.createUser(rawUiState.name)
                paymentsRepository.createCustomer()
                navigateTo(AuthNavigationState.Main)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                loadingState.update { false }
            }
        }
    }

    fun updateName(name: String) {
        nameState.update { name }
    }

    fun editInterests() {
        navigateTo(AuthNavigationState.Interests)
    }

}
