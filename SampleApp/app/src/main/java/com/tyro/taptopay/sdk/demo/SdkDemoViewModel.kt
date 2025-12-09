package com.tyro.taptopay.sdk.demo

import android.app.Activity
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.tyro.taptopay.sdk.api.TapToPaySdk
import com.tyro.taptopay.sdk.api.TapToPayUxOptions
import com.tyro.taptopay.sdk.api.TapToPayUxOptions.TapZone
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
import com.tyro.taptopay.sdk.demo.SdkDemoScreen.READER_ID_INPUT
import com.tyro.taptopay.sdk.demo.SdkDemoScreen.STORE_DEMO
import com.tyro.taptopay.sdk.demo.SdkDemoScreen.SUCCESS
import com.tyro.taptopay.sdk.demo.SdkDemoScreen.TRANSACTION_ERROR
import com.tyro.taptopay.sdk.demo.data.SampleProducts
import com.tyro.taptopay.sdk.demo.util.AmountFormatter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.util.UUID

class SdkDemoViewModel(private val tapToPaySdk: TapToPaySdk) : ViewModel() {
  private val _state = MutableStateFlow(SdkDemoState())
  val state: StateFlow<SdkDemoState> = _state
  val products = SampleProducts.products

  fun incrementTotal(amount: Double) {
    _state.update {
      it.copy(
        demoStoreTotal = it.demoStoreTotal + amount,
      )
    }
  }

  fun resetTotal() {
    _state.update {
      it.copy(
        demoStoreTotal = 0.0,
      )
    }
  }

  fun showLoadingScreen() {
    _state.update { it.copy(screen = LOADING) }
  }

  fun showReaderIdInputScreen() {
    _state.update { it.copy(screen = READER_ID_INPUT) }
  }

  /**
   * Determine the appropriate Tap Zone based on device screen size.
   * It is advised to adjust the Tap Zone for specific device types to optimize user experience
   * based on screen real estate and NFC reader placement.
   *
   * This is a basic implementation that you should further refined based on actual device
   * characteristics.
   */
  private fun Activity.getDeviceSpecificTapZone(): TapZone {
    val metrics = resources.displayMetrics
    val widthDp = metrics.widthPixels / metrics.density
    val heightDp = metrics.heightPixels / metrics.density
    return if (widthDp >= 600 && heightDp >= 600) {
      TapZone.Large.OnDevice(
        position = TapZone.Large.OnDevice.NfcPosition.CENTER,
        orientation = TapZone.DeviceOrientation.PORTRAIT,
      )
    } else {
      // Phone or small screen
      TapZone.Small.OnDevice(
        position = TapZone.Small.OnDevice.NfcPosition.CENTER,
      )
    }
  }

  fun initTapToPaySdk(activity: ComponentActivity) {
    // initialise the Tyro Tap to Pay SDK
    // this could take some time, so show a progress spinner
    showLoadingScreen()
    tapToPaySdk.init(activity) { initResult ->
      onInitResult(initResult)
    }
    viewModelScope.launch {
      tapToPaySdk.setTapToPayUxOptions(
        TapToPayUxOptions(
          tapZone = activity.getDeviceSpecificTapZone(),
        ),
      )
    }
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

  fun storeDemo() {
    _state.update {
      it.copy(
        screen = STORE_DEMO,
      )
    }
  }

  fun updateAdminSettings(activity: ComponentActivity) {
    tapToPaySdk.updateAdminSettings(activity)
  }

  fun startTransaction(
    activity: ComponentActivity,
    formattedAmount: String,
  ) {
    val amountInCents = AmountFormatter.toCents(formattedAmount)
    val transactionRequest =
      TransactionRequest(
        type = state.value.transactionType,
        amountInCents = amountInCents,
        reference = UUID.randomUUID().toString(),
        posInfo = null,
      )
    tapToPaySdk.startTransaction(activity, transactionRequest)
  }

  fun onTransactionResult(result: TransactionResult) {
    when (result.status) {
      TXN_CANCELLED -> {
        _state.update {
          it.copy(
            screen = AMOUNT,
            transactionId = result.detail?.transactionID,
          )
        }
      }

      TXN_SUCCESS -> {
        _state.update {
          it.copy(
            screen = SUCCESS,
            transactionId = result.detail?.transactionID,
          )
        }
      }

      else ->
        _state.update {
          it.copy(
            screen = TRANSACTION_ERROR,
            errorMessage = result.errorMessage(),
            transactionId = result.detail?.transactionID,
          )
        }
    }
    _state.update {
      it.copy(
        amountAuthorised = result.detail?.amount,
        surcharge = result.detail?.surcharge,
      )
    }
  }

  fun resetToHome() {
    _state.update { SdkDemoState() }
  }

  suspend fun sendDigitalReceipt(email: String): Boolean {
    if (email.isBlank()) {
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

  fun onAmountKeyPress(s: String) {
    val newAmount = AmountFormatter.addDigit(state.value.amountString, s)
    _state.update {
      it.copy(amountString = newAmount)
    }
  }

  fun onAmountClearLast() {
    val newAmount = AmountFormatter.removeLastDigit(state.value.amountString)
    _state.update {
      it.copy(amountString = newAmount)
    }
  }

  fun onAmountClearAll() {
    _state.update { it.copy(amountString = AmountFormatter.clearAmount()) }
  }

  fun getTransactionAmount(): String {
    val surcharge = (state.value.surcharge)?.toBigDecimal()?.divide(BigDecimal("100"))
    return if (state.value.amountAuthorised != null) {
      val amount = state.value.amountAuthorised!!.toBigDecimal().divide(BigDecimal("100"))
      "$%.${2}f".format(amount)
    } else if (state.value.demoStoreTotal > 0) {
      val total = state.value.demoStoreTotal.toBigDecimal() + (surcharge ?: BigDecimal("0.0"))
      "$%.${2}f".format(total)
    } else {
      val netAmount = state.value.amountString.replace("$", "").toBigDecimal()
      val total = netAmount + (surcharge ?: BigDecimal("0.0"))
      "$%.${2}f".format(total)
    }
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
        ): T = SdkDemoViewModel(
          TapToPaySdk.instance,
        ) as T
      }
  }
}
