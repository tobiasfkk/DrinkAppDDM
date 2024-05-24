package br.udesc.drinkappddm.View

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import br.udesc.drinkappddm.Api.ApiClient
import br.udesc.drinkappddm.Api.ApiService
import br.udesc.drinkappddm.R
import br.udesc.drinkappddm.ViewModel.CarrinhoViewModel
import br.udesc.drinkappddm.ViewModel.PagamentoViewModel
import br.udesc.drinkappddm.databinding.ActivityPagamentoBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class PagamentoActivity : AppCompatActivity() {

    private lateinit var viewModel: PagamentoViewModel
    private lateinit var binding: ActivityPagamentoBinding

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPagamentoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(PagamentoViewModel::class.java)

        // Ler o valor total passado como parâmetro
        val total = intent.getDoubleExtra("total", 0.0)

        // Atualizar o TextView com o valor total
        binding.tvTotalPagamento.text = "Total: R$ %.2f".format(total)

        binding.btnPagar.setOnClickListener {
            val nomeCartao = binding.etNomeCartao.text.toString()
            val numeroCartao = binding.etNumeroCartao.text.toString()
            val validadeCartao = binding.etValidadeCartao.text.toString()
            val cvvCartao = binding.etCvvCartao.text.toString()

            viewModel.realizarPagamento(nomeCartao, numeroCartao, validadeCartao, cvvCartao)
            CoroutineScope(Dispatchers.Main).launch {
                val resultado = fetchPaymentValidation()
                Toast.makeText(this@PagamentoActivity, resultado, Toast.LENGTH_SHORT).show()
                term()

            }
        }
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

    private fun term() {
        val intent = Intent(this@PagamentoActivity, CatalogoCategoriaActivity::class.java)
        startActivity(intent)
        val carrinhoViewModel = ViewModelProvider(this).get(CarrinhoViewModel::class.java)
        carrinhoViewModel.limparCarrinho()
        finish()
    }
}
