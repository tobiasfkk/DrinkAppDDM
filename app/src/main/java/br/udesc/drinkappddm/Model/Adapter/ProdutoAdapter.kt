package br.udesc.drinkappddm.View

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import br.udesc.drinkappddm.Model.Produto
import br.udesc.drinkappddm.R
import br.udesc.drinkappddm.ViewModel.CarrinhoViewModel
import br.udesc.drinkappddm.databinding.ActivityCatalogoProdutoDetalhesBinding

class ProdutoAdapter(private val context: Context, private val produtos: List<Produto>) : BaseAdapter() {

    private val carrinhoViewModel = ViewModelProvider(context as AppCompatActivity).get(CarrinhoViewModel::class.java)

    override fun getCount(): Int {
        return produtos.size
    }

    override fun getItem(position: Int): Produto {
        return produtos[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding: ActivityCatalogoProdutoDetalhesBinding
        val view: View

        if (convertView == null) {
            binding = ActivityCatalogoProdutoDetalhesBinding.inflate(LayoutInflater.from(context), parent, false)
            view = binding.root
            view.tag = binding
        } else {
            binding = convertView.tag as ActivityCatalogoProdutoDetalhesBinding
            view = convertView
        }

        val produto = getItem(position)
        binding.produtoNomeTextView.text = produto.nome
        binding.produtoPrecoTextView.text = "Preço: R$ ${produto.preco}"
        binding.produtoDescricaoTextView.text = produto.descricao

        // Configurar NumberPicker
        binding.produtoQuantidadePicker.minValue = 1
        binding.produtoQuantidadePicker.maxValue = 5
        binding.produtoQuantidadePicker.value = 1

        view.setOnClickListener {
            if (binding.produtoDescricaoTextView.visibility == View.GONE) {
                binding.produtoPrecoTextView.visibility = View.VISIBLE
                binding.produtoDescricaoTextView.visibility = View.VISIBLE
                binding.produtoQuantidadePicker.visibility = View.VISIBLE
                binding.addToCartButton.visibility = View.VISIBLE
            } else {
                binding.produtoPrecoTextView.visibility = View.GONE
                binding.produtoDescricaoTextView.visibility = View.GONE
                binding.produtoQuantidadePicker.visibility = View.GONE
                binding.addToCartButton.visibility = View.GONE
            }
        }

        binding.addToCartButton.setOnClickListener {
            val quantidade = binding.produtoQuantidadePicker.value
            carrinhoViewModel.adicionarAoCarrinho(produto, quantidade)
            Toast.makeText(context, "${produto.nome} adicionado ao carrinho", Toast.LENGTH_SHORT).show()
        }

        return view
    }
}
