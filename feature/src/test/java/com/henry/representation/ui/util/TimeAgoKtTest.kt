package com.henry.representation.ui.util

import junit.framework.TestCase.assertEquals
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class TimeAgoKtTest {

    @Test
    fun `returns empty when input is null`() {
        val result = formatTimeAgo(null)
        assertEquals("", result)
    }
    @Test
    fun `returns empty when empty`() {
        val result = formatTimeAgo("")
        assertEquals("", result)
    }
    @Test
    fun `returns now when less than 1 minute ago`() {
        val date = dateMinutesAgo(0)
        val result = formatTimeAgo(date)
        assertEquals("now", result)
    }

    @Test
    fun `returns 5 minutes when there are less than 1 hour`() {
        val date = dateMinutesAgo(5)
        val result = formatTimeAgo(date)
        assertEquals("5m", result)
    }

    @Test
    fun `returns 3 hours when there are less than 1 day`() {
        val date = dateMinutesAgo(180)
        val result = formatTimeAgo(date)
        assertEquals("3h", result)
    }

    @Test
    fun `returns 3 days when there are less than 7 days`() {
        val date = dateMinutesAgo(4320)
        val result = formatTimeAgo(date)
        assertEquals("3d", result)
    }

    @Test
    fun `returns date now when there are less than more 7 days`() {
        val date = dateMinutesAgo(11520)
        val result = formatTimeAgo(date)

        val expectedFormat = SimpleDateFormat("MMM d", Locale("pt", "BR"))
        val expectedDate = Date(System.currentTimeMillis() - 11520 * 60 * 1000)
        val expected = expectedFormat.format(expectedDate)


        assertEquals(expected, result)
    }

    private fun dateMinutesAgo(minutes: Long): String {
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
        format.timeZone = TimeZone.getTimeZone("UTC")
        val date = Date(System.currentTimeMillis() - minutes * 60 * 1000)
        return format.format(date)
    }

}
