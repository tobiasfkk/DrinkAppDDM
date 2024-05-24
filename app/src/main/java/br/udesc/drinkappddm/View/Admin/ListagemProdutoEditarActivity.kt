package br.udesc.drinkappddm.View.Admin

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import br.udesc.drinkappddm.Model.Categoria
import br.udesc.drinkappddm.Model.Produto
import br.udesc.drinkappddm.ViewModel.CatalogoProdutoViewModel
import br.udesc.drinkappddm.databinding.ActivityListarProdutosBinding

class ListagemProdutoEditActivity : AppCompatActivity() {
    private lateinit var viewModel: CatalogoProdutoViewModel
    private lateinit var binding: ActivityListarProdutosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListarProdutosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(CatalogoProdutoViewModel::class.java)

        val listView: ListView = binding.listagemProdutoEditListView

        // Obtém a categoria selecionada da Intent
        val categoriaSelecionada = intent.getSerializableExtra("categoria") as? Categoria

        if (categoriaSelecionada != null) {
            viewModel.produtos.observe(this) { produtos ->
                produtos?.let {
                    val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, it.map { produto -> produto.nome })
                    listView.adapter = adapter
                }
            }

            // Chama o método para obter os produtos por categoria
            viewModel.obterProdutosPorCategoria(categoriaSelecionada)
        }

        listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val produto = viewModel.produtos.value?.get(position)
            if (produto != null) {
                abrirTelaEdicaoProduto(produto)
            }
        }
    }

    private fun abrirTelaEdicaoProduto(produto: Produto) {
        val intent = Intent(this, EditarProdutoActivity::class.java)
        intent.putExtra("produto", produto)
        startActivity(intent)
    }
}
