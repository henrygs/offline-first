package com.henry.offline_first.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.henry.domain.model.Article
import com.henry.domain.usecase.GetTopHeadlinesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getTopHeadlinesUseCase: GetTopHeadlinesUseCase
) : ViewModel() {
    val uiState: StateFlow<NewsUiState> =
        getTopHeadlinesUseCase("br")
            .map<List<Article>, NewsUiState> { articles ->
                NewsUiState.Success(articles)
            }
            .onStart {
                emit(NewsUiState.Loading)
            }
            .catch { e ->
                emit(NewsUiState.Error(e.message ?: "Unknown error"))

            }
            .flowOn(Dispatchers.IO)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = NewsUiState.Loading
            )
//    fun fetchNews() {
//        _uiState.value = NewsUiState.Loading
//        viewModelScope.launch {
//            try {
//                val articles = getTopHeadlinesUseCase("br")
//
//                    .flowOn(Dispatchers.IO)
//                    .stateIn(
//                        viewModelScope,
//                        SharingStarted.WhileSubscribed(5_000),
//                        emptyList()
//                    )
//                _uiState.value = NewsUiState.Success(articles)
//            } catch (e: Exception) {
//                _uiState.value = NewsUiState.Error(e.message ?: "Unknown error")
//            }
//        }
//    }
}
