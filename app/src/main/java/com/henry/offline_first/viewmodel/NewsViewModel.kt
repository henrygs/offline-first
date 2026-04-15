package com.henry.offline_first.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.henry.domain.model.Article
import com.henry.domain.usecase.GetTopHeadlinesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getTopHeadlinesUseCase: GetTopHeadlinesUseCase
) : ViewModel() {
    val uiState: Flow<PagingData<Article>> =
        getTopHeadlinesUseCase("us").cachedIn(viewModelScope)
//            .map<List<Article>, NewsUiState> { articles ->
//                NewsUiState.Success(articles)
//            }
//            .onStart {
//                emit(NewsUiState.Loading)
//            }
//            .catch { e ->
//                emit(NewsUiState.Error(e.message ?: "Unknown error"))
//
//            }
//            .flowOn(Dispatchers.IO)
//            .stateIn(
//                scope = viewModelScope,
//                started = SharingStarted.WhileSubscribed(5_000),
//                initialValue = NewsUiState.Loading
//            )
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
