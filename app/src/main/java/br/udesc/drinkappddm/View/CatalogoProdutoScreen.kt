package br.udesc.drinkappddm.View

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.lifecycle.viewmodel.compose.viewModel
import br.udesc.drinkappddm.Model.Categoria
import br.udesc.drinkappddm.Model.Produto
import br.udesc.drinkappddm.ViewModel.CarrinhoViewModel
import br.udesc.drinkappddm.ViewModel.CatalogoProdutoViewModel
import br.udesc.drinkappddm.ui.theme.GradientBackground

@Composable
fun CatalogoProdutoScreen(
    context: Context,
    categoriaSelecionada: Categoria,
    onVerCarrinhoClicked: () -> Unit,
    viewModel: CatalogoProdutoViewModel = viewModel()
) {
    val produtos by viewModel.produtos.observeAsState(emptyList())

    LaunchedEffect(Unit) {
        viewModel.obterProdutosPorCategoria(categoriaSelecionada)
    }

    GradientBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(produtos) { produto ->
                    ProdutoItem(context = context, produto = produto)
                }
            }

            Button(
                onClick = onVerCarrinhoClicked,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clip(RoundedCornerShape(8.dp)),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6EC7))
            ) {
                Text(text = "Ver Carrinho", color = Color.White)
            }
        }
    }
}

@Composable
fun ProdutoItem(context: Context, produto: Produto) {
    var expanded by remember { mutableStateOf(false) }
    val carrinhoViewModel: CarrinhoViewModel = viewModel()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.White, RoundedCornerShape(8.dp))
            .clickable { expanded = !expanded }
            .padding(16.dp)
    ) {
        Text(
            text = produto.nome,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF333333)
        )
        if (expanded) {
            Text(
                text = "Preço: R$ ${produto.preco}",
                fontSize = 14.sp,
                color = Color(0xFF333333)
            )
            Text(
                text = produto.descricao,
                fontSize = 14.sp,
                color = Color(0xFF333333)
            )
            Spacer(modifier = Modifier.height(8.dp))

            val quantidade = remember { mutableStateOf(1) }
            NumberPicker(
                value = quantidade.value,
                onValueChange = { quantidade.value = it },
                range = 1..5
            )
            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    carrinhoViewModel.adicionarAoCarrinho(produto, quantidade.value)
                    Toast.makeText(context, "${produto.nome} adicionado ao carrinho", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier.clip(RoundedCornerShape(8.dp)),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6EC7))
            ) {
                Text(text = "Adicionar ao carrinho", color = Color.White)
            }
        }
    }
}
