package br.udesc.drinkappddm.View

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import br.udesc.drinkappddm.Api.ApiClient
import br.udesc.drinkappddm.Api.ApiService
import br.udesc.drinkappddm.Api.PaymentValidationResponse
import br.udesc.drinkappddm.R
import br.udesc.drinkappddm.R.id
import br.udesc.drinkappddm.ViewModel.PagamentoViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response


class PagamentoActivity : AppCompatActivity() {

    private lateinit var viewModel: PagamentoViewModel

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pagamento)

        viewModel = ViewModelProvider(this).get(PagamentoViewModel::class.java)

        val etNomeCartao = findViewById<EditText>(id.et_nome_cartao)
        val etNumeroCartao = findViewById<EditText>(id.et_numero_cartao)
        val etValidadeCartao = findViewById<EditText>(id.et_validade_cartao)
        val etCvvCartao = findViewById<EditText>(id.et_cvv_cartao)
        val btnPagar = findViewById<Button>(id.btn_pagar)

        btnPagar.setOnClickListener {
            val nomeCartao = etNomeCartao.text.toString()
            val numeroCartao = etNumeroCartao.text.toString()
            val validadeCartao = etValidadeCartao.text.toString()
            val cvvCartao = etCvvCartao.text.toString()

            viewModel.realizarPagamento(nomeCartao, numeroCartao, validadeCartao, cvvCartao)
            CoroutineScope(Dispatchers.Main).launch {
                val resultado = fetchPaymentValidation()
                // Use o resultado aqui
                Toast.makeText(this@PagamentoActivity, resultado, Toast.LENGTH_SHORT).show()
            }
        }

    }
    suspend fun fetchPaymentValidation(): String {
        return withContext(Dispatchers.IO) {
            try {
                val response: Response<String> = ApiClient.retrofit.create(ApiService::class.java).getPaymentValidation()
                if (response.isSuccessful && response.code() == 201) {
                    response.body() ?: "Pagamento Verificado!" // Use response.body() or default message
                } else {
                    "Erro ao obter validação de pagamento: ${response.message()}"
                }
            } catch (e: Exception) {
                "jjjj: ${e.message}"
            }
        }
    }
}