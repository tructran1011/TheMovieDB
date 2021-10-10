package com.me.themoviedb.testutil

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source

const val LISTING_PAGE_1 = "listing_page_1.json"
const val MOVIE_DETAILS = "movie_details.json"
const val MOVIE_CREDITS = "movie_credits.json"

fun MockWebServer.enqueueFile(fileName: String) {
    enqueue(MockResponse().setBodyFromFile(fileName))
}

fun MockResponse.setBodyFromFile(fileName: String): MockResponse {
    val inputStream = javaClass.classLoader!!.getResourceAsStream("api_response/$fileName")
    val source = inputStream.source().buffer()
    val body = source.readString(Charsets.UTF_8)
    return setBody(body)
}