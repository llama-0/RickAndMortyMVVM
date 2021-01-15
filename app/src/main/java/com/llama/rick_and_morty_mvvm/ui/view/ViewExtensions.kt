package com.llama.rick_and_morty_mvvm.ui.view

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat

fun ImageView.setDrawable(@DrawableRes id: Int) {
    setImageDrawable(
        ContextCompat.getDrawable(
            context,
            id
        )
    )
}