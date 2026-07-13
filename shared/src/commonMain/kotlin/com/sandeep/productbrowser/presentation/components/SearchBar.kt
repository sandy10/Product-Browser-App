package com.sandeep.productbrowser.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*

import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SearchBar(

    value: String,
    onValueChange: (String) -> Unit

) {

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),

        value = value,
        onValueChange = onValueChange,
        label = {
            Text("Search Products")
        }
    )
}