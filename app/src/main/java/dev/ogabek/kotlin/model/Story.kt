package dev.ogabek.kotlin.model

class Story {
    val isCreateStory: Boolean
    var photo = 0
    var profile = 0
    var fullName: String? = null

    constructor() {
        isCreateStory = true
    }

    constructor(profile: Int, fullName: String, photo: Int) {
        this.photo = photo
        this.profile = profile
        this.fullName = fullName
        isCreateStory = false
    }
}