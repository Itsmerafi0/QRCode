package com.example.qrcodepayment.compose

import android.content.Intent
import android.widget.ImageView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.qrcodepayment.controller.API
import com.example.qrcodepayment.controller.MainActivity
import com.example.qrcodepayment.controller.PromoDetail
import com.example.qrcodepayment.data.API.Promo
import com.example.qrcodepayment.presentation.MainViewModel

@Composable
fun PromoList(navController: NavController, viewModel: MainViewModel) {
    val promos by viewModel.promos.collectAsState(initial = emptyList())
    val context = LocalContext.current
    val colorScheme = MaterialTheme.colorScheme

    LaunchedEffect(Unit) {
        viewModel.fetchPromos()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter("https://upload.wikimedia.org/wikipedia/id/thumb/5/55/BNI_logo.svg/2560px-BNI_logo.svg.png"),
            contentDescription = null,
            modifier = Modifier
                .size(100.dp)
                .padding(0.dp),
        )
        Text(
            text = "Promo BNI",
            modifier = Modifier.fillMaxWidth().padding(top = 0.dp), // Center-align the text
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge,
        )
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(promos) { promo ->
                PromoAppCard(promo, navController)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val intent = Intent(context, MainActivity::class.java)
                context.startActivity(intent)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp),
            shape = RoundedCornerShape(20),
        ) {
            Text(
                text = "Ke Halaman Utama",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = colorScheme.onSecondary
            )
        }
    }
}

@Composable
fun PromoAppCard(promo: Promo, navController: NavController, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate("promoDetail/${promo.id}")
            },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp),
            shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Image(
                painter = rememberImagePainter(promo.img.url),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(shape = RoundedCornerShape(8.dp))
                    .graphicsLayer(
                        scaleX = 1f, // No horizontal scaling
                        scaleY = 1f // No vertical scaling
                    )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = promo.nama,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Lokasi : ${promo.lokasi}",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
                    .align(Alignment.End),
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}