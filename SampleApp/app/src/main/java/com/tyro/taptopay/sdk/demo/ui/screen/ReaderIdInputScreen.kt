package com.tyro.taptopay.sdk.demo.ui.screen

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tyro.taptopay.sdk.demo.BuildConfig
import com.tyro.taptopay.sdk.demo.DataStoreManager
import com.tyro.taptopay.sdk.demo.R
import com.tyro.taptopay.sdk.demo.ui.theme.SdkDemoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ReaderIdInputScreen(sdkVersion: String, onConfirmReaderId: (String) -> Unit) {
  val dataStoreManager = DataStoreManager(LocalContext.current)
  val currentReaderId by dataStoreManager.getReaderIdFlow().collectAsState(initial = "")
  val readerId = remember(currentReaderId) { mutableStateOf(currentReaderId) }
  val focusManager = LocalFocusManager.current

  Scaffold(
    modifier = Modifier.navigationBarsPadding(),
    topBar = {
      Column {
        CenterAlignedTopAppBar(
          title = {
            Text(
              stringResource(R.string.input_reader_id_msg),
              style = MaterialTheme.typography.bodyMedium,
            )
          },
          navigationIcon = {},
        )
        HorizontalDivider(color = MaterialTheme.colorScheme.onSurfaceVariant)
      }
    },
    bottomBar = {
      Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
      ) {
        Button(
          onClick = {
            onConfirmReaderId(readerId.value)
          },
          contentPadding = PaddingValues(18.dp),
          modifier = Modifier.fillMaxWidth(),
          enabled = readerId.value.isNotBlank(),
        ) {
          Text(stringResource(R.string.confirm), style = MaterialTheme.typography.bodyMedium)
        }
      }
    },
  ) { innerPadding ->
    Column(
      verticalArrangement = Arrangement.Top,
      modifier =
        Modifier
          .pointerInput(Unit) { detectTapGestures(onTap = { focusManager.clearFocus() }) }
          .padding(innerPadding)
          .padding(horizontal = 24.dp, vertical = 16.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      OutlinedTextField(
        label = {
          Text(
            text = stringResource(R.string.input_reader_id_label),
            style = MaterialTheme.typography.labelMedium,
          )
        },
        value = readerId.value,
        onValueChange = { readerId.value = it },
        shape = RoundedCornerShape(24.dp),
        modifier =
          Modifier
            .fillMaxWidth()
            .onFocusChanged { focusState ->
              if (!focusState.isFocused) readerId.value = readerId.value.trim()
            }
            .testTag("readerIdInput"),
        singleLine = true,
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        textStyle = TextStyle(fontSize = 14.sp),
      )

      Spacer(modifier = Modifier.weight(1f))

      Text(
        text = stringResource(id = R.string.tap_to_pay_sdk_demo),
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(vertical = 16.dp),
      )
      Text(
        text = stringResource(
          id = R.string.build_config,
          BuildConfig.VERSION_NAME,
          sdkVersion,
          BuildConfig.APPLICATION_ID,
          BuildConfig.BUILD_TYPE,
        ),
        style = MaterialTheme.typography.labelSmall,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(vertical = 16.dp),
      )
    }
  }
}

@Preview
@Composable
fun ReaderIdInputScreenPreview() {
  SdkDemoTheme {
    Surface {
      ReaderIdInputScreen("3.0.0") {}
    }
  }
}
