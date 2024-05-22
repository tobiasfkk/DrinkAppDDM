package br.udesc.drinkappddm.View

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import br.udesc.drinkappddm.R
import br.udesc.drinkappddm.R.id
import br.udesc.drinkappddm.ViewModel.PagamentoViewModel

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
        }

        viewModel.getPagamentoRealizado().observe(this) { pagamentoRealizado ->
            if (pagamentoRealizado) {
                // Mostrar mensagem de sucesso
                Toast.makeText(this, "Pagamento realizado com sucesso!", Toast.LENGTH_SHORT).show()
            } else {
                // Mostrar mensagem de erro
                Toast.makeText(this, "Erro ao realizar pagamento.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}