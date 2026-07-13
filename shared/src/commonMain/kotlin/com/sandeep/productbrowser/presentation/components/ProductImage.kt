package com.sandeep.productbrowser.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@Composable
fun ProductImage(
    images: List<String>,
    modifier: Modifier = Modifier
) {
    if (images.isEmpty()) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(250.dp)
                .background(
                    MaterialTheme.colorScheme.surfaceVariant,
                    RoundedCornerShape(12.dp)
                )
        )
        return
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val validImages = images.filter { it.isNotBlank() }

        if (validImages.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .background(
                        MaterialTheme.colorScheme.surfaceVariant,
                        RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text("No Images", style = MaterialTheme.typography.labelMedium)
            }
            return
        }

        val pagerState = rememberPagerState(pageCount = { validImages.size })

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .clip(RoundedCornerShape(12.dp))
        ) { page ->
            KamelImage(
                resource = asyncPainterResource(data = validImages[page]),
                contentDescription = "Product image $page",
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentScale = ContentScale.Fit,
                onLoading = {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                },
                onFailure = {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Image failed to load", style = MaterialTheme.typography.labelSmall)
                    }
                }
            )
        }

        if (validImages.size > 1) {
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                repeat(validImages.size) { iteration ->
                    val color = if (pagerState.currentPage == iteration) 
                        MaterialTheme.colorScheme.primary 
                    else 
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                    
                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(color)
                    )
                }
            }
        }
    }
}