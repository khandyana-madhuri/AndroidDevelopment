package com.example.androiddevelopment

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
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
            val vm: NewsViewModel = hiltViewModel()
            val uiState by vm.uiState.collectAsState()
            NewsScreen(uiState)
        }
    }
}

@Composable
fun NewsScreen(uiState: NewsUiState) {
    when(uiState) {
        is NewsUiState.Loading -> {
            LoadingScreen()
        }

        is NewsUiState.Success -> {
            NewsListScreen(uiState.articles)
        }

        is NewsUiState.Error -> {
            Toast.makeText(LocalContext.current, uiState.message, Toast.LENGTH_SHORT).show()
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
fun NewsListScreen(articles: List<ArticleDto>) {
    LazyColumn {
        items(articles) { article ->
            Column(modifier= Modifier.padding(16.dp)) {
                Text(
                    text = article.title ?: "No Title",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = article.description ?: "No Description",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

