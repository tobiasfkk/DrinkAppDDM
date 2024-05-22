package br.udesc.drinkappddm.View

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import br.udesc.drinkappddm.Model.Categoria
import br.udesc.drinkappddm.R
import br.udesc.drinkappddm.ViewModel.CatalogoCategoriaViewModel
import br.udesc.drinkappddm.databinding.ActivityCatalogoCategoriaBinding

class CatalogoCategoriaActivity : AppCompatActivity() {
    private lateinit var viewModel: CatalogoCategoriaViewModel
    private lateinit var binding: ActivityCatalogoCategoriaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCatalogoCategoriaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(CatalogoCategoriaViewModel::class.java)

        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1)
        val listView = findViewById<ListView>(R.id.catalogoListView)
        listView.adapter = adapter

        // Obter categorias da ViewModel e preencher o Spinner
        viewModel.obterCategorias()
        // Observar as categorias na ViewModel
        viewModel.categorias.observe(this) { categorias ->
            categorias?.let {
                adapter.clear()
                for (categoria in it) {
                    adapter.add(categoria.nome)
                }
            }
        }

        // Iniciar a obtenção das categorias
        viewModel.obterCategorias()

        binding.catalogoListView.setOnItemClickListener { parent, view, position, id ->
            // Obtendo o item selecionado
            val itemSelecionado = parent.getItemAtPosition(position)

            // Verification se o item selecionado é uma instância de Categoria
            if (itemSelecionado is String) {
                // Aqui você pode criar uma instância de Categoria com base no item selecionado
                val categoriaSelecionada = Categoria(itemSelecionado)
                abrirTelaProdutos(categoriaSelecionada)
            }
        }
    }

    private fun abrirTelaProdutos(categoria: Categoria) {
        val intent = Intent(this, CatalogoProdutoActivity::class.java)
        intent.putExtra("categoria", categoria)
        startActivity(intent)
    }
}
