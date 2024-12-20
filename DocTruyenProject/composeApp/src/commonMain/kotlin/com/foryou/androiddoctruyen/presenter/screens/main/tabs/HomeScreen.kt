package com.foryou.androiddoctruyen.presenter.screens.main.tabs

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.foryou.androiddoctruyen.datasource.remote.model.CategoryModel
import com.foryou.androiddoctruyen.datasource.remote.model.HomeModel
import com.foryou.androiddoctruyen.datasource.remote.model.StoryModel
import com.foryou.androiddoctruyen.presenter.screens.main.bottomNavigation.NavigationScreen
import com.foryou.androiddoctruyen.utils.UiState
import com.seiko.imageloader.model.ImageAction
import com.seiko.imageloader.model.ImageRequest
import com.seiko.imageloader.rememberImageAction
import com.seiko.imageloader.rememberImageActionPainter
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(navController: NavController) {
    val homeVM = koinViewModel<HomeVM>()
    val uiState by homeVM.uiState

    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        when (uiState) {
            is UiState.Loading -> {
                Text(text = uiState.toString())
            }

            is UiState.Success -> {
                // Convert pixels to Dp
                val density = LocalDensity.current.density
                val widthInDp: Dp = (250 / density).dp
                val heightInDp: Dp = (340 / density).dp

                LazyColumn(
                    modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(
                        vertical = 5.dp, horizontal = 5.dp
                    )
                ) {
                    item {
                        StoriesShowCaseList(
                            navController,
                            stories = (uiState as UiState.Success<HomeModel>).data.categoryList[0].storyList
                                ?: emptyList(),
                            widthInDp,
                            heightInDp
                        )
                    }

                    items((uiState as UiState.Success<HomeModel>).data.categoryList) { category ->
                        CategorySection(
                            navController, category = category, widthInDp, heightInDp
                        )
                    }
                }
            }

            is UiState.Error -> {
                Text(text = (uiState as UiState.Error).message)
            }
        }
    }
}

@Composable
fun StoriesShowCaseList(
    navController: NavController, stories: List<StoryModel>, widthInDp: Dp, heightInDp: Dp
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(stories) { story ->
            StoryItem(navController, story = story, widthInDp, heightInDp)
        }
    }
}

@Composable
fun StoryItem(navController: NavController, story: StoryModel, widthInDp: Dp, heightInDp: Dp) {
    val action: ImageAction by rememberImageAction(ImageRequest(story.image[0].url))
    val painter: Painter = rememberImageActionPainter(action)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.height(220.dp).width(300.dp)
    ) {
        Card(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier.fillMaxSize().clickable {
                    navController.navigate(NavigationScreen.StoryDetail.route.plus("/${story.id}"))
                }, contentAlignment = Alignment.BottomStart // Align content at the bottom start
            ) {
                Image(
                    painter = painter,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.background(
                        MaterialTheme.colorScheme.surfaceColorAtElevation(16.dp)
                    ).fillMaxWidth().height(420.dp)
                )

                Text(
                    text = "  " + story.name,
                    fontSize = 12.sp,
                    maxLines = 1,
                    color = Color.White,
                    minLines = 1,
                    modifier = Modifier.fillMaxWidth().background(Color.Gray)
                )
            }
        }
    }
}


@Composable
fun CategorySection(
    navController: NavController, category: CategoryModel, widthInDp: Dp, heightInDp: Dp
) {
    Column {
        Text(
            text = category.name,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 5.dp, top = 5.dp)
        )

        category.storyList?.let {
            // Horizontal story list for each category
            LazyRow {
                items(it) { story ->
                    StoryItemCard(navController, story = story, widthInDp, heightInDp)
                }
            }
        }
    }
}

@Composable
fun StoryItemCard(navController: NavController, story: StoryModel, widthInDp: Dp, heightInDp: Dp) {
    val action: ImageAction by rememberImageAction(ImageRequest(story.image[0].url))
    val painter: Painter = rememberImageActionPainter(action)
    Box(modifier = Modifier.fillMaxSize().clickable {
        navController.navigate(NavigationScreen.StoryDetail.route.plus("/${story.id}"))
    }) {
        Column(
            modifier = Modifier.width(200.dp)
        ) {
            Image(
                painter = painter,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
                modifier = Modifier.background(
                    MaterialTheme.colorScheme.surfaceColorAtElevation(16.dp)
                ).size(190.dp, 126.dp).padding(1.dp)
            )

            Text(
                modifier = Modifier.padding(top = 5.dp, bottom = 5.dp),
                text = story.name,
                color = Color.Black,
                fontSize = 18.sp,
                minLines = 1,
                maxLines = 1,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = story.name,
                minLines = 2,
                maxLines = 2,
                color = Color(red = 97, green = 97, blue = 97),
                fontSize = 14.sp
            )
        }
    }
}