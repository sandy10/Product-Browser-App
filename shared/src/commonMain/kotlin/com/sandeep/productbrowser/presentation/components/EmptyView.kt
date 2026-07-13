package com.sandeep.productbrowser.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.sandeep.productbrowser.core.common.Constants

@Composable
fun EmptyView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Text(
            text = Constants.Strings.NO_PRODUCTS_FOUND,
            style = MaterialTheme.typography.titleMedium
        )
    }
}