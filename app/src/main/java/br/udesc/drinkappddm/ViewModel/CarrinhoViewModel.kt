package br.udesc.drinkappddm.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.udesc.drinkappddm.Model.ItemCarrinho
import br.udesc.drinkappddm.Model.Produto

class CarrinhoViewModel : ViewModel() {
    private val _itens = MutableLiveData<MutableList<ItemCarrinho>>()
    val itens: LiveData<MutableList<ItemCarrinho>> get() = _itens

    init {
        _itens.value = mutableListOf()
    }

    fun adicionarAoCarrinho(produto: Produto, quantidade: Int) {
        val lista = _itens.value ?: mutableListOf()
        val itemExistente = lista.find { it.produto == produto }
        if (itemExistente != null) {
            val novaQuantidade = quantidade
            val novoItem = itemExistente.copy(quantidade = novaQuantidade)
            lista[lista.indexOf(itemExistente)] = novoItem
        } else {
            lista.add(ItemCarrinho(produto, quantidade))
        }
        _itens.value = lista
    }

}
