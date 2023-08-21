package com.example.qrcodepayment.compose

import android.content.Intent
import android.media.Image
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.qrcodepayment.controller.API
import com.example.qrcodepayment.controller.MainActivity
import com.example.qrcodepayment.data.API.Promo
import com.example.qrcodepayment.presentation.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPromo(promoId: String, viewModel: MainViewModel = hiltViewModel()) {
    val promo = remember { mutableStateOf<Promo?>(null) }
    val context = LocalContext.current

    LaunchedEffect(promoId) {
        promo.value = viewModel.promos.value.firstOrNull { it.id == promoId }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Image(
                        painter = rememberAsyncImagePainter("https://upload.wikimedia.org/wikipedia/id/thumb/5/55/BNI_logo.svg/2560px-BNI_logo.svg.png"),
                        contentDescription = null,
                        modifier = Modifier
                            .size(100.dp)
                            .padding(0.dp),
                    )
                    Text(
                        text = "Detail Promo",
                        modifier = Modifier.fillMaxWidth().padding(top = 50.dp), // Center-align the text
                        style = MaterialTheme.typography.titleLarge, // Apply a suitable typography style
                        textAlign = TextAlign.Center
                    )
                }
            )
        }
    ) { padding ->
        if (promo.value != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                Image(
                    painter = rememberImagePainter(promo.value!!.img.url),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp) // Adjust the image height
                        .clip(shape = RoundedCornerShape(8.dp)) // Add rounded corners
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                        top = 4.dp,
                        end = 8.dp)
                        .align(Alignment.End),
                    text = promo.value!!.nama,
                    style = MaterialTheme.typography.titleMedium, // Use a larger font size
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = promo.value!!.desc,
                    style = MaterialTheme.typography.titleMedium, // Use a smaller font size
                    color = MaterialTheme.colorScheme.onSurface // Match text color to the background
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Lokasi : ${promo.value!!.lokasi}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 4.dp,
                            end = 8.dp)
                        .align(Alignment.End),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.End
                )
            }

        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 0.dp, horizontal = 25.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalAlignment = Alignment.Start
        ) {

            Divider(thickness = 2.dp)

            Button(
                onClick = {
                    val intent = Intent(context, API::class.java)
                    context.startActivity(intent)
                },
                modifier = Modifier
                    .fillMaxWidth().padding(top = 700.dp), // Add some top padding
                shape = RoundedCornerShape(20),
            ) {
                Box {
                    Text(
                        text = "Back Promo",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}


