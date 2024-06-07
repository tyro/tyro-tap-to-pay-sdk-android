package com.tyro.taptopay.sdk.demo.ui.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tyro.taptopay.sdk.api.data.request.TransactionType
import com.tyro.taptopay.sdk.demo.R
import com.tyro.taptopay.sdk.demo.SdkDemoViewModel
import com.tyro.taptopay.sdk.demo.ui.theme.tyroDemoBlack
import com.tyro.taptopay.sdk.demo.ui.theme.tyroDemoDarkGrey
import com.tyro.taptopay.sdk.demo.ui.theme.tyroDemoGrey
import com.tyro.taptopay.sdk.demo.ui.theme.tyroDemoRed

@Composable
fun RowScope.AmountKey(
    text: String = "",
    imageVector: ImageVector? = null,
    fontWeight: FontWeight = FontWeight.Normal,
    color: Color = tyroDemoBlack,
    onClick: () -> Unit = {},
) {
    Button(
        modifier =
            Modifier
                .height(70.dp)
                .weight(1f)
                .padding(6.dp),
        onClick = onClick,
        shape = RoundedCornerShape(50),
        colors =
            ButtonDefaults.buttonColors(
                containerColor = tyroDemoGrey,
                contentColor = tyroDemoBlack,
            ),
    ) {
        imageVector?.let {
            Icon(
                imageVector = it,
                modifier = Modifier.size(40.dp),
                contentDescription = stringResource(R.string.content_desc_backspace),
                tint = tyroDemoBlack,
            )
        } ?: Text(
            text = text,
            fontSize = 24.sp,
            fontWeight = fontWeight,
            color = color,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AmountScreen(
    viewModel: SdkDemoViewModel = viewModel(),
    onNext: (String) -> Unit,
    onCancel: () -> Unit,
) {
    val state = viewModel.state.collectAsState().value

    BackHandler(onBack = { onCancel() })

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(),
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    colors =
                        topAppBarColors(
                            containerColor = Color.Transparent,
                            titleContentColor = tyroDemoBlack,
                        ),
                    title = {
                        Text(stringResource(R.string.tap_to_pay_sdk_demo), style = typography.bodyLarge)
                    },
                    navigationIcon = {
                        IconButton(onClick = { onCancel() }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = stringResource(R.string.content_desc_back),
                            )
                        }
                    },
                )
            },
        ) { padding ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(padding),
            ) {
                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text =
                        when (state.transactionType) {
                            TransactionType.PURCHASE -> stringResource(R.string.purchase)
                            TransactionType.REFUND -> stringResource(R.string.refund)
                        },
                    style = typography.bodyLarge,
                )
                Text(
                    state.amountString,
                    style = typography.bodyLarge,
                    fontSize = 48.sp,
                    color =
                        if (!state.amountString.isValidAmount()) {
                            tyroDemoDarkGrey
                        } else {
                            tyroDemoBlack
                        },
                )

                Spacer(modifier = Modifier.weight(1f))

                Column {
                    Row(Modifier.fillMaxWidth()) {
                        AmountKey("1") { viewModel.onAmountKeyPress("1") }
                        AmountKey("2") { viewModel.onAmountKeyPress("2") }
                        AmountKey("3") { viewModel.onAmountKeyPress("3") }
                    }
                    Row(Modifier.fillMaxWidth()) {
                        AmountKey("4") { viewModel.onAmountKeyPress("4") }
                        AmountKey("5") { viewModel.onAmountKeyPress("5") }
                        AmountKey("6") { viewModel.onAmountKeyPress("6") }
                    }
                    Row(Modifier.fillMaxWidth()) {
                        AmountKey("7") { viewModel.onAmountKeyPress("7") }
                        AmountKey("8") { viewModel.onAmountKeyPress("8") }
                        AmountKey("9") { viewModel.onAmountKeyPress("9") }
                    }
                    Row(Modifier.fillMaxWidth()) {
                        AmountKey(
                            text = "C",
                            color = tyroDemoRed,
                            fontWeight = FontWeight.Bold,
                        ) {
                            viewModel.onAmountClearAll()
                        }
                        AmountKey("0") { viewModel.onAmountKeyPress("0") }
                        AmountKey(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_backspace_24dp),
                        ) {
                            viewModel.onAmountClearLast()
                        }
                    }
                }
                Button(
                    { onNext(state.amountString) },
                    contentPadding = PaddingValues(18.dp),
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp, vertical = 8.dp),
                    enabled = state.amountString.isValidAmount(),
                ) {
                    Text(stringResource(R.string.proceed), style = typography.bodyMedium)
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

private fun String.isValidAmount(): Boolean {
    // sdk will throw if value is not between 0 - 100,000,000
    val amountInCents = this.replace("$", "").replace(".", "").toInt();
    return (amountInCents in 1..9_999_999);
}
