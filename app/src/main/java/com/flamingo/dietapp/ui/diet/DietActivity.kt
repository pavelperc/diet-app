package com.flamingo.dietapp.ui.diet

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.flamingo.dietapp.R
import com.flamingo.dietapp.domain.Diet
import kotlinx.android.synthetic.main.activity_diet.*

class DietActivity : AppCompatActivity() {

    private var isFollowing = false
    set(value) {
        field = value
        adapter.withCheckBoxes = value
        if (value) {
            btnFollow.text = "Unfollow"
        } else {
            btnFollow.text = "Follow"
        }
    }

    val diet by lazy { intent.getSerializableExtra("diet") as Diet }
    val adapter by lazy { DietDayListAdapter(this, diet, isFollowing) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diet)

        btnBack.setOnClickListener { onBackPressed() }
        tvDescription.text = diet.description
        tvName.text = diet.name
        Glide.with(this)
            .load(diet.previewImage)
            .into(ivPreview)

        rvDays.adapter = adapter
        rvDays.layoutManager = LinearLayoutManager(this)

        btnFollow.setOnClickListener {
            isFollowing = !isFollowing
        }
    }
}