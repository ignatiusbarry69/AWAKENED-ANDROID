package com.example.storyapp

import com.example.storyapp.logic.repository.retrofit.responses.ListStoryItem

object DataDummy {

    fun generateDummyQuoteResponse(): List<ListStoryItem> {
        val items: MutableList<ListStoryItem> = arrayListOf()
        for (i in 0..100) {
            val story = ListStoryItem(
                i.toString(),
                "url_$i",
                "createdAt_$i",
                "name_$i",
                "desc_$i",
            )
            items.add(story)
        }
        return items
    }
}