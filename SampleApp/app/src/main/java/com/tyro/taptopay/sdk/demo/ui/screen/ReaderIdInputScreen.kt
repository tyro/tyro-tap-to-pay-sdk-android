package com.tyro.taptopay.sdk.demo.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.tyro.taptopay.sdk.demo.DataStoreManager
import com.tyro.taptopay.sdk.demo.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ReaderIdInputScreen(onConfirmReaderId: (String) -> Unit) {
    val dataStoreManager = DataStoreManager(LocalContext.current)
    val currentReaderId by dataStoreManager.getReaderIdFlow().collectAsState(initial = "")
    var readerId =
        remember(currentReaderId) {
            mutableStateOf(currentReaderId)
        }
    val focusManager = LocalFocusManager.current

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start,
        modifier =
        Modifier
            .background(Color.White)
            .fillMaxSize(),
    ) {
        Text(
            text =
            stringResource(
                R.string.input_reader_id_msg,
            ),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(vertical = 16.dp, horizontal = 24.dp),
        )
        Divider(color = Color.LightGray, modifier = Modifier.height(1.dp))
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier =
            Modifier
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        focusManager.clearFocus()
                    })
                }
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp),
        ) {
            Text(
                text = stringResource(R.string.input_reader_id_label),
                style = MaterialTheme.typography.labelMedium,
                modifier =
                Modifier
                    .run {
                        Modifier
                            .offset(y = 12.dp, x = 12.dp)
                            .zIndex(1f)
                            .background(Color.White)
                            .padding(2.dp)
                    }
                    .align(Alignment.Start),
            )
            OutlinedTextField(
                value = readerId.value,
                onValueChange = {
                    readerId.value = it
                },
                shape = RoundedCornerShape(24.dp),
                modifier =
                Modifier
                    .fillMaxWidth(),
                singleLine = true,
                keyboardActions =
                KeyboardActions(onDone = {
                    focusManager.clearFocus()
                }),
                textStyle = TextStyle(fontSize = 14.sp),
            )

            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { onConfirmReaderId(readerId.value) },
                contentPadding = PaddingValues(18.dp),
                modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp),
            ) {
                Text(stringResource(R.string.confirm), style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

@Preview
@Composable
fun ReaderIdInputScreenPreview() {
    return ReaderIdInputScreen({})
}
