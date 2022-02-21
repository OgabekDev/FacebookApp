package dev.ogabek.kotlin.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import dev.ogabek.kotlin.R
import dev.ogabek.kotlin.activity.MainActivity
import dev.ogabek.kotlin.model.Feed
import dev.ogabek.kotlin.model.Photos
import dev.ogabek.kotlin.model.Post
import io.github.ponnamkarthik.richlinkpreview.MetaData
import io.github.ponnamkarthik.richlinkpreview.ResponseListener
import io.github.ponnamkarthik.richlinkpreview.RichPreview

class FeedAdapter(var context: MainActivity, var feeds: ArrayList<Feed>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        val feed: Feed = feeds[position]
        return when {
            feed.isHeader -> {
                TYPE_HEADER_VIEW
            }
            feed.stories.size > 0 -> {
                TYPE_STORY_VIEW
            }
            feed.post!!.isNewProfile -> {
                TYPE_NEW_PROFILE_POST_VIEW
            }
            feed.post!!.photos.isNotEmpty() -> {
                TYPE_PHOTOS_VIEW
            }
            feed.post!!.isOwnPost -> {
                TYPE_OWN_POST_VIEW
            }
            else -> {
                TYPE_POST_VIEW
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_HEADER_VIEW -> {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_feed_header, parent, false)
                FeedHeaderViewHolder(view)
            }
            TYPE_STORY_VIEW -> {
                val view: View =
                    LayoutInflater.from(parent.context).inflate(R.layout.item_feed_story, parent, false)
                FeedStoryViewHolder(context, view)
            }
            TYPE_NEW_PROFILE_POST_VIEW -> {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_feed_post_change_profile, parent, false)
                FeedChangeProfilePostViewHolder(view)
            }
            TYPE_PHOTOS_VIEW -> {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_feed_post_all, parent, false)
                FeedAllPhotosPostViewHolder(view)
            }
            TYPE_OWN_POST_VIEW -> {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.feed_own_post, parent, false)
                OwnPostViewHolder(view)
            }
            else -> {
                val view: View =
                    LayoutInflater.from(parent.context).inflate(R.layout.item_feed_post, parent, false)
                FeedPostViewHolder(view)
            }
        }
    }

    fun addPost(post: Post) {

        feeds.add(2, Feed(post))
        notifyItemChanged(2)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val feed: Feed = feeds[position]
        if (holder is FeedHeaderViewHolder) {
            holder.apply {
                tv_post_a_post.setOnClickListener {
                    context.callCreatePostActivity()
                }
            }
        }
        if (holder is FeedStoryViewHolder) {
            val recyclerView = holder.recyclerView
            recyclerView.adapter = StoryAdapter(feed.stories)
        }
        if (holder is FeedPostViewHolder) {
            holder.iv_post_photo.setImageResource(feed.post!!.photo)
            holder.iv_post_profile.setImageResource(feed.post!!.profile)
            holder.tv_post_full_name.text = feed.post!!.fullName
        }
        if (holder is FeedChangeProfilePostViewHolder) {
            val newString = Html.fromHtml(
                "<b>" + feed.post!!.fullName
                    .toString() + "</b> Uploaded his profile picture"
            )
            holder.fullName.text = newString
            holder.profile.setImageResource(feed.post!!.profile)
            holder.newPhoto.setImageResource(feed.post!!.photo)
        }
        if (holder is FeedAllPhotosPostViewHolder) {
            holder.iv_post_profile.setImageResource(feed.post!!.profile)
            holder.tv_post_full_name.text = feed.post!!.fullName
            val photo: List<Photos> = feed.post!!.photos
            when (photo.size) {
                1 -> {
                    holder.iv_1.setImageResource(photo[0].photo)
                    holder.ll_2.visibility = View.GONE
                    holder.lll_1.visibility = View.GONE
                }
                2 -> {
                    holder.iv_1.setImageResource(photo[0].photo)
                    holder.iv_2.setImageResource(photo[1].photo)
                    holder.lll_1.visibility = View.GONE
                }
                3 -> {
                    holder.iv_1.setImageResource(photo[0].photo)
                    holder.iv_2.setImageResource(photo[1].photo)
                    holder.iv_3.setImageResource(photo[2].photo)
                    holder.ll_4.visibility = View.GONE
                    holder.ll_5.visibility = View.GONE
                    holder.lll_1.visibility = View.VISIBLE
                    holder.ll_text.visibility = View.GONE
                }
                4 -> {
                    holder.iv_1.setImageResource(photo[0].photo)
                    holder.iv_2.setImageResource(photo[1].photo)
                    holder.iv_3.setImageResource(photo[2].photo)
                    holder.iv_4.setImageResource(photo[3].photo)
                    holder.ll_5.visibility = View.GONE
                    holder.lll_1.visibility = View.VISIBLE
                    holder.ll_text.visibility = View.GONE
                }
                5 -> {
                    holder.iv_1.setImageResource(photo[0].photo)
                    holder.iv_2.setImageResource(photo[1].photo)
                    holder.iv_3.setImageResource(photo[2].photo)
                    holder.iv_4.setImageResource(photo[3].photo)
                    holder.iv_5.setImageResource(photo[4].photo)
                    holder.lll_1.visibility = View.VISIBLE
                    holder.ll_text.visibility = View.GONE
                }
                else -> {
                    holder.iv_1.setImageResource(photo[0].photo)
                    holder.iv_2.setImageResource(photo[1].photo)
                    holder.iv_3.setImageResource(photo[2].photo)
                    holder.iv_4.setImageResource(photo[3].photo)
                    holder.iv_5.setImageResource(photo[4].photo)
                    holder.lll_1.visibility = View.VISIBLE
                    holder.ll_text.visibility = View.VISIBLE
                    holder.tv_how_much.text = "+ " + (photo.size - 5)
                }
            }
        }
        if (holder is OwnPostViewHolder) {

            val post = feed.post!!


            holder.apply {

                var data: MetaData?

                val richPreview = RichPreview(object : ResponseListener {
                    override fun onData(metaData: MetaData) {
                        iv_link_photo.visibility = View.VISIBLE
                        data = metaData
                        link_preview.visibility = View.VISIBLE
                        if (data!!.imageurl == null) {
                            iv_link_photo.visibility = View.GONE
                        } else {
                            Glide.with(context).load(data!!.imageurl).into(iv_link_photo)
                        }
                        tv_link_title.text = data!!.title
                        tv_link_description.text = data!!.description

                    }

                    @SuppressLint("SetTextI18n")
                    override fun onError(e: Exception) {
                        iv_link_photo.visibility = View.GONE
                        tv_link_title.text = "Something went wrong"
                        tv_link_description.text = "Check Your Internet Please"
                    }
                })


                iv_post_profile.setImageResource(post.profile);
                tv_post_full_name.text = post.fullName
                tv_post_text.text = post.postText
                if (post.isLink) {
                    richPreview.getPreview(post.link)
                } else {
                    link_preview.visibility = View.GONE
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return feeds.size
    }

    class OwnPostViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val iv_post_profile: ShapeableImageView = view.findViewById(R.id.iv_post_profile)
        val tv_post_full_name: TextView = view.findViewById(R.id.tv_post_full_name)
        val tv_post_text: TextView = view.findViewById(R.id.post_text)
        val iv_link_photo: ImageView = view.findViewById(R.id.iv_link_photo)
        val tv_link_title: TextView = view.findViewById(R.id.tv_link_title)
        val tv_link_description: TextView = view.findViewById(R.id.tv_link_description)
        val link_preview: LinearLayout = view.findViewById(R.id.link_preview)
    }

    private class FeedHeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tv_post_a_post: TextView = view.findViewById(R.id.post_a_post)
    }

    private class FeedPostViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var iv_post_profile: ShapeableImageView = view.findViewById(R.id.iv_post_profile)
        var tv_post_full_name: TextView = view.findViewById(R.id.tv_post_full_name)
        var iv_post_photo: ImageView = view.findViewById(R.id.iv_post_photo)
    }

    private class FeedStoryViewHolder(context: Context?, view: View) :
        RecyclerView.ViewHolder(view) {
        var recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewStory)

        init {
            recyclerView.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private class FeedChangeProfilePostViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var fullName: TextView = view.findViewById(R.id.tv_post_new_full_name)
        var profile: ShapeableImageView = view.findViewById(R.id.iv_post_new_profile_profile)
        var newPhoto: ShapeableImageView = view.findViewById(R.id.iv_new_photo)
    }

    private class FeedAllPhotosPostViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var iv_post_profile: ShapeableImageView = view.findViewById(R.id.iv_post_profile)
        var tv_post_full_name: TextView = view.findViewById(R.id.tv_post_full_name)
        var iv_1: ImageView = view.findViewById(R.id.iv_1)
        var iv_2: ImageView = view.findViewById(R.id.iv_2)
        var iv_3: ImageView = view.findViewById(R.id.iv_3)
        var iv_4: ImageView = view.findViewById(R.id.iv_4)
        var iv_5: ImageView = view.findViewById(R.id.iv_5)
        var ll_2: LinearLayout = view.findViewById(R.id.ll_2)
        var ll_4: LinearLayout = view.findViewById(R.id.ll_4)
        var ll_5: LinearLayout = view.findViewById(R.id.ll_5)
        var ll_text: LinearLayout = view.findViewById(R.id.ll_text)
        var lll_1: LinearLayout = view.findViewById(R.id.lll_1)
        var tv_how_much: TextView = view.findViewById(R.id.tv_how_much)
    }

    companion object {
        private const val TYPE_HEADER_VIEW = 0
        private const val TYPE_STORY_VIEW = 1
        private const val TYPE_POST_VIEW = 2
        private const val TYPE_NEW_PROFILE_POST_VIEW = 3
        private const val TYPE_PHOTOS_VIEW = 4
        private const val TYPE_OWN_POST_VIEW = 5;
    }

}