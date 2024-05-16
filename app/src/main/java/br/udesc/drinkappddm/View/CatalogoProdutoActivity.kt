package br.udesc.drinkappddm.View

import android.R
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import br.udesc.drinkappddm.Model.Categoria
import br.udesc.drinkappddm.ViewModel.CatalogoProdutoViewModel
import br.udesc.drinkappddm.databinding.ActivityCatalogoProdutoBinding

class CatalogoProdutoActivity : AppCompatActivity() {
    private lateinit var viewModel: CatalogoProdutoViewModel
    private lateinit var binding: ActivityCatalogoProdutoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCatalogoProdutoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(CatalogoProdutoViewModel::class.java)

        val adapter = ArrayAdapter<String>(this, R.layout.simple_list_item_1)
        val listView = findViewById<ListView>(br.udesc.drinkappddm.R.id.catalogoListView)
        listView.adapter = adapter

        // Obtém a categoria selecionada da Intent
        val categoriaSelecionada = intent.getSerializableExtra("categoria") as? Categoria

        if (categoriaSelecionada != null) {
            viewModel.produtos.observe(this) { produtos ->
                produtos?.let {
                    adapter.clear()
                    for (produto in it) {
                        adapter.add(produto.nome)
                    }
                }
            }
        }

        if (categoriaSelecionada != null) {
            viewModel.produtos.observe(this) { produtos ->
                produtos?.let {
                    adapter.clear()
                    for (produto in it) {
                        adapter.add(produto.nome)
                    }
                }
                viewModel.obterProdutosPorCategoria(categoriaSelecionada) // Chama o método dentro do bloco if
            }
        }

    }
}