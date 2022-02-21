package dev.ogabek.kotlin.model

class Feed {
    var isHeader = false
    var post: Post? = null
    var stories = ArrayList<Story>()

    constructor() {
        isHeader = true
    }

    constructor(post: Post) {
        this.post = post
        isHeader = false
    }

    constructor(stories: ArrayList<Story>) {
        this.stories = stories
        isHeader = false
    }
}