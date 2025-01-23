package com.example.anime_app.core.presentation.detail

import android.net.Uri
import android.util.Log
import android.webkit.WebView
import android.widget.MediaController
import android.widget.VideoView
import androidx.annotation.OptIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ImageNotSupported
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView


@kotlin.OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    id:Int
) {

    val viewModel = hiltViewModel<DetailsViewModel>()

    LaunchedEffect(id) {
        viewModel.getAnimeById(id)
    }
    val state = viewModel.animeIdState.collectAsState().value

    val isLoading = viewModel.isLoading.collectAsState().value

    val imageState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(state.animeId?.images?.jpg?.large_image_url)
            .size(Size.ORIGINAL)
            .build()
    ).state


    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        Scaffold(
            topBar = {
                TopAppBar(
                    colors = topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.inverseSurface,
                    ),
                    title = {
                        Text(
                            textAlign = TextAlign.Center,
                            text = "Details",
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                )
            }
        ) { paddingValues ->
            Column(
                Modifier.fillMaxSize().padding(paddingValues).padding(10.dp)
            ) {

                state.animeId?.trailer?.youtube_id?.let {
                    YoutubePlayer(
                        youtubeVideoId = it,
                        lifecycleOwner = LocalLifecycleOwner.current
                    )
                }

                Row(
                    Modifier.fillMaxWidth()
                        .height(180.dp)
                ) {

                    Box(
                        modifier = Modifier
                            .height(180.dp)
                            .width(150.dp)
                            .padding(start = 5.dp, top =  5.dp, bottom =  5.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(color = Color.White),

                        ) {
                        if( imageState is AsyncImagePainter.State.Error){
                            Icon(
                                modifier = Modifier.fillMaxSize(),
                                imageVector = Icons.Rounded.ImageNotSupported, contentDescription = null
                            )
                        }

                        if(imageState is AsyncImagePainter.State.Loading){
                            CircularProgressIndicator(
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }

                        if(imageState is AsyncImagePainter.State.Success){
                            Image(
                                modifier = Modifier
                                    .fillMaxSize(),
                                contentScale = ContentScale.Crop,
                                painter = imageState.painter , contentDescription = null
                            )
                        }
                    }

                    Column(
                        modifier = Modifier.padding(4.dp)
                    ) {

                        Text(
                            text = state.animeId?.title_english.toString(),
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            maxLines = 2
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Text("Rating:${state.animeId?.score}⭐️",
                            fontWeight = FontWeight.SemiBold,)

                        Text("Episodes:${state.animeId?.episodes}",
                            fontWeight = FontWeight.SemiBold,)

                        Text("Year:${state.animeId?.year}",
                            fontWeight = FontWeight.SemiBold,)

                    }
                }

                Text(
                    text = state.animeId?.synopsis.toString(),
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Justify
                )



            }
        }


    }


}
@Composable
fun YoutubePlayer(
    youtubeVideoId: String,
    lifecycleOwner: LifecycleOwner
) {

    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp)),
        factory = { context ->
            YouTubePlayerView(context = context).apply {
                lifecycleOwner.lifecycle.addObserver(this)

                addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        youTubePlayer.loadVideo(youtubeVideoId, 0f)
                    }
                })
            }
        })

}







