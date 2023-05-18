package com.example.storyapp.ui.detailstory

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.storyapp.R
import com.example.storyapp.databinding.ActivityDetailStoryBinding
import com.example.storyapp.logic.repository.retrofit.responses.ListStoryItem
import java.util.*

class DetailStoryActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailStoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
    }

    private fun setupView(){
        val story = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(story, ListStoryItem::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(story)
        }

        getDetaiStory(story)
    }
    private fun getDetaiStory(data: ListStoryItem?) {
        Glide.with(this)
            .load(data?.photoUrl)
            .into(binding.ivStory)
        with(binding){
            tvName.text = data?.name
            val date = data?.createdAt.toString().split("-")
            val formattedDate = "${date[0]}-${date[1]}-${date[2].take(2)}"
            tvDate.text = resources.getString(R.string.uploadedAt, formattedDate)
            tvCaption.text = resources.getString(R.string.desc, data?.description)
        }
    }

    companion object{
        const val story = "story"
    }
}