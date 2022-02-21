package dev.ogabek.kotlin.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.ogabek.kotlin.R
import dev.ogabek.kotlin.adapter.FeedAdapter
import dev.ogabek.kotlin.model.Feed
import dev.ogabek.kotlin.model.Photos
import dev.ogabek.kotlin.model.Post
import dev.ogabek.kotlin.model.Story

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }

    val adapter = FeedAdapter(this, allFeeds())

    private fun initViews() {
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        recyclerView.adapter = adapter
    }

    private fun allFeeds(): ArrayList<Feed> {
        val stories: ArrayList<Story> = ArrayList()
        stories.add(Story())
        stories.add(Story(R.drawable.aziz, "Azizbek", R.drawable.aziz))
        stories.add(Story(R.drawable.bogibek, "Bogibek Matyaqubov", R.drawable.bogibek))
        stories.add(Story(R.drawable.elmurod, "Elmurod Nazirov", R.drawable.elmurod))
        stories.add(Story(R.drawable.jabbor, "Jabbor", R.drawable.jabbor))
        stories.add(Story(R.drawable.jonibek, "Jonibek Xolmonov", R.drawable.jonibek))
        stories.add(Story(R.drawable.ogabekdev, "Ogabek Matyakubov", R.drawable.ogabekdev))
        stories.add(Story(R.drawable.shakhriyor, "Shakhriyor", R.drawable.shakhriyor))
        stories.add(Story(R.drawable.yahyo, "Yahyo Mahmudiy", R.drawable.yahyo))
        val feeds: ArrayList<Feed> = ArrayList<Feed>()

        //Head
        feeds.add(Feed())

        //Story
        feeds.add(Feed(stories))

        //PhotoList
        val photos: MutableList<Photos> = ArrayList()
        photos.add(Photos(R.drawable.post_4))
        photos.add(Photos(R.drawable.post_2))
        photos.add(Photos(R.drawable.post_3))
        photos.add(Photos(R.drawable.post_4))
        photos.add(Photos(R.drawable.post_2))
        photos.add(Photos(R.drawable.post_3))
        photos.add(Photos(R.drawable.post_3))
        photos.add(Photos(R.drawable.post_3))

        //Post
        feeds.add(Feed(Post(R.drawable.aziz, "Azizbek", R.drawable.post_1, false)))
        feeds.add(Feed(Post(R.drawable.bogibek, "Bogibek Matyaqubov", R.drawable.post_2, false)))
        feeds.add(Feed(Post(R.drawable.elmurod, "Elmurod Nazirov", R.drawable.post_3, false)))
        feeds.add(Feed(Post(R.drawable.jabbor, "Jabbor", photos)))
        feeds.add(Feed(Post(R.drawable.jabbor, "Jabbor", R.drawable.jabbor, true)))
        feeds.add(Feed(Post(R.drawable.jonibek, "Jonibek Xolmonov", R.drawable.post_1, false)))
        feeds.add(Feed(Post(R.drawable.ogabekdev, "Ogabek Matyakubov", R.drawable.ogabekdev, true)))
        feeds.add(Feed(Post(R.drawable.shakhriyor, "Shakhriyor", R.drawable.post_3, false)))
        feeds.add(Feed(Post(R.drawable.yahyo, "Yahyo Mahmudiy", R.drawable.post_4, false)))
        feeds.add(Feed(Post(R.drawable.aziz, "Azizbek", R.drawable.aziz, true)))
        feeds.add(Feed(Post(R.drawable.bogibek, "Bogibek Matyaqubov", R.drawable.post_2, false)))
        feeds.add(Feed(Post(R.drawable.elmurod, "Elmurod Nazirov", R.drawable.elmurod, true)))
        feeds.add(Feed(Post(R.drawable.jonibek, "Jonibek Xolmonov", R.drawable.post_1, false)))
        feeds.add(Feed(Post(R.drawable.ogabekdev, "Ogabek Matyakubov", R.drawable.post_2, false)))
        feeds.add(Feed(Post(R.drawable.shakhriyor, "Shakhriyor", R.drawable.post_3, false)))
        feeds.add(Feed(Post(R.drawable.yahyo, "Yahyo Mahmudiy", R.drawable.yahyo, true)))
        feeds.add(Feed(Post(R.drawable.aziz, "Azizbek", R.drawable.post_1, false)))
        feeds.add(Feed(Post(R.drawable.bogibek, "Bogibek Matyaqubov", R.drawable.post_2, false)))
        feeds.add(Feed(Post(R.drawable.elmurod, "Elmurod Nazirov", R.drawable.elmurod, true)))
        feeds.add(Feed(Post(R.drawable.jabbor, "Jabbor", R.drawable.post_4, false)))
        feeds.add(Feed(Post(R.drawable.jonibek, "Jonibek Xolmonov", R.drawable.post_1, false)))
        feeds.add(Feed(Post(R.drawable.ogabekdev, "Ogabek Matyakubov", R.drawable.post_2, false)))
        feeds.add(Feed(Post(R.drawable.shakhriyor, "Shakhriyor", R.drawable.shakhriyor, true)))
        feeds.add(Feed(Post(R.drawable.yahyo, "Yahyo Mahmudiy", R.drawable.post_4, false)))
        return feeds
    }

    fun callCreatePostActivity() {
        val intent = Intent(this, CreatePostActivity::class.java)
        resultActivity.launch(intent)
    }

    private var resultActivity = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()){ result ->
        if (result.resultCode == Activity.RESULT_OK){
            val data:Intent? = result.data
            val result = data!!.getParcelableExtra<Post>("post")!!
            adapter.addPost(result)
        }
    }

}