package com.wall.app.data.repository

import com.wall.app.data.model.XPathSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class XPathSourceRepository {
    private val state = MutableStateFlow(
        listOf(
            XPathSource(
                name = "壁纸喵首页",
                url = "http://service.picasso.adesk.com/v1/vertical/vertical?disorder=true&limit=16&skip=0&adult=false&first=1&order=hot",
                xpath = "//img/@src",
            )
        )
    )

    fun observeSources(): StateFlow<List<XPathSource>> = state.asStateFlow()

    fun addSource(source: XPathSource) {
        state.value = state.value + source
    }

    fun toggle(sourceId: String, enabled: Boolean) {
        state.value = state.value.map { if (it.id == sourceId) it.copy(enabled = enabled) else it }
    }
}
