package br.udesc.drinkappddm.View

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import br.udesc.drinkappddm.Model.Categoria
import br.udesc.drinkappddm.R
import br.udesc.drinkappddm.View.Admin.ListagemProdutoEditActivity
import br.udesc.drinkappddm.ViewModel.CatalogoCategoriaViewModel
import br.udesc.drinkappddm.databinding.ActivityCatalogoCategoriaBinding
import br.udesc.drinkappddm.databinding.ActivityListagemCategoriaBinding

class ListagemCategoriaActivity : AppCompatActivity() {
    private lateinit var viewModel: CatalogoCategoriaViewModel
    private lateinit var binding: ActivityListagemCategoriaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListagemCategoriaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(CatalogoCategoriaViewModel::class.java)

        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1)
        val listView = findViewById<ListView>(R.id.listagemCategoriaListView)
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

        listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val categoria = viewModel.categorias.value?.get(position)
            if (categoria != null) {
                abrirListagemProduto(categoria)
            }
        }

        binding.listagemCategoriaListView.setOnItemClickListener { parent, view, position, id ->
            // Obtendo o item selecionado
            val itemSelecionado = parent.getItemAtPosition(position)

            // Verification se o item selecionado é uma instância de Categoria
            if (itemSelecionado is String) {
                // Aqui você pode criar uma instância de Categoria com base no item selecionado
                val categoriaSelecionada = Categoria(itemSelecionado)
                abrirListagemProduto(categoriaSelecionada)
            }
        }

    }

    private fun abrirListagemProduto(categoria: Categoria) {
        val intent = Intent(this, ListagemProdutoEditActivity::class.java)
        intent.putExtra("categoria", categoria)
        startActivity(intent)
    }
}
