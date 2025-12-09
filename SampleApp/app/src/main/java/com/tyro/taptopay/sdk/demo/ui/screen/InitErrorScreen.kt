// kotlin
package com.tyro.taptopay.sdk.demo.ui.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tyro.taptopay.sdk.demo.R
import com.tyro.taptopay.sdk.demo.SdkDemoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InitErrorScreen(
  onClose: () -> Unit = {},
  viewModel: SdkDemoViewModel = viewModel(),
) {
  val state = viewModel.state.collectAsState().value
  BackHandler(onBack = { onClose() })
  InitErrorScreenContent(
    errorMessage = state.errorMessage,
    onClose = onClose,
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InitErrorScreenContent(
  errorMessage: String,
  onClose: () -> Unit,
) {
  Scaffold(
    topBar = {
      CenterAlignedTopAppBar(
        title = {
          Image(
            painter = painterResource(id = R.drawable.tyro_logo_dark),
            contentDescription = stringResource(R.string.content_desc_tyro_logo),
            modifier = Modifier.size(80.dp),
          )
        },
        navigationIcon = {
          IconButton(onClick = onClose) {
            Icon(
              imageVector = Icons.Default.Close,
              contentDescription = stringResource(R.string.content_desc_close),
            )
          }
        },
      )
    },
    bottomBar = {
      Column(
        modifier =
          Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
      ) {
        Button(
          onClick = onClose,
          contentPadding = PaddingValues(18.dp),
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        ) {
          Text(
            text = stringResource(R.string.close),
            style = MaterialTheme.typography.bodyMedium,
          )
        }
      }
    },
  ) { innerPadding ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center,
    ) {
      Image(
        painter = painterResource(id = R.drawable.ic_warning_80dp),
        contentDescription = stringResource(R.string.content_desc_error),
        modifier = Modifier
          .size(160.dp)
          .padding(8.dp),
      )
      Text(
        text = stringResource(R.string.initialisation_failed),
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onSurface,
      )
      Text(
        text = errorMessage,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp),
      )
    }
  }
}

@Preview(showBackground = true)
@Composable
private fun InitErrorScreenPreview() {
  InitErrorScreenContent(
    errorMessage = "Initialisation failed due to network timeout.",
    onClose = {},
  )
}
