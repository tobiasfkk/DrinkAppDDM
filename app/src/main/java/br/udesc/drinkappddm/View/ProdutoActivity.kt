package br.udesc.drinkappddm.View
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import br.udesc.drinkappddm.Model.Produto
import br.udesc.drinkappddm.databinding.ActivityProdutoBinding
import br.udesc.drinkappddm.ViewModel.ProdutoViewModel

import kotlinx.coroutines.*


class ProdutoActivity : AppCompatActivity() {

    private lateinit var viewModel: ProdutoViewModel
    private lateinit var binding: ActivityProdutoBinding // Usando ViewBinding para acessar os componentes da UI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_produto)//setando a tela xml
        binding = ActivityProdutoBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Obtém a ViewModel, passando o contexto da atividade
        viewModel = ViewModelProvider(this).get(ProdutoViewModel::class.java)


        // Cria o objeto Produto
        //val produto = Produto(1, "Refrigerante", "https://example.com/refrigerante.png", 5.00)
        // Recuperar os valores dos campos da tela
        binding.produtoSalvar.setOnClickListener {
            val produtoNome: String = binding.produtoNome.text.toString()
            val produtoImagem: String = binding.produtoImagem.text.toString()
            val produtoPrecoStr: String = binding.produtoPreco.text.toString()
            val produtoPreco: Double = produtoPrecoStr.toDoubleOrNull() ?: 0.0

            val produto = Produto(produtoNome, produtoImagem, produtoPreco)
            // Adiciona o produto ao Firebase


            GlobalScope.launch(Dispatchers.IO) {
                viewModel.addProduto(produto)
            }
        }
    }
}

