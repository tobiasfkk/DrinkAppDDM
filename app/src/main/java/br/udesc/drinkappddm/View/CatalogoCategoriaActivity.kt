package br.udesc.drinkappddm.View

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import br.udesc.drinkappddm.Model.Categoria
import br.udesc.drinkappddm.ViewModel.CatalogoCategoriaViewModel
import br.udesc.drinkappddm.ui.theme.GradientBackground

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
    GradientBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            categorias.forEach { categoria ->
                Button(
                    onClick = { onCategoriaClick(categoria) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Text(
                        text = categoria.nome,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
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
