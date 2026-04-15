package com.henry.core.network.di

import org.junit.Test
import com.henry.core.BuildConfig
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import okhttp3.Interceptor
import okhttp3.Request
import org.junit.Assert.assertEquals


class ApiKeyInterceptorTest {

    @Test
    fun `adds api key header to request`() {
        val interceptor = ApiKeyInterceptor()

        val originalRequest = Request
            .Builder()
            .url("https://newsapi.org/v2/top-headlines")
            .build()

        val chain = mockk<Interceptor.Chain>()
        every { chain.request() } returns originalRequest
        every { chain.proceed(any()) } returns mockk(relaxed = true)

        interceptor.intercept(chain)

        val slot = slot<Request>()
        verify { chain.proceed(capture(slot)) }

        assertEquals(BuildConfig.NEWS_API_KEY, slot.captured.header("X-Api-Key"))
    }
}