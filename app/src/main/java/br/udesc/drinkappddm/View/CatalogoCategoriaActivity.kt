package br.udesc.drinkappddm.View

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import br.udesc.drinkappddm.Model.Categoria
import br.udesc.drinkappddm.ViewModel.CatalogoCategoriaViewModel

class CatalogoCategoriaActivity : ComponentActivity() {

    private lateinit var viewModel: CatalogoCategoriaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CatalogoCategoriaViewModel::class.java)

        setContent {
            val categorias = viewModel.categorias.observeAsState(emptyList())
            CatalogoCategoriaScreen(categorias.value) { categoria ->
                abrirTelaProdutos(categoria)
            }
        }

        viewModel.obterCategorias()
    }

    private fun abrirTelaProdutos(categoria: Categoria) {
        val intent = Intent(this, CatalogoProdutoActivity::class.java)
        intent.putExtra("categoria", categoria)
        startActivity(intent)
    }
}

@Composable
fun CatalogoCategoriaScreen(categorias: List<Categoria>, onCategoriaClick: (Categoria) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color.Blue, Color.Cyan)
                )
            )
            .padding(16.dp)
    ) {
        items(categorias) { categoria ->
            Text(
                text = categoria.nome,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
                    .clickable { onCategoriaClick(categoria) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CatalogoCategoriaScreenPreview() {
    CatalogoCategoriaScreen(
        categorias = listOf(Categoria("Bebidas"), Categoria("Comidas")),
        onCategoriaClick = {}
    )
}
