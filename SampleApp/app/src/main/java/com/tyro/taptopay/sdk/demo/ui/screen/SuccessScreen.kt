package com.tyro.taptopay.sdk.demo.ui.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tyro.taptopay.sdk.demo.R
import com.tyro.taptopay.sdk.demo.SdkDemoViewModel
import com.tyro.taptopay.sdk.demo.ui.theme.SdkDemoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuccessScreen(
  onDone: () -> Unit,
  onSendDigitalReceipt: (email: String) -> Unit,
  viewModel: SdkDemoViewModel = viewModel(),
) {
  SuccessScreenContent(
    amount = viewModel.getTransactionAmount(),
    surcharge = viewModel.state.collectAsState().value.surcharge,
    sendingEmail = viewModel.state.collectAsState().value.sendingEmail,
    onDone = onDone,
    onSendDigitalReceipt = onSendDigitalReceipt,
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuccessScreenContent(
  amount: String,
  surcharge: String?,
  sendingEmail: Boolean,
  onDone: () -> Unit,
  onSendDigitalReceipt: (email: String) -> Unit,
) {
  var email by remember { mutableStateOf("") }
  BackHandler(onBack = { onDone() })

  Scaffold(
    modifier = Modifier.navigationBarsPadding(),
    topBar = {
      CenterAlignedTopAppBar(
        title = {
          Image(
            modifier = Modifier.size(80.dp),
            painter = painterResource(id = R.drawable.tyro_logo_dark),
            contentDescription = stringResource(R.string.content_desc_tyro_logo),
          )
        },
        navigationIcon = {
          IconButton(onClick = onDone) {
            Icon(
              imageVector = Icons.Default.Close,
              contentDescription = stringResource(id = R.string.content_desc_close),
            )
          }
        },
      )
    },
    bottomBar = {
      Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
      ) {
        TextField(
          value = email,
          onValueChange = { email = it },
          modifier = Modifier
            .fillMaxWidth()
            .testTag("emailInput"),
          label = {
            Text(
              stringResource(R.string.digital_receipt_input_label),
              style = MaterialTheme.typography.titleSmall,
            )
          },
          keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        )
        Button(
          modifier = Modifier.fillMaxWidth(),
          contentPadding = PaddingValues(18.dp),
          onClick = { onSendDigitalReceipt(email) },
          colors = ButtonDefaults.buttonColors()
            .copy(containerColor = MaterialTheme.colorScheme.primary),
        ) {
          if (sendingEmail) {
            CircularProgressIndicator(
              color = MaterialTheme.colorScheme.onPrimary,
              modifier = Modifier.size(24.dp),
              strokeWidth = 4.dp,
            )
          } else {
            Text(
              text = stringResource(id = R.string.send_digital_receipt_button),
              style = MaterialTheme.typography.bodyMedium,
            )
          }
        }

        Button(
          onClick = { onDone() },
          contentPadding = PaddingValues(18.dp),
          modifier = Modifier.fillMaxWidth(),
        ) {
          Text(stringResource(R.string.done), style = MaterialTheme.typography.bodyMedium)
        }
      }
    },
  ) { padding ->
    Column(
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier
        .fillMaxSize()
        .padding(padding),
    ) {
      Image(
        painter = painterResource(id = R.drawable.ic_tick_80dp),
        contentDescription = stringResource(R.string.content_desc_eftpos_logo),
        modifier = Modifier
          .size(160.dp)
          .padding(8.dp),
      )
      Text(
        text = stringResource(R.string.approved),
        style = MaterialTheme.typography.bodyLarge,
      )
      Text(
        text = amount,
        fontSize = 64.sp,
        style = MaterialTheme.typography.bodyMedium,
      )
      if (surcharge != null) {
        val surchargeInDouble = surcharge.toDouble().div(100.0)
        Text(
          text = stringResource(
            R.string.surcharge_applied,
            "$%.${2}f".format(surchargeInDouble),
          ),
          style = MaterialTheme.typography.bodyMedium,
        )
      }
    }
  }
}

@Composable
@Preview
fun SuccessScreenContentPreview() {
  SdkDemoTheme {
    SuccessScreenContent(
      amount = "$10.00",
      surcharge = "30",
      sendingEmail = true,
      onDone = {},
      onSendDigitalReceipt = {},
    )
  }
}
