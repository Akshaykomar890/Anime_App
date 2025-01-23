package com.example.anime_app.core.presentation.component

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ImageNotSupported
import androidx.compose.material.icons.rounded.StarRate
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.anime_app.core.Destination.Details
import com.example.anime_app.core.domain.model.Data

@Composable
fun AnimeItems(
    animeList:Data,
    navController: NavHostController
) {


    val imageState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(animeList.images.jpg.large_image_url)
            .size(Size.ORIGINAL)
            .build()
    ).state


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(5.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(color = MaterialTheme.colorScheme.surfaceDim)
            .clickable {
                Log.d("akshay","${animeList.mal_id}")
                    navController.navigate(Details(id = animeList.mal_id))
            }

    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(120.dp)
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
                text = animeList.title_english,
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = animeList.synopsis,
                fontWeight = FontWeight.Normal,
                overflow = TextOverflow.Ellipsis,
                maxLines = 3
            )

            Spacer(modifier = Modifier.height(5.dp))

            Row(
                modifier = Modifier.fillMaxWidth().padding(start = 3.dp, end = 5.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text("Rating:${animeList.score}⭐️",
                    fontWeight = FontWeight.Bold,)



                Text("Episode:${animeList.episodes}",
                    fontWeight = FontWeight.Bold,)
            }

        }

    }

}