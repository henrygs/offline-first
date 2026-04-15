package com.henry.data.remote.dto

import junit.framework.TestCase.assertEquals
import org.junit.Test

class ArticleDtoTest {

    @Test
    fun `toDomain maps all fields correctly`() {
        val dto = ArticleDto(
            title = "Test Title",
            description = "Test Description",
            author = "Test Author",
            url = "https://example.com/article",
            publishedAt = "2024-06-01T12:00:00Z",
            urlToImage = "https://example.com/image.jpg"
        )
        val article = dto.toDomain()

        assertEquals("Test Title", article.title)
        assertEquals("Test Description", article.description)
        assertEquals("Test Author", article.author)
        assertEquals("https://example.com/article", article.url)
        assertEquals("2024-06-01T12:00:00Z", article.publishedAt)
        assertEquals("https://example.com/image.jpg", article.urlToImage)
    }

    @Test
    fun `toDomain handles null fields`() {
        val dto = ArticleDto(
            title = null,
            description = null,
            author = null,
            url = null,
            publishedAt = null,
            urlToImage = null
        )
        val article = dto.toDomain()

        assertEquals(null, article.title)
        assertEquals(null, article.description)
        assertEquals(null, article.author)
        assertEquals(null, article.url)
        assertEquals(null, article.publishedAt)
        assertEquals(null, article.urlToImage)
    }
}