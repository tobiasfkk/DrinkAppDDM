package br.udesc.drinkappddm.View
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import br.udesc.drinkappddm.Model.Categoria
import br.udesc.drinkappddm.Model.Produto
import br.udesc.drinkappddm.databinding.ActivityProdutoBinding
import br.udesc.drinkappddm.ViewModel.ProdutoViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProdutoActivity : AppCompatActivity() {

    private lateinit var viewModel: ProdutoViewModel
    private lateinit var binding: ActivityProdutoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProdutoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(ProdutoViewModel::class.java)

        // Configurar o Spinner de Categorias
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.produtoCategoriaSpinner.adapter = adapter
        // Obter categorias da ViewModel e preencher o Spinner
        viewModel.obterCategorias()
        viewModel.categorias.observe(this) { categorias ->
            categorias?.let {
                adapter.clear()
                for (categoria in it) {
                    adapter.add(categoria.nome)
                }
            }
        }

        // Configurar o clique no botão "Salvar Produto"
        binding.produtoSalvar.setOnClickListener {
            salvarProduto()
        }
    }

    private fun salvarProduto() {
        val produtoNome = binding.produtoNome.text.toString()
        val produtoImagem = binding.produtoImagem.text.toString()
        val produtoPrecoStr = binding.produtoPreco.text.toString()
        val produtoPreco = produtoPrecoStr.toDoubleOrNull() ?: 0.0
        val categoriaSelecionada = binding.produtoCategoriaSpinner.selectedItem.toString()
        val descricao = binding.produtoDescricao.text.toString()
        val quantidadeEstoqueStr = binding.produtoQuantidadeEstoque.text.toString()
        val quantidadeEstoque = quantidadeEstoqueStr.toIntOrNull() ?: 0

        val produto = Produto(produtoNome, produtoImagem, produtoPreco, Categoria(categoriaSelecionada), descricao, quantidadeEstoque)


        GlobalScope.launch(Dispatchers.IO) {
            viewModel.addProduto(produto)
        }
    }
}
