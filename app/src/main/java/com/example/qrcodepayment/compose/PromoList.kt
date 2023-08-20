package com.example.qrcodepayment.compose

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
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

    LaunchedEffect(Unit) {
        viewModel.fetchPromos()
    }
    Column {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Promo BNI",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(16.dp)
            )
        }
        LazyColumn {
            items(promos.chunked(2)) { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    row.forEach { promo ->
                        PromoCard(promo, navController)
                    }
                }
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
                    val intent = Intent(context, MainActivity::class.java)
                    context.startActivity(intent)
                },
                modifier = Modifier
                    .fillMaxWidth().padding(bottom = 15.dp), // Add some top padding
                shape = RoundedCornerShape(20),
            ) {
                Box {
                    Text(
                        text = "Home",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }

}

@Composable
fun PromoCard(promo: Promo, navController: NavController, modifier: Modifier = Modifier) {

    Card(
        modifier = modifier
            .padding(8.dp)
            .width(180.dp)
            .clickable {
                navController.navigate("promoDetail/${promo.id}")
            },
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = rememberImagePainter(promo.img.url),
                contentDescription = null,
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = promo.nama, style = MaterialTheme.typography.titleMedium)
        }
    }
}