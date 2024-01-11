package com.tyro.taptopay.sdk.demo

import com.tyro.taptopay.sdk.api.data.request.TransactionType

data class SdkDemoState(
    val errorMessage: String = "",
    val amountString: String = "$0.00",
    val transactionType: TransactionType = TransactionType.PURCHASE,
    val screen: SdkDemoScreen = SdkDemoScreen.HOME,
)

enum class SdkDemoScreen {
    LOADING,
    HOME,
    AMOUNT,
    SUCCESS,
    TRANSACTION_ERROR,
    INIT_ERROR,
}
