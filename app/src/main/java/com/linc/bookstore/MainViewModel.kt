package com.linc.bookstore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linc.data.repository.BooksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val booksRepository: BooksRepository
) : ViewModel() {

    init {
        loadBooks()
    }

    fun loadBooks() {
        viewModelScope.launch {
            try {
//                booksRepository.getBooks()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}