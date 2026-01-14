package com.example.yourday.presentation.home

import android.content.Intent
import com.example.yourday.model.FavQuote
import com.example.yourday.model.Quote


fun ShareQuote(context: android.content.Context, quote: Quote) {
    val sendIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, "“${quote.text}” — ${quote.author}")
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, "Share Quote via")
    context.startActivity(shareIntent)

}

fun ShareQuote(context: android.content.Context, favQuote: FavQuote) {
    val sendIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, "“${favQuote.text}” — ${favQuote.author}")
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, "Share Quote via")
    context.startActivity(shareIntent)

}
