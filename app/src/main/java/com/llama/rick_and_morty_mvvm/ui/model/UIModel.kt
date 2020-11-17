package com.llama.rick_and_morty_mvvm.ui.model

import android.widget.Button
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class UIModel(
        val progressBar: ProgressBar,
        val snackbar: Snackbar,
        val buttonRetry: Button,
        val recyclerView: RecyclerView
)