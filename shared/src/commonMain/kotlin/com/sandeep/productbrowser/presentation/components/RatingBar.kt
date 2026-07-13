package com.sandeep.productbrowser.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

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
            text = String.format("%.1f", rating),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}