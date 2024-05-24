package br.udesc.drinkappddm.View

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import br.udesc.drinkappddm.ViewModel.EnderecoViewModel
import br.udesc.drinkappddm.databinding.ActivityEnderecoBinding

class EnderecoActivity : AppCompatActivity() {
    private lateinit var viewModel: EnderecoViewModel
    private lateinit var binding: ActivityEnderecoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEnderecoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(EnderecoViewModel::class.java)

        // Receber o valor total passado como parâmetro
        val total = intent.getDoubleExtra("total", 0.0)

        binding.btnPagar.setOnClickListener {
            val endereco = binding.enderecoRua.text.toString()
            val numero = binding.enderecoNumero.text.toString()
            val bairro = binding.enderecoBairro.text.toString()
            telaPagamento(endereco, numero, bairro, total)
        }
    }

    private fun telaPagamento(endereco: String, numero: String, bairro: String, total: Double) {
        val intent = Intent(this@EnderecoActivity, PagamentoActivity::class.java).apply {
            putExtra("endereco", endereco)
            putExtra("numero", numero)
            putExtra("bairro", bairro)
            putExtra("total", total)
        }
        startActivity(intent)
    }
}
