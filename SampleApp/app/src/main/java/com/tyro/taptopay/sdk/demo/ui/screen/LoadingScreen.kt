package com.tyro.taptopay.sdk.demo.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun LoadingScreen() {
  Column(
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier.fillMaxSize()
  ) {
    CircularProgressIndicator(
      color = MaterialTheme.colorScheme.primary,
      strokeWidth = 4.dp,
      modifier = Modifier.size(96.dp),
    )
  }
}

@Composable
@Preview(showBackground = true)
fun LoadingScreenPreview() {
  LoadingScreen()
}
