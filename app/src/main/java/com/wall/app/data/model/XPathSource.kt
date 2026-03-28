package com.wall.app.data.model

import java.util.UUID

data class XPathSource(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val url: String,
    val xpath: String,
    val updateIntervalMs: Long = 60 * 60 * 1000,
    val enabled: Boolean = true,
    val lastSuccess: Long = 0L,
    val lastError: String? = null,
)
