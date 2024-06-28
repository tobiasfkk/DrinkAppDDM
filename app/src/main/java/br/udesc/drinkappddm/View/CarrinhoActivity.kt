package br.udesc.drinkappddm.View

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import br.udesc.drinkappddm.ViewModel.CarrinhoViewModel
import br.udesc.drinkappddm.Model.ItemCarrinho
import br.udesc.drinkappddm.ui.theme.GradientBackground

class CarrinhoActivity : ComponentActivity() {
    private lateinit var viewModel: CarrinhoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(CarrinhoViewModel::class.java)

        setContent {
            CarrinhoScreen(
                viewModel = viewModel,
                onFinalizarCompraClicked = { total ->
                    abrirTelaEndereco(total)
                }
            )
        }
    }

    private fun abrirTelaEndereco(total: Double) {
        val intent = Intent(this, EnderecoActivity::class.java)
        intent.putExtra("total", total)
        startActivity(intent)
    }
}

@Composable
fun CarrinhoScreen(
    viewModel: CarrinhoViewModel,
    onFinalizarCompraClicked: (Double) -> Unit
) {
    val itens by viewModel.itens.observeAsState(emptyList())

    // Recalcular o total sempre que os itens mudarem
    val total by remember(itens) {
        mutableStateOf(viewModel.calcularTotalCompra())
    }

    GradientBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(itens) { item ->
                    ProdutoItemCarrinho(itemCarrinho = item)
                }
            }

            Text(
                text = "Total: R$ %.2f".format(total),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333333),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            )

            Button(
                onClick = { onFinalizarCompraClicked(total) },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp)),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6EC7))
            ) {
                Text(text = "FINALIZAR COMPRA", color = Color.White)
            }
        }
    }
}

@Composable
fun ProdutoItemCarrinho(itemCarrinho: ItemCarrinho) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.White, RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Text(
            text = itemCarrinho.produto.nome,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF333333)
        )
        Text(
            text = "Preço: R$ ${itemCarrinho.produto.preco}",
            fontSize = 14.sp,
            color = Color(0xFF333333)
        )
        Text(
            text = itemCarrinho.produto.descricao,
            fontSize = 14.sp,
            color = Color(0xFF333333)
        )
        Text(
            text = "Quantidade: ${itemCarrinho.quantidade}",
            fontSize = 14.sp,
            color = Color(0xFF333333)
        )
    }
}
