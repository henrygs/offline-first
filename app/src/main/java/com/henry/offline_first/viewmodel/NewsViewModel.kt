package com.henry.offline_first.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.henry.domain.usecase.GetTopHeadlinesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getTopHeadlinesUseCase: GetTopHeadlinesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<NewsUiState>(NewsUiState.Loading)
    val uiState: StateFlow<NewsUiState> = _uiState

    init {
        fetchNews()
    }

    fun fetchNews() {
        _uiState.value = NewsUiState.Loading
        viewModelScope.launch {
            try {
                val articles = getTopHeadlinesUseCase("us")
                _uiState.value = NewsUiState.Success(articles)
            } catch (e: Exception) {
                _uiState.value = NewsUiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}
