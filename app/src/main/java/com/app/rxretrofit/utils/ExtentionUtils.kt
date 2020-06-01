package com.app.rxretrofit.utils

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadImg(imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        Glide.with(this).load(imageUrl).into(this)

    }
}