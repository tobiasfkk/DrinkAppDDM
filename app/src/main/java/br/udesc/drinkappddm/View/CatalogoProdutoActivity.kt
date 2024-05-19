package br.udesc.drinkappddm.View

import ProdutoAdapter
import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import br.udesc.drinkappddm.Model.Categoria
import br.udesc.drinkappddm.R
import br.udesc.drinkappddm.ViewModel.CatalogoProdutoViewModel
import br.udesc.drinkappddm.Model.Produto
import br.udesc.drinkappddm.databinding.ActivityCatalogoProdutoBinding

class CatalogoProdutoActivity : AppCompatActivity() {
    private lateinit var viewModel: CatalogoProdutoViewModel
    private lateinit var binding: ActivityCatalogoProdutoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCatalogoProdutoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(CatalogoProdutoViewModel::class.java)

        val listView = findViewById<ListView>(R.id.produtoListView)

        // Obtém a categoria selecionada da Intent
        val categoriaSelecionada = intent.getSerializableExtra("categoria") as? Categoria

        if (categoriaSelecionada != null) {
            viewModel.produtos.observe(this) { produtos ->
                produtos?.let {
                    // Instancia o adaptador personalizado
                    val adapter = ProdutoAdapter(this, it)
                    listView.adapter = adapter
                }
            }

            // Chama o método para obter os produtos por categoria
            viewModel.obterProdutosPorCategoria(categoriaSelecionada)
        }
    }
}
