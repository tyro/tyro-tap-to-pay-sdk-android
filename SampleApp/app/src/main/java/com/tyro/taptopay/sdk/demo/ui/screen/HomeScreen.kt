package com.tyro.taptopay.sdk.demo.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tyro.taptopay.sdk.demo.BuildConfig
import com.tyro.taptopay.sdk.demo.DataStoreManager
import com.tyro.taptopay.sdk.demo.R
import com.tyro.taptopay.sdk.demo.ui.theme.SdkDemoTheme

@Composable
fun HomeScreen(
  sdkVersion: String,
  onSettings: () -> Unit,
  onPurchase: () -> Unit,
  onRefund: () -> Unit,
  onStoreDemo: () -> Unit,
) {
  val dataStoreManager = DataStoreManager(LocalContext.current)
  val currentReaderId by dataStoreManager.getReaderIdFlow().collectAsState("")
  Column(
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier.fillMaxSize(),
  ) {
    Image(
      painter = painterResource(id = R.drawable.tyro_logo_dark),
      contentDescription = stringResource(R.string.content_desc_tyro_logo),
      modifier = Modifier.size(80.dp),

      )
    Text(stringResource(R.string.tap_to_pay_sdk_demo), style = typography.bodyLarge)
    Text(
      stringResource(R.string.current_reader_id, currentReaderId),
      style = typography.bodyMedium,
      textAlign = TextAlign.Center,
      modifier = Modifier.padding(16.dp),
    )
    Text(
      text =
        stringResource(
          id = R.string.build_config,
          BuildConfig.VERSION_NAME,
          sdkVersion,
          BuildConfig.APPLICATION_ID,
          BuildConfig.BUILD_TYPE,
        ),
      textAlign = TextAlign.Center,
      style = typography.labelSmall,
      modifier = Modifier.padding(vertical = 16.dp, horizontal = 24.dp),
    )
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier.widthIn(0.dp, 400.dp),
    ) {
      Button(
        { onSettings() },
        contentPadding = PaddingValues(18.dp),
        modifier =
          Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 8.dp),
      ) {
        Text(stringResource(R.string.admin_settings), style = typography.bodyMedium)
      }
      Button(
        { onStoreDemo() },
        contentPadding = PaddingValues(18.dp),
        modifier =
          Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 8.dp),
      ) {
        Text(stringResource(R.string.store_demo), style = typography.bodyMedium)
      }
      Button(
        { onPurchase() },
        contentPadding = PaddingValues(18.dp),
        modifier =
          Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 8.dp),
      ) {
        Text(stringResource(R.string.purchase), style = typography.bodyMedium)
      }
      Button(
        { onRefund() },
        contentPadding = PaddingValues(18.dp),
        modifier =
          Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 8.dp),
      ) {
        Text(stringResource(R.string.refund), style = typography.bodyMedium)
      }
    }
    Spacer(modifier = Modifier.height(12.dp))
  }
}

@Composable
@Preview
fun HomeScreenPreview() {
  SdkDemoTheme {
    Surface {
      HomeScreen("3.0.0", {}, {}, {}, {})
    }
  }
}
