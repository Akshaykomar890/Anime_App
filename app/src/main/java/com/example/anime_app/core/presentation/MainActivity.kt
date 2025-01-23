package com.example.anime_app.core.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.anime_app.core.Destination.Details
import com.example.anime_app.core.Destination.Home
import com.example.anime_app.core.presentation.detail.DetailScreen
import com.example.anime_app.core.presentation.home.HomeScreen
import com.example.compose.Anime_AppTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Anime_AppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SystemBarColor(color = MaterialTheme.colorScheme.inverseSurface)


                    Text("",Modifier.padding(innerPadding))

                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = Home ){
                        composable<Home> {
                            HomeScreen(innerPadding,navController)
                        }
                        composable<Details> {
                            val route = it.toRoute<Details>()
                            Log.d("123","${route.id}")
                            DetailScreen(route.id)
                        }
                    }

                }
            }
        }
    }
    @Composable
    fun SystemBarColor(color: Color) {
        val ui = rememberSystemUiController()
        LaunchedEffect(key1 = color) {
            ui.setSystemBarsColor(color)
        }
    }
}


