package br.udesc.drinkappddm.ViewModel

import androidx.lifecycle.ViewModel
import br.udesc.drinkappddm.Model.Produto
import br.udesc.drinkappddm.repository.ProdutoRepository


class ProdutoViewModel : ViewModel() {

    private val produtoRepository = ProdutoRepository()

    suspend fun addProduto(produto: Produto) {
        produtoRepository.addProduto(produto)
    }
}