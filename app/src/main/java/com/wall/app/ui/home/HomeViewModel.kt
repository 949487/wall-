package com.wall.app.ui.home

import androidx.lifecycle.ViewModel
import com.wall.app.data.model.XPathSource
import com.wall.app.data.repository.XPathSourceRepository
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel(
    private val repository: XPathSourceRepository = XPathSourceRepository(),
) : ViewModel() {
    val sources: StateFlow<List<XPathSource>> = repository.observeSources()

    fun toggleEnabled(id: String, enabled: Boolean) = repository.toggle(id, enabled)
}
