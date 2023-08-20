package com.example.qrcodepayment.presentation

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.qrcodepayment.data.models.ScannedData
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue


@Composable
fun MainScreen(viewModel: MainViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val state = viewModel.state.collectAsState()
    val detailList: List<ScannedData> by viewModel.listData.observeAsState(initial = listOf())
    val sharedPreferences = LocalContext.current.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
    var money by rememberSaveable { mutableStateOf(sharedPreferences.getInt("money", 1500000)) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 0.dp, horizontal = 25.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Image(
            painter = rememberAsyncImagePainter("https://upload.wikimedia.org/wikipedia/id/thumb/5/55/BNI_logo.svg/2560px-BNI_logo.svg.png"),
            contentDescription = null,
            modifier = Modifier
                .size(128.dp)
                .padding(0.dp),
        )


        Text(
            text = "Saldo : IDR  $money"
        )

        Divider(thickness = 1.dp)

        Button(
            onClick = viewModel::startScanning,
            modifier = Modifier
                .fillMaxWidth().padding(bottom = 0.dp), // Add some top padding
            shape = RoundedCornerShape(20),
        ) {
            Box {
                Text(
                    text = "SCAN QRIS",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }


        Divider(thickness = 1.dp)
        Text(
            text = "Transaction History",
            fontSize = 24.sp
        )

        // Just a fake data... a Pair of Int and String
        val tableData = (1..100).mapIndexed { index, item ->
            index to "Item $index"
        }

        // Each cell of a column must have the same weight.
        val column1Weight = .7f // 70%
        val column2Weight = .3f // 30%
        // The LazyColumn will be our table. Notice the use of the weights below\
        LazyColumn(Modifier.fillMaxSize()) {

            // Here is the header
            item {
                Row(Modifier.background(Color.LightGray)) {
                    TableCell(text = "Keterangan", weight = column1Weight)
                    TableCell(text = "Nominal", weight = column2Weight)
                }
            }

            if (detailList.isNotEmpty()) {
                items(items = detailList) { dtl ->
                    Row(Modifier.fillMaxWidth()) {
                        TableCell(text = dtl.name, weight = column1Weight)
                        TableCell(text = "IDR ${dtl.transaction}", weight = column2Weight)
                    }
                    Divider(thickness = 1.dp)
                }
            }
        }
    }
}

@Composable
fun RowScope.TableCell(
    text: String,
    weight: Float
) {
    Text(
        text = text,
        Modifier
            .weight(weight)
            .padding(8.dp)
    )
}


