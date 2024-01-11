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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tyro.taptopay.sdk.demo.R
import com.tyro.taptopay.sdk.demo.SdkDemoViewModel
import com.tyro.taptopay.sdk.demo.ui.theme.tyroDemoBlack

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuccessScreen(
    viewModel: SdkDemoViewModel,
    onDone: () -> Unit,
) {
    val state = viewModel.state.collectAsState().value

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
                            contentDescription = stringResource(id = R.string.content_desc_close),
                        )
                    }
                },
            )
        }
        Spacer(modifier = Modifier.weight(1f))

        Image(
            painter = painterResource(id = R.drawable.ic_tick_80dp),
            contentDescription = stringResource(R.string.content_desc_eftpos_logo),
            modifier =
                Modifier
                    .size(160.dp)
                    .padding(8.dp),
        )
        Text(
            text = stringResource(R.string.approved),
            color = tyroDemoBlack,
            style = MaterialTheme.typography.bodyLarge,
        )
        Text(
            text = state.amountString,
            color = tyroDemoBlack,
            fontSize = 64.sp,
            style = MaterialTheme.typography.bodyMedium,
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { onDone() },
            contentPadding = PaddingValues(18.dp),
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 8.dp),
        ) {
            Text(stringResource(R.string.done), style = MaterialTheme.typography.bodyMedium)
        }
        Spacer(modifier = Modifier.height(12.dp))
    }
}
