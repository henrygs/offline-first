package com.henry.offline_first.viewmodel

import app.cash.turbine.test
import com.henry.domain.model.Article
import com.henry.domain.usecase.GetTopHeadlinesUseCase
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class NewsViewModelTest {

    @get:Rule private val mainDispatcherRule = MainDispatcherRule()
    private val newsUseCase= mockk<GetTopHeadlinesUseCase>()
    private val viewModel by lazy { NewsViewModel(newsUseCase) }


    @Test
    fun `quando usecase retorna artigos, estado deve ser Success`() = runTest {
        // Arrange
        every { newsUseCase.invoke("br") } returns flowOf(articlesFake)

        // Act
        viewModel.uiState.test {
            // Assert
            assertEquals(NewsUiState.Loading, awaitItem())
            assertEquals(NewsUiState.Success(articlesFake), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `quando usecase lanca exception, estado deve ser Error`() = runTest {
        // Arrange
        every { newsUseCase.invoke("br") } returns flow { throw Exception("Network error") }

        // Act
        viewModel.uiState.test {
            // Assert
            assertEquals(NewsUiState.Loading, awaitItem())
            assertEquals(NewsUiState.Error("Network error"), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    private val articlesFake: List<Article> = listOf(
        Article(
            title = "Title",
            description = "Description",
            url = "https://example.com/fake-article",
            urlToImage = "https://example.com/fake-image.jpg",
            publishedAt = "2024-06-01T12:00:00Z",
            author = "Author"
        )
    )
}