package com.wall.core.xpath

import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

object RuleJson {
    private val json = Json { ignoreUnknownKeys = true }
    private val listSerializer = ListSerializer(XPathRule.serializer())

    fun encode(rules: List<XPathRule>): String = json.encodeToString(listSerializer, rules)

    fun decode(text: String): List<XPathRule> = runCatching {
        if (text.trim().startsWith("[")) json.decodeFromString(listSerializer, text)
        else listOf(json.decodeFromString(XPathRule.serializer(), text))
    }.getOrDefault(emptyList())
}
