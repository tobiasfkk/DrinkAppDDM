package br.udesc.drinkappddm.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.udesc.drinkappddm.repository.PagamentoRepository
import br.udesc.drinkappddm.repository.ProdutoRepository

class PagamentoViewModel : ViewModel() {

    private val pagamentoRealizado = MutableLiveData<Boolean>()
    private val pagamentoRepository = PagamentoRepository()
    fun realizarPagamento(
        nomeCartao: String,
        numeroCartao: String,
        validadeCartao: String,
        cvvCartao: String){
        // Simular o processamento de pagamento
        val pagamentoSimulado = simularPagamentoComCartao(nomeCartao, numeroCartao, validadeCartao, cvvCartao)

        // Atualizar o LiveData com o resultado do pagamento
        pagamentoRealizado.value = pagamentoSimulado
    }

    private fun simularPagamentoComCartao(
        nomeCartao: String,
        numeroCartao: String,
        validadeCartao: String,
        cvvCartao: String

    ): Boolean {

        return true // Simulação de pagamento bem-sucedido
    }
}
