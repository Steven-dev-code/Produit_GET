package com.example.cours.screens

import androidx.annotation.DrawableRes
import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.HorizontalUncontainedCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cours.R
import com.example.cours.R.drawable.logo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        visible = true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Image(
                    painter = painterResource(id = logo),
                    contentDescription = "GET Logo",
                    modifier = Modifier.size(140.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Produits disponibles :",
                            fontWeight = FontWeight.Bold
                        )

                            data class CarouselItem(
                                val id: Int,
                                @DrawableRes val imageResId: Int,
                                val contentDescription: String
                            )

                            val carouselItems = remember {
                                listOf(
                                    CarouselItem(0, R.drawable.t_shirt, "t_shirt"),
                                    CarouselItem(1, R.drawable.sweat, "sweat"),
                                    CarouselItem(2, R.drawable.casquette, "casquette"),
                                )
                            }

                            HorizontalUncontainedCarousel(
                                state = rememberCarouselState { carouselItems.count() },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                                    .padding(top = 16.dp, bottom = 16.dp),
                                itemWidth = 186.dp,
                                itemSpacing = 8.dp,
                                contentPadding = PaddingValues(horizontal = 16.dp)
                            ) { i ->
                                val item = carouselItems[i]
                                Image(
                                    modifier = Modifier
                                        .height(205.dp)
                                        .maskClip(MaterialTheme.shapes.extraLarge),
                                    painter = painterResource(id = item.imageResId),
                                    contentDescription = item.contentDescription,
                                    contentScale = ContentScale.Crop
                                )
                            }
                    }
                }
            }
        }
    }


} 