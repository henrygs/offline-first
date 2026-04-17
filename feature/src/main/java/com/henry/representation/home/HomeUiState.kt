package com.henry.representation.home

import com.henry.domain.model.Article

sealed interface HomeUiState {
    data object Loading : HomeUiState
    data class Success(val articles: List<Article>) : HomeUiState
    data class Error(val message: String) : HomeUiState
}
