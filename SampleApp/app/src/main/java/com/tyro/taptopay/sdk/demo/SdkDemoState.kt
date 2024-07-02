package com.tyro.taptopay.sdk.demo

import com.tyro.taptopay.sdk.api.data.request.TransactionType

data class SdkDemoState(
    val errorMessage: String = "",
    val amountString: String = "$0.00",
    val transactionType: TransactionType = TransactionType.PURCHASE,
    val screen: SdkDemoScreen = SdkDemoScreen.HOME,
    val sendingEmail: Boolean = false,
    val transactionId: String? = null,
    val amountAuthorised: String? = null,
    val surcharge: String? = null,
)

enum class SdkDemoScreen {
    LOADING,
    HOME,
    AMOUNT,
    SUCCESS,
    TRANSACTION_ERROR,
    INIT_ERROR,
    READER_ID_INPUT,
}
