package com.henry.data.repository

import com.henry.data.remote.api.NewsApiService
import com.henry.data.remote.dto.ArticleDto
import com.henry.data.remote.dto.NewsResponseDto
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.fail
import org.junit.Test

class NewsRepositoryImplTest {

    private val newsApiService = mockk<NewsApiService>()
    private val newsRepository = NewsRepositoryImpl(newsApiService)

    @Test
    fun `quando api retorna sucesso, repository retorna lista de artigos`() {
        // Arrange
        every { newsApiService.getTopHeadlines("br") } returns articlesDto

        // Act
        val result = newsRepository.getTopHeadlines("br")

        // Assert
        assertEquals(articlesDto.articles.map { it.toDomain() }, result)
    }

    @Test
    fun `quando api lanca exception, repository propaga o erro`() {
        // Arrange
        every { newsApiService.getTopHeadlines("br") } throws Exception("Network error")

        // Act + Assert
        try {
            newsRepository.getTopHeadlines("br")
            fail("Deveria ter lancado exception")
        } catch (e: Exception) {
            assertEquals("Network error", e.message)
        }
    }

    private val articlesDto = NewsResponseDto(
        status = "ok",
        totalResults = 1,
        articles = listOf(
            ArticleDto(
                title = "Article",
                description = "This is a test article.",
                author = "John Doe",
                url = "https://example.com/test-article",
                publishedAt = "2024-06-01T12:00:00Z",
                urlToImage = "https://example.com/test-image.jpg"
            )
        )
    )

}
