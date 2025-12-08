package com.tyro.taptopay.sdk.demo.model

import kotlinx.serialization.Serializable

@Serializable
data class ProductInfo(
  val title: String,
  val description: String,
  val amount: Double,
  val image: Int,
)
