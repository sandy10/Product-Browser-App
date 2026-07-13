package com.sandeep.productbrowser.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import kotlin.math.roundToInt

@Composable
fun RatingBar(
    rating: Double
) {

    Row {

        Text(
            text = "⭐",
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = ((rating * 10.0).roundToInt() / 10.0).toString(),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}