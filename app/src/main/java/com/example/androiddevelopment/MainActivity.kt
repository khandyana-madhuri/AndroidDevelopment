package com.example.androiddevelopment

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.androiddevelopment.data.remote.dto.ArticleDto
import com.example.androiddevelopment.ui.viewmodel.NewsUiState
import com.example.androiddevelopment.ui.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            NewsNavGraph()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen(
    uiState: NewsUiState,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    onArticleClick: (ArticleDto) -> Unit
) {

    val state = rememberPullToRefreshState()

    PullToRefreshBox(
        state = state,
        isRefreshing = isRefreshing,
        onRefresh = onRefresh
    ) {

        when (uiState) {

            is NewsUiState.Loading -> {
                LoadingScreen()
            }

            is NewsUiState.Success -> {
                NewsListScreen(
                    articles = uiState.articles,
                    onArticleClick = onArticleClick
                )
            }

            is NewsUiState.Error -> {
                Toast.makeText(LocalContext.current, uiState.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}


@Composable
fun LoadingScreen() {
    Box(
      modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun NewsListScreen(articles: List<ArticleDto>, onArticleClick: (ArticleDto) -> Unit) {

    LazyColumn(
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(articles) { article ->
            NewsCard(article, onArticleClick)
        }
    }
}

@Composable
fun NewsDetailsScreen(article: ArticleDto) {
    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp)
    ) {
        Text(text = article.title ?: "No Title", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = article.content ?: "No Content", style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun NewsNavGraph() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "news_list"
    ) {
        composable("news_list") {

            val viewModel: NewsViewModel = hiltViewModel()

            val uiState by viewModel.uiState.collectAsState()
            val isRefreshing by viewModel.isRefreshing.collectAsState()

            NewsScreen(
                uiState = uiState,
                isRefreshing = isRefreshing,
                onRefresh = {
                    viewModel.fetchNews(isPullRefresh = true)
                },
                onArticleClick = { article ->
                    navController.currentBackStackEntry
                        ?.savedStateHandle
                        ?.set("article", article)

                    navController.navigate("news_detail")
                }
            )
        }


        composable("news_detail") {
            val article = navController.previousBackStackEntry?.savedStateHandle?.get<ArticleDto>("article")
            article?.let {
                NewsDetailsScreen(it)
            }
        }
    }
}

@Composable
fun NewsCard(article: ArticleDto, onClick: (ArticleDto) -> Unit) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onClick(article) },
        elevation = CardDefaults.cardElevation(6.dp)
    ) {

        Column {

            AsyncImage(
                model = article.urlToImage,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = article.title ?: "",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = 12.dp)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = article.description ?: "",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
            )
        }
    }
}



