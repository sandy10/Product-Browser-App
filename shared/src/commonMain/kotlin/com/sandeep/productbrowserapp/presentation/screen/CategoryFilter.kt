package com.sandeep.productbrowserapp.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
    fun CategoryFilter(
        categories: List<String>,
        selectedCategory: String?,
        onCategorySelected: (String) -> Unit
    ) {

        LazyRow(
            modifier = Modifier.padding(8.dp)
        ) {

            items(categories) { category ->

                val isSelected =
                    if (category == "All") selectedCategory == null
                    else category == selectedCategory
                Card(
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .clickable {
                            onCategorySelected(category)
                        },
                    backgroundColor =
                        if (isSelected) Color.Blue else Color.LightGray,
                    elevation = 4.dp
                ) {
                    Text(
                        text = category,
                        color = if (isSelected) Color.White else Color.Black,
                        modifier = Modifier.padding(10.dp)
                    )
                }
            }
        }
    }