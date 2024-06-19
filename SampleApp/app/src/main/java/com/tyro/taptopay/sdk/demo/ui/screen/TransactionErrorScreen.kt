package com.tyro.taptopay.sdk.demo.ui.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tyro.taptopay.sdk.api.TapToPaySdk
import com.tyro.taptopay.sdk.api.TyroEnvStub
import com.tyro.taptopay.sdk.demo.R
import com.tyro.taptopay.sdk.demo.SdkDemoViewModel
import com.tyro.taptopay.sdk.demo.ui.theme.tyroDemoBlack
import com.tyro.taptopay.sdk.demo.ui.theme.tyroDemoRed

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionErrorScreen(
    onDone: () -> Unit,
    onSendDigitalReceipt: (email: String) -> Unit,
    viewModel: SdkDemoViewModel = viewModel(),
) {
    val state = viewModel.state.collectAsState().value
    var email by remember { mutableStateOf("") }
    BackHandler(onBack = { onDone() })
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(),
    ) {
        Box {
            Image(
                painter = painterResource(id = R.drawable.tyro_logo_dark),
                contentDescription = stringResource(R.string.content_desc_tyro_logo),
                modifier =
                Modifier
                    .size(120.dp)
                    .align(Alignment.Center),
            )
            TopAppBar(
                colors =
                TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color.Transparent,
                    navigationIconContentColor = tyroDemoBlack,
                ),
                title = { },
                navigationIcon = {
                    IconButton(onClick = onDone) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = stringResource(R.string.content_desc_close),
                        )
                    }
                },
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = R.drawable.ic_warning_80dp),
            contentDescription = stringResource(R.string.content_desc_error),
            modifier =
            Modifier
                .size(80.dp)
                .padding(8.dp),
        )
        Text(
            text = stringResource(R.string.transaction_failed),
            color = tyroDemoBlack,
            style = MaterialTheme.typography.bodyLarge,
        )
        Text(
            text = state.errorMessage,
            textAlign = TextAlign.Center,
            color = tyroDemoBlack,
            style = MaterialTheme.typography.bodyMedium,
        )
        Text(
            text = viewModel.getTransactionAmount(),
            color = tyroDemoRed,
            fontSize = 32.sp,
            style = MaterialTheme.typography.bodyMedium,
        )

        Spacer(modifier = Modifier.weight(1f))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.widthIn(0.dp, 400.dp),
        ) {
            TextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 0.dp),
                label = { Text(stringResource(R.string.digital_receipt_input_label), style = MaterialTheme.typography.titleSmall) },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            )
            Button(
                modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 3.dp),
                contentPadding = PaddingValues(8.dp),
                onClick = { onSendDigitalReceipt(email) },
            ) {
                if (state.sendingEmail) {
                    CircularProgressIndicator(
                        color = Color.White,
                    )
                } else {
                    Text(
                        text = stringResource(id = R.string.send_digital_receipt_button),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
            Button(
                onClick = onDone,
                contentPadding = PaddingValues(8.dp),
                modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 8.dp),
            ) {
                Text(stringResource(R.string.close), style = MaterialTheme.typography.bodyMedium)
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Preview
@Composable
fun TransactionErrorScreenPreview() {
    return TransactionErrorScreen(
        viewModel =
        SdkDemoViewModel(
            TapToPaySdk.createInstance(
                TyroEnvStub(),
            ),
        ),
        onDone = {},
        onSendDigitalReceipt = {},
    )
}
