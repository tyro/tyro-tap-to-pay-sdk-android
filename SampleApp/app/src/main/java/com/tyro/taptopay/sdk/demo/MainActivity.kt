package com.tyro.taptopay.sdk.demo

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.lifecycleScope
import com.tyro.taptopay.sdk.api.TapToPaySdk
import com.tyro.taptopay.sdk.demo.SdkDemoScreen.AMOUNT
import com.tyro.taptopay.sdk.demo.SdkDemoScreen.HOME
import com.tyro.taptopay.sdk.demo.SdkDemoScreen.INIT_ERROR
import com.tyro.taptopay.sdk.demo.SdkDemoScreen.LOADING
import com.tyro.taptopay.sdk.demo.SdkDemoScreen.READER_ID_INPUT
import com.tyro.taptopay.sdk.demo.SdkDemoScreen.SUCCESS
import com.tyro.taptopay.sdk.demo.SdkDemoScreen.TRANSACTION_ERROR
import com.tyro.taptopay.sdk.demo.ui.screen.AmountScreen
import com.tyro.taptopay.sdk.demo.ui.screen.HomeScreen
import com.tyro.taptopay.sdk.demo.ui.screen.InitErrorScreen
import com.tyro.taptopay.sdk.demo.ui.screen.LoadingScreen
import com.tyro.taptopay.sdk.demo.ui.screen.ReaderIdInputScreen
import com.tyro.taptopay.sdk.demo.ui.screen.SuccessScreen
import com.tyro.taptopay.sdk.demo.ui.screen.TransactionErrorScreen
import com.tyro.taptopay.sdk.demo.ui.theme.SdkDemoTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val viewModel: SdkDemoViewModel by viewModels { SdkDemoViewModel.Factory }
    private lateinit var dataStoreManager: DataStoreManager

    private val tapToPaySdk: TapToPaySdk
        get() = (application as SdkDemoApplication).tapToPaySDK

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TapToPaySdkDemoApp(viewModel)
        }
        dataStoreManager = DataStoreManager(this@MainActivity.applicationContext)

        viewModel.showLoadingScreen()
        viewModel.showReaderIdInputScreen()

        // register a handler for transaction results
        tapToPaySdk.registerTransactionResultHandler(this) { transactionResult ->
            viewModel.onTransactionResult(transactionResult)
        }

    }

    private fun initSdkWithReaderId(readerId: String) {
        (application as SdkDemoApplication).connectionProvider.readerId = readerId
        viewModel.initTapToPaySdk(this@MainActivity)
        lifecycleScope.launch(Dispatchers.IO) {
            dataStoreManager.storeReaderId(readerId)
        }
    }

    private fun sendDigitalReceipt(email: String) {
        lifecycleScope.launch {
            // Note this will not work on the sandbox environment
            val emailQueued = viewModel.sendDigitalReceipt(email)
            Toast.makeText(
                applicationContext,
                if (emailQueued) {
                    getText(
                        R.string.digital_receipt_success_msg,
                    )
                } else {
                    getText(R.string.digital_receipt_failed_msg)
                },
                Toast.LENGTH_SHORT,
            ).show()
        }
    }


    @Composable
    fun TapToPaySdkDemoApp(viewModel: SdkDemoViewModel) {
        val state = viewModel.state.collectAsState().value

        SdkDemoTheme {
            Surface(
                color = MaterialTheme.colorScheme.background,
            ) {
                when (state.screen) {
                    LOADING -> LoadingScreen()

                    READER_ID_INPUT ->
                        ReaderIdInputScreen(
                            onConfirmReaderId = { readerId ->
                                initSdkWithReaderId(readerId)
                            },
                        )

                    HOME ->
                        HomeScreen(
                            onSettings = {viewModel.updateAdminSettings(this)},
                            onPurchase = { viewModel.beginPurchaseFlow() },
                            onRefund = { viewModel.beginRefundFlow() },
                        )

                    AMOUNT ->
                        AmountScreen(
                            onNext = { formattedAmount ->
                                try {
                                    viewModel.startTransaction(this, formattedAmount)
                                } catch (e: SecurityException) {
                                    Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                                }
                            },
                            onCancel = { viewModel.resetToHome() },
                        )

                    SUCCESS ->
                        SuccessScreen(
                            onDone = { viewModel.resetToHome() },
                            onSendDigitalReceipt = { email -> sendDigitalReceipt(email) },
                        )

                    TRANSACTION_ERROR ->
                        TransactionErrorScreen(
                            onDone = { viewModel.resetToHome() },
                            onSendDigitalReceipt = { email -> sendDigitalReceipt(email) },
                        )

                    INIT_ERROR ->
                        InitErrorScreen(
                            onClose = { this.finish() },
                        )
                }
            }
        }
    }
}
