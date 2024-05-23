package br.udesc.drinkappddm.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.udesc.drinkappddm.Model.ItemCarrinho
import br.udesc.drinkappddm.Model.Produto
import br.udesc.drinkappddm.repository.CarrinhoRepository
import kotlinx.coroutines.launch

class CarrinhoViewModel : ViewModel() {
    private val repository = CarrinhoRepository()
    private val _itens = MutableLiveData<MutableList<ItemCarrinho>>()
    val itens: LiveData<MutableList<ItemCarrinho>> get() = _itens

    init {
        _itens.value = mutableListOf()
        carregarItensDoCarrinho()
    }

    fun adicionarAoCarrinho(produto: Produto, quantidade: Int) {
        viewModelScope.launch {
            val lista = _itens.value ?: mutableListOf()
            val itemExistente = lista.find { it.produto.nome == produto.nome }
            if (itemExistente != null) {
                val novoItem = itemExistente.copy(quantidade = quantidade)
                lista[lista.indexOf(itemExistente)] = novoItem
                repository.atualizarItemCarrinho(novoItem)
            } else {
                val novoItem = ItemCarrinho(produto, quantidade)
                lista.add(novoItem)
                repository.addItemCarrinho(novoItem)
            }
            _itens.value = lista
        }
    }

    private fun carregarItensDoCarrinho() {
        viewModelScope.launch {
            val itensCarregados = repository.obterItensCarrinho()
            _itens.value = itensCarregados.toMutableList()
        }
    }

    fun limparCarrinho() {
        viewModelScope.launch {
            repository.limparCarrinho()
            _itens.value = mutableListOf()
        }
    }

    fun calcularTotalCompra(): Double {
        val lista = _itens.value ?: return 0.0
        return lista.sumByDouble { it.produto.preco * it.quantidade }
    }
}
