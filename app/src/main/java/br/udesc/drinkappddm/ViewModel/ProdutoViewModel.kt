package br.udesc.drinkappddm.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.udesc.drinkappddm.Model.Categoria
import br.udesc.drinkappddm.Model.Produto
import br.udesc.drinkappddm.repository.ProdutoRepository
import kotlinx.coroutines.launch


class ProdutoViewModel : ViewModel() {

    private val produtoRepository = ProdutoRepository()
    private val _categorias = MutableLiveData<List<Categoria>>()
    val categorias: LiveData<List<Categoria>> = _categorias

    suspend fun addProduto(produto: Produto) {
        viewModelScope.launch {
            produtoRepository.addProduto(produto)
        }
    }

    fun obterCategorias() {
        viewModelScope.launch {
            val categoriasList = produtoRepository.obterCategorias()
            _categorias.value = categoriasList
        }
    }

}