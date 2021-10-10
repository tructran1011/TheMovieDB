package com.me.themoviedb.presentation.error

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import com.me.themoviedb.databinding.DialogErrorBinding

class ErrorDialog(
    context: Context,
    imageResId: Int,
    message: String,
) : Dialog(context, android.R.style.Theme_DeviceDefault_NoActionBar_Fullscreen) {

    constructor(context: Context, imageResId: Int, messageResId: Int) :
            this(context, imageResId, context.getString(messageResId))

    private val binding: DialogErrorBinding = DialogErrorBinding.inflate(LayoutInflater.from(context))

    init {
        binding.run {
            setContentView(root)
            ivIcon.setImageResource(imageResId)
            tvMessage.text = message
            btnOk.setOnClickListener { dismiss() }
        }
    }

}