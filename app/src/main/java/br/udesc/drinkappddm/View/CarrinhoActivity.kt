package br.udesc.drinkappddm.View

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import br.udesc.drinkappddm.ViewModel.CarrinhoViewModel
import br.udesc.drinkappddm.databinding.ActivityCarrinhoBinding

class CarrinhoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCarrinhoBinding
    private lateinit var viewModel: CarrinhoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCarrinhoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(CarrinhoViewModel::class.java)

        val listView = binding.carrinhoListView
        val totalTextView = binding.totalTextView

        viewModel.itens.observe(this) { itens ->
            itens?.let {
                val adapter = CarrinhoAdapter(this, it)
                listView.adapter = adapter
                val total = viewModel.calcularTotalCompra()
                totalTextView.text = "Total: R$ %.2f".format(total)
            }
        }

        binding.finalizarCompraButton.setOnClickListener {
            // Lógica para finalizar a compra
            val intent = Intent(this, PagamentoActivity::class.java)
            startActivity(intent)
        }
    }
}
