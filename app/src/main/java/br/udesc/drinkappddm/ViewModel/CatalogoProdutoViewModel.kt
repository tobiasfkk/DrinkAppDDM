package br.udesc.drinkappddm.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.udesc.drinkappddm.Model.Categoria
import br.udesc.drinkappddm.Model.Produto
import br.udesc.drinkappddm.repository.ProdutoRepository
import kotlinx.coroutines.launch

class CatalogoProdutoViewModel : ViewModel() {
    private val produtoRepository = ProdutoRepository()
    private val _produtos = MutableLiveData<List<Produto>>()
    val produtos: LiveData<List<Produto>> get() = _produtos // Cria um LiveData para acessar os produtos
    fun obterProdutosPorCategoria(categoria: Categoria) {
        viewModelScope.launch {
            val produtosList = produtoRepository.obterProdutosPorCategoria(categoria)
            _produtos.value = produtosList // Atualiza os produtos na variável _products
        }
    }
}