package dev.ogabek.kotlin.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.URLUtil
import android.widget.*
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import com.bumptech.glide.Glide
import dev.ogabek.kotlin.R
import dev.ogabek.kotlin.model.Post
import io.github.ponnamkarthik.richlinkpreview.MetaData
import io.github.ponnamkarthik.richlinkpreview.ResponseListener
import io.github.ponnamkarthik.richlinkpreview.RichPreview

class CreatePostActivity: AppCompatActivity() {

    var isLink: Boolean = false
    var link: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post)

        initViews()

    }

    private fun initViews() {
        val close: ImageView = findViewById(R.id.iv_close)
        val post: Button = findViewById(R.id.btn_post)
        val link_preview: LinearLayout = findViewById(R.id.link_preview)
        val post_text: EditText = findViewById(R.id.et_text)
        val close_url: ImageView = findViewById(R.id.iv_close_url)
        val link_image: ImageView = findViewById(R.id.iv_link_photo)
        val link_title: TextView = findViewById(R.id.tv_link_title)
        val link_description: TextView = findViewById(R.id.tv_link_description)

        close_url.setOnClickListener {
            link_preview.visibility = View.GONE
            post_text.setText("")
        }

        post.setOnClickListener {
            val post = Post(true, R.drawable.ogabekdev, getString(R.string.ogabek_matyakubov), post_text.text.toString(), isLink, link)
            intent.putExtra("post", post)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

        close.setOnClickListener {
            finish()
        }

        var data: MetaData?

        val richPreview = RichPreview(object : ResponseListener {
            override fun onData(metaData: MetaData) {
                data = metaData
                link_preview.visibility = View.VISIBLE
                if (data!!.imageurl == null) {
                    link_image.visibility = View.GONE
                } else {
                    Glide.with(this@CreatePostActivity).load(data!!.imageurl).into(link_image)
                }
                link_title.text = data!!.title
                link_description.text = data!!.description

            }

            override fun onError(e: Exception) {
            }
        })

        post_text.doAfterTextChanged {
            val text = post_text.text.toString()

            if (text.isNotEmpty()) {
                post.setBackgroundResource(R.drawable.create_post_btn)
                post.setTextColor(Color.WHITE)
                post.isEnabled = true
            } else {
                post.setBackgroundResource(R.drawable.create_post_btn_enabled)
                post.setTextColor(Color.parseColor("#90FFFFFF"))
                post.isEnabled = false
            }

            val texts = text.split(" ")

            for (i in texts) {
                if (URLUtil.isValidUrl(i)) {
                    richPreview.getPreview(i)
                    isLink = true
                    link = i
                    link_preview.setOnClickListener {
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(i)))
                    }
                } else {
                    link_preview.visibility = View.GONE
                    continue
                }
            }
        }

    }

}