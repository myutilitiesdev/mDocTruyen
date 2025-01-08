package com.foryou.androiddoctruyen.presenter.screens.main.tabs

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.foryou.androiddoctruyen.presenter.components.ErrorLayout
import com.foryou.androiddoctruyen.presenter.screens.main.bottomNavigation.NavigationScreen
import com.foryou.androiddoctruyen.utils.UiState
import com.seiko.imageloader.model.ImageAction
import com.seiko.imageloader.model.ImageRequest
import com.seiko.imageloader.rememberImageAction
import com.seiko.imageloader.rememberImageActionPainter
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HotScreen(navController: NavController) {

    val viewModel = koinViewModel<HotVM>()

    val data by viewModel.storiesList.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val isRefreshing by viewModel.isRefreshing

    val uiState by viewModel.uiState

    // Convert pixels to Dp
    val density = LocalDensity.current.density
    val widthInDp: Dp = (250 / density).dp
    val heightInDp: Dp = (340 / density).dp

    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {

        when (uiState) {
            is UiState.Loading -> {
                CircularProgressIndicator() // Show loading spinner
            }

            is UiState.Success -> {

                // Pass the data and callbacks to the reusable LazyColumnWithRefreshAndLoadMore composable
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
//                    verticalArrangement = Arrangement.spacedBy(8.dp),
//                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(data) { item ->
                        Box(
                            modifier = Modifier.fillMaxWidth().clickable {
                                navController.navigate(NavigationScreen.StoryDetail.route.plus("/${item.id}"))
                            },
                            contentAlignment = Alignment.CenterStart // Centers content vertically and horizontally
                        ) {
                            val action: ImageAction by rememberImageAction(ImageRequest(item.image[0].url))
                            val painter: Painter = rememberImageActionPainter(action)

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Image(
                                    painter = painter,
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.background(
                                        MaterialTheme.colorScheme.surfaceColorAtElevation(16.dp)
                                    ).size(widthInDp, heightInDp)
                                )

                                Column(
                                    modifier = Modifier.fillMaxWidth()
                                        .padding(start = 5.dp, end = 5.dp)
                                ) {
                                    Text(
                                        modifier = Modifier.fillMaxWidth(),
                                        text = item.name,
                                        color = Color.Black,
                                        fontWeight = FontWeight.Bold,
                                        style = MaterialTheme.typography.bodyMedium
                                    )

                                    Text(
                                        color = Color(red = 97, green = 97, blue = 97),
                                        text = item.description,
                                        maxLines = 3,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }

                            }
                        }
                    }
                }
            }

            is UiState.Error -> {
                // Show error
                ErrorLayout(onReloadClicked = {
                    viewModel.getHotStories()
                })
            }
        }
    }
}