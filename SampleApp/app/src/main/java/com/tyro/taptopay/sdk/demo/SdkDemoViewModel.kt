package com.tyro.taptopay.sdk.demo

import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.tyro.taptopay.sdk.api.TapToPaySdk
import com.tyro.taptopay.sdk.api.data.request.TransactionRequest
import com.tyro.taptopay.sdk.api.data.request.TransactionType
import com.tyro.taptopay.sdk.api.data.response.InitResult
import com.tyro.taptopay.sdk.api.data.response.InitStatus
import com.tyro.taptopay.sdk.api.data.response.TransactionResult
import com.tyro.taptopay.sdk.api.data.response.TransactionStatus.TXN_CANCELLED
import com.tyro.taptopay.sdk.api.data.response.TransactionStatus.TXN_SUCCESS
import com.tyro.taptopay.sdk.demo.SdkDemoScreen.AMOUNT
import com.tyro.taptopay.sdk.demo.SdkDemoScreen.HOME
import com.tyro.taptopay.sdk.demo.SdkDemoScreen.INIT_ERROR
import com.tyro.taptopay.sdk.demo.SdkDemoScreen.LOADING
import com.tyro.taptopay.sdk.demo.SdkDemoScreen.SUCCESS
import com.tyro.taptopay.sdk.demo.SdkDemoScreen.TRANSACTION_ERROR
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID

class SdkDemoViewModel(private val tapToPaySdk: TapToPaySdk) : ViewModel() {
    private val _state = MutableStateFlow(SdkDemoState())
    val state: StateFlow<SdkDemoState> = _state

    fun showLoadingScreen() {
        _state.update { it.copy(screen = LOADING) }
    }

    fun onInitResult(initResult: InitResult) {
        when (initResult.status) {
            InitStatus.INIT_SUCCESS ->
                _state.update { it.copy(screen = HOME) }
            else ->
                _state.update {
                    it.copy(
                        screen = INIT_ERROR,
                        errorMessage = initResult.errorMessage(),
                    )
                }
        }
    }

    fun beginPurchaseFlow() {
        _state.update {
            it.copy(
                screen = AMOUNT,
                transactionType = TransactionType.PURCHASE,
            )
        }
    }

    fun beginRefundFlow() {
        _state.update {
            it.copy(
                screen = AMOUNT,
                transactionType = TransactionType.REFUND,
            )
        }
    }

    fun startTransaction(
        activity: ComponentActivity,
        formattedAmount: String,
    ) {
        val amountInCents = formattedAmount.replace("$", "").replace(".", "").toInt()
        val transactionRequest =
            TransactionRequest(
                type = state.value.transactionType,
                amountInCents = amountInCents,
                reference = UUID.randomUUID().toString(),
            )
        tapToPaySdk.startTransaction(activity, transactionRequest)
    }

    fun onTransactionResult(result: TransactionResult) {
        when (result.status) {
            TXN_CANCELLED ->
                _state.update { it.copy(screen = AMOUNT) }
            TXN_SUCCESS ->
                _state.update { it.copy(screen = SUCCESS, transactionId = result.detail?.transactionID) }
            else ->
                _state.update {
                    it.copy(
                        screen = TRANSACTION_ERROR,
                        errorMessage = result.errorMessage(),
                    )
                }
        }
    }

    suspend fun sendDigitalReceipt(email: String): Boolean {
        if (email.isNullOrBlank()) {
            return false
        }
        if (state.value.transactionId.isNullOrBlank()) {
            return false
        }
        _state.update { it.copy(sendingEmail = true) }
        val requestSent = tapToPaySdk.sendDigitalReceipt(state.value.transactionId!!, email)
        _state.update { it.copy(sendingEmail = false) }
        return requestSent
    }

    fun resetToHome() {
        _state.update { SdkDemoState() }
    }

    fun onAmountKeyPress(s: String) {
        val amountInCents =
            state.value.amountString.replace("$", "").replace(".", "")
                .toInt().toString() // strip leading zeroes
        val newAmountInCents = amountInCents + s
        val newAmountPadded = newAmountInCents.padStart(3, '0')
        val newAmount =
            "$" + newAmountPadded.substring(0, newAmountPadded.length - 2) +
                "." + newAmountPadded.substring(newAmountPadded.length - 2)
        _state.update {
            it.copy(amountString = newAmount)
        }
    }

    fun onAmountClearLast() {
        val amountInCents =
            state.value.amountString.replace("$", "").replace(".", "")
                .toInt().toString() // strip leading zeroes
        val newAmountInCents =
            if (amountInCents.length > 1) {
                amountInCents.substring(0, amountInCents.length - 1)
            } else {
                ""
            }
        val newAmountPadded = newAmountInCents.padStart(3, '0')
        val newAmount =
            "$" + newAmountPadded.substring(0, newAmountPadded.length - 2) + "." +
                newAmountPadded.substring(newAmountPadded.length - 2)
        _state.update {
            it.copy(amountString = newAmount)
        }
    }

    fun onAmountClearAll() {
        _state.update { it.copy(amountString = "$0.00") }
    }

    private fun InitResult.errorMessage() = "${this.status}\n${this.errorMessage.orEmpty()}"

    private fun TransactionResult.errorMessage() = "${this.status}\n${this.errorMessage.orEmpty()}"

    companion object {
        val Factory: ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    modelClass: Class<T>,
                    extras: CreationExtras,
                ): T {
                    val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
                    return SdkDemoViewModel(
                        (application as SdkDemoApplication).tapToPaySDK,
                    ) as T
                }
            }
    }
}
