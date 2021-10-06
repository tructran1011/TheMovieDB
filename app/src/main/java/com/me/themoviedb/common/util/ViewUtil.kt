package com.me.themoviedb.common.util

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.me.themoviedb.R

fun RecyclerView.addSimpleDivider() {
    val itemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        .apply {
            ContextCompat.getDrawable(context, R.drawable.item_divider)?.let {
                setDrawable(it)
            }
        }
    addItemDecoration(itemDecoration)
}