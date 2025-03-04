package com.jackanota.blocdenotas

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Note(
    val title: String,
    val text: String
) : Parcelable
