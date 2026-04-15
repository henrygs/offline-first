package com.henry.domain.usecase

import app.cash.turbine.test
import com.henry.domain.model.Article
import com.henry.domain.repository.NewsRepository
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test


class GetTopHeadlinesUseCaseTest  {
    private val repository = mockk<NewsRepository>()
    private val useCase = GetTopHeadlinesUseCase(repository)

    @Test
    fun `quando repository retorna artigos, usecase emite lista corretamente`() = runTest {
        // Arrange
        val fakeArticles = listOf(
            Article(
                title = "Title",
                description = "Description",
                url = "https://example.com/fake-article",
                urlToImage = "https://example.com/fake-image.jpg",
                publishedAt = "2024-06-01T12:00:00Z",
                author = "Author"
            )
        )
        every { repository.getTopHeadlines("br") } returns fakeArticles

        // Act
        useCase("br").test {
            // Assert
            assertEquals(fakeArticles, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `quando repository lanca exception, usecase propaga o erro`() = runTest {
        // Arrange
        every { repository.getTopHeadlines("br") } throws Exception("Network error")

        // Act
        useCase("br").test {
            // Assert
            assertEquals("Network error", awaitError().message)
        }
    }
}