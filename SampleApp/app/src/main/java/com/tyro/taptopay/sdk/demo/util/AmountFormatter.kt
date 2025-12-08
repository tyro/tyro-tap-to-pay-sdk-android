package com.tyro.taptopay.sdk.demo.util

object AmountFormatter {
  private const val MAX_AMOUNT_LENGTH = 11 // "$999999.99" format
  private const val CENTS_IN_DOLLAR = 100

  fun formatCentsAsCurrency(cents: Long): String {
    val dollars = cents / CENTS_IN_DOLLAR
    val remainingCents = cents % CENTS_IN_DOLLAR
    return "$%d.%02d".format(dollars, remainingCents)
  }

  fun addDigit(
    currentAmount: String,
    digit: String,
  ): String {
    val amountInCents = extractCents(currentAmount)
    val newAmountInCents = (amountInCents.toString() + digit).toLongOrNull() ?: return currentAmount
    val newAmount = formatCentsAsCurrency(newAmountInCents)

    // Don't exceed maximum amount length
    return if (newAmount.length >= MAX_AMOUNT_LENGTH) {
      currentAmount
    } else {
      newAmount
    }
  }

  fun removeLastDigit(currentAmount: String): String {
    val amountInCents = extractCents(currentAmount)
    val newAmountInCents =
      if (amountInCents > 0) {
        amountInCents / 10
      } else {
        0L
      }
    return formatCentsAsCurrency(newAmountInCents)
  }

  fun clearAmount(): String = formatCentsAsCurrency(0)

  private fun extractCents(formattedAmount: String): Long {
    val digitsOnly = formattedAmount.filter { it.isDigit() }
    return digitsOnly.toLongOrNull() ?: 0L
  }

  fun toCents(formattedAmount: String): Int = extractCents(formattedAmount).toInt()
}
