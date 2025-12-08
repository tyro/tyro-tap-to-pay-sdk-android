package com.tyro.taptopay.sdk.demo.ui.screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tyro.taptopay.sdk.demo.R
import com.tyro.taptopay.sdk.demo.SdkDemoViewModel
import com.tyro.taptopay.sdk.demo.data.SampleProducts
import com.tyro.taptopay.sdk.demo.model.ProductInfo
import com.tyro.taptopay.sdk.demo.ui.theme.SdkDemoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoreDemoScreen(
  onAddClicked: (product: ProductInfo) -> Unit,
  onPayClicked: () -> Unit,
  onResetClicked: () -> Unit,
  onBackClicked: () -> Unit,
  viewModel: SdkDemoViewModel = viewModel(),
) {
  val state = viewModel.state.collectAsState().value

  StoreDemoScreenContent(
    products = viewModel.products,
    total = state.demoStoreTotal,
    onAddClicked = onAddClicked,
    onPayClicked = onPayClicked,
    onResetClicked = onResetClicked,
    onBackClicked = onBackClicked,
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoreDemoScreenContent(
  products: List<ProductInfo>,
  total: Double,
  onAddClicked: (product: ProductInfo) -> Unit,
  onPayClicked: () -> Unit,
  onResetClicked: () -> Unit,
  onBackClicked: () -> Unit,
) {
  val context = LocalContext.current
  val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
  val dividerColor = MaterialTheme.colorScheme.onSurfaceVariant
  Scaffold(
    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    topBar = {
      CenterAlignedTopAppBar(
        colors =
          TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
          ),
        title = {
          Image(
            alignment = Alignment.Center,
            painter = painterResource(id = R.drawable.teeshoplogo),
            contentDescription = "tshirt",
            modifier = Modifier
              .size(120.dp)
              .fillMaxWidth(),
          )
        },
        navigationIcon = {
          IconButton(onClick = onBackClicked) {
            Icon(
              imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
              contentDescription = "Back",
            )
          }
        },
        scrollBehavior = scrollBehavior,
        modifier =
          Modifier.drawWithContent {
            drawContent()
            drawLine(
              color = dividerColor,
              start = Offset(0f, size.height),
              end = Offset(size.width, size.height),
              strokeWidth = 1f,
            )
          },
      )
    },
    bottomBar = {
      BottomAppBar(
        containerColor = MaterialTheme.colorScheme.surface,
        modifier =
          Modifier.drawWithContent {
            drawContent()
            drawLine(
              color = dividerColor,
              start = Offset(0f, 0f),
              end = Offset(size.width, 0f),
              strokeWidth = 1f,
            )
          },
      ) {
        Row(Modifier.padding(16.dp, 0.dp), verticalAlignment = Alignment.CenterVertically) {
          Text(
            text = "Total: $%.${2}f".format(total),
            style = MaterialTheme.typography.labelLarge,
          )
          IconButton(
            onClick = {
              onResetClicked()
              Toast.makeText(
                context,
                "Total Cleared",
                Toast.LENGTH_SHORT,
              ).show()
            },
          ) {
            Icon(
              imageVector = Icons.Rounded.Refresh,
              contentDescription = "Clear Amount",
            )
          }
          Spacer(modifier = Modifier.weight(1f))
          Button(
            onClick = onPayClicked,
            enabled = total != 0.0,
          ) {
            Text(text = "Start Payment", style = typography.bodySmall)
          }
          Spacer(Modifier.padding(6.dp))
        }
      }
    },
  ) { innerPadding ->
    LazyVerticalGrid(
      modifier = Modifier.padding(innerPadding),
      columns = GridCells.Adaptive(minSize = 150.dp),
    ) {
      items(products) { product ->
        MessageCard(product) {
          onAddClicked(product)
          Toast.makeText(
            context,
            "Added $${product.amount} to total",
            Toast.LENGTH_SHORT,
          ).show()
        }
      }
    }
  }
}

@Composable
@Preview
fun StoreDemoScreenContentPreview() {
  SdkDemoTheme {
    StoreDemoScreenContent(
      products = SampleProducts.products,
      total = 45.50,
      onAddClicked = {},
      onPayClicked = {},
      onResetClicked = {},
      onBackClicked = {},
    )
  }
}

class ProductInfoPreviewProvider : PreviewParameterProvider<ProductInfo> {
  override val values = SampleProducts.products.asSequence()
}

@Composable
@Preview
fun MessageCard(
  @PreviewParameter(ProductInfoPreviewProvider::class) productInfo: ProductInfo,
  onAddClicked: () -> Unit = {},
) {
  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier.padding(16.dp),
  ) {
    Image(
      alignment = Alignment.Center,
      painter = painterResource(id = productInfo.image),
      contentDescription = "tshirt",
      modifier = Modifier
        .size(200.dp)
        .fillMaxWidth(),
    )
    Row(verticalAlignment = Alignment.CenterVertically) {
      Text(
        text = "$%.${2}f".format(productInfo.amount),
        style = MaterialTheme.typography.labelSmall,
        fontWeight = FontWeight(1000),
      )
      Spacer(modifier = Modifier.weight(1f))
      IconButton(
        onClick = onAddClicked,
        Modifier
          .size(30.dp)
          .padding(0.dp, 0.dp, 8.dp, 0.dp),
      ) {
        Icon(
          imageVector = Icons.Rounded.AddCircle,
          contentDescription = "Add",
          tint = MaterialTheme.colorScheme.primary,
        )
      }
    }
    Text(
      text = productInfo.title,
      style = MaterialTheme.typography.labelSmall,
      textAlign = TextAlign.Start,
      modifier = Modifier.fillMaxWidth(1f),
    )
    Text(
      text = productInfo.description,
      style = MaterialTheme.typography.labelSmall,
      textAlign = TextAlign.Start,
      fontWeight = FontWeight(300),
      modifier = Modifier.fillMaxWidth(1f),
    )
  }
}
