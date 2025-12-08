package com.tyro.taptopay.sdk.demo.data

import com.tyro.taptopay.sdk.demo.R
import com.tyro.taptopay.sdk.demo.model.ProductInfo

object SampleProducts {
  val products =
    listOf(
      ProductInfo(
        title = "Blue Designer Tee",
        description = "Tyro designer Tee made only from the best materials",
        amount = 22.0,
        image = R.drawable.blue,
      ),
      ProductInfo(
        title = "White Designer Tee",
        description = "Tyro designer Tee made only from the best materials",
        amount = 8.00,
        image = R.drawable.white,
      ),
      ProductInfo(
        title = "Black Designer Tee",
        description = "Tyro designer Tee made only from the best materials",
        amount = 38.00,
        image = R.drawable.black,
      ),
      ProductInfo(
        title = "Grey Designer Tee",
        description = "Tyro designer Tee made only from the best materials",
        amount = 11.00,
        image = R.drawable.grey,
      ),
      ProductInfo(
        title = "Yellow Designer Tee",
        description = "Tyro designer Tee made only from the best materials",
        amount = 27.00,
        image = R.drawable.yellow,
      ),
      ProductInfo(
        title = "Orange Designer Tee",
        description = "Tyro designer Tee made only from the best materials",
        amount = 18.00,
        image = R.drawable.orange,
      ),
    )
}
