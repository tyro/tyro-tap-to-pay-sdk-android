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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tyro.taptopay.sdk.demo.R

@Composable
fun HomeScreen(
    onSettings: () -> Unit,
    onPurchase: () -> Unit,
    onRefund: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(),
    ) {
        Image(
            painter = painterResource(id = R.drawable.tyro_logo_dark),
            contentDescription = stringResource(R.string.content_desc_tyro_logo),
            modifier = Modifier.size(120.dp),
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(stringResource(R.string.tap_to_pay_sdk_demo), style = typography.bodyLarge)
        Spacer(modifier = Modifier.weight(1f))
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
    HomeScreen({}, {}, {})
}
