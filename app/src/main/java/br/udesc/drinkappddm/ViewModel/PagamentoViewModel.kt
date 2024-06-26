package br.udesc.drinkappddm.ViewModel

import androidx.lifecycle.ViewModel
import br.udesc.drinkappddm.Api.ApiClient
import br.udesc.drinkappddm.Api.ApiService
import br.udesc.drinkappddm.Model.Pagamento
import br.udesc.drinkappddm.repository.PagamentoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class PagamentoViewModel : ViewModel() {

    private val pagamentoRepository = PagamentoRepository()

    fun realizarPagamento(
        nomeCartao: String,
        numeroCartao: String,
        validadeCartao: String,
        cvvCartao: String
    ) {
        // Simular o processamento de pagamento
        val pagamentoSimulado = simularPagamentoComCartao(nomeCartao, numeroCartao, validadeCartao, cvvCartao)
    }

    private fun simularPagamentoComCartao(
        nomeCartao: String,
        numeroCartao: String,
        validadeCartao: String,
        cvvCartao: String
    ): Boolean {
        return true // Simulação de pagamento bem-sucedido
    }

    suspend fun fetchPaymentValidation(): String {
        return withContext(Dispatchers.IO) {
            try {
                val response: Response<String> = ApiClient.retrofit.create(ApiService::class.java).getPaymentValidation()
                if (response.isSuccessful && response.code() == 201) {
                    "Pagamento Verificado!" // Use response.body() or default message
                } else {
                    "Erro ao obter validação de pagamento: ${response.message()}"
                }
            } catch (e: Exception) {
                "Erro: ${e.message}"
            }
        }
    }

    suspend fun salvarPagamento(pagamento: Pagamento) {
        pagamentoRepository.realizarPagamentoComCartao(pagamento)
    }
}
