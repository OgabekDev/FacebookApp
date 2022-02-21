package dev.ogabek.kotlin.model

import android.os.Parcel
import android.os.Parcelable
import java.util.ArrayList

class Post() : Parcelable {
    var isNewProfile: Boolean = false
    var photo = 0
    var profile: Int = 0
    var fullName: String = ""
    var photos: List<Photos> = ArrayList<Photos>()

    var isOwnPost: Boolean = false;
    var isLink: Boolean = false
    var postText: String = ""
    var link: String = ""

    constructor(parcel: Parcel) : this() {
        isNewProfile = parcel.readByte() != 0.toByte()
        photo = parcel.readInt()
        profile = parcel.readInt()
        fullName = parcel.readString().toString()
        isOwnPost = parcel.readByte() != 0.toByte()
        isLink = parcel.readByte() != 0.toByte()
        postText = parcel.readString().toString()
        link = parcel.readString().toString()
    }

    constructor(profile: Int, fullName: String, photo: Int, isNewProfile: Boolean) : this() {
        this.photo = photo
        this.profile = profile
        this.fullName = fullName
        this.isNewProfile = isNewProfile
    }

    constructor(isOwnPost: Boolean, profile: Int, fullName: String, postText: String, isLink: Boolean, link: String) : this() {
        this.isOwnPost = isOwnPost;
        this.profile = profile
        this.fullName = fullName
        this.postText = postText
        this.isLink = isLink
        this.link = link
    }

    constructor(profile: Int, fullName: String, photos: List<Photos>) : this() {
        this.photos = photos
        this.profile = profile
        this.fullName = fullName
        isNewProfile = false
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeByte(if (isNewProfile) 1 else 0)
        parcel.writeInt(photo)
        parcel.writeInt(profile)
        parcel.writeString(fullName)
        parcel.writeByte(if (isOwnPost) 1 else 0)
        parcel.writeByte(if (isLink) 1 else 0)
        parcel.writeString(postText)
        parcel.writeString(link)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Post> {
        override fun createFromParcel(parcel: Parcel): Post {
            return Post(parcel)
        }

        override fun newArray(size: Int): Array<Post?> {
            return arrayOfNulls(size)
        }
    }
}