package br.udesc.drinkappddm.View

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

        viewModel.itens.observe(this) { itens ->
            itens?.let {
                val adapter = CarrinhoAdapter(this, itens)
                listView.adapter = adapter
            }
        }

        binding.finalizarCompraButton.setOnClickListener {
            // Lógica para finalizar a compra
        }
    }
}
