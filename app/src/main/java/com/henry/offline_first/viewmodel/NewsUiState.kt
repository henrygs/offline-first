package com.henry.offline_first.viewmodel

import com.henry.domain.model.Article

sealed interface NewsUiState {
    data object Loading : NewsUiState
    data class Success(val articles: List<Article>) : NewsUiState
    data class Error(val message: String) : NewsUiState
}
