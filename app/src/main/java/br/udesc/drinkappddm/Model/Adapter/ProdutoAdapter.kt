package br.udesc.drinkappddm.View

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.NumberPicker
import android.widget.TextView
import br.udesc.drinkappddm.Model.Produto
import br.udesc.drinkappddm.R

class ProdutoAdapter(private val context: Context, private val produtos: List<Produto>) : BaseAdapter() {

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
        val view: View
        val holder: ViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.activity_catalogo_produto_detalhes, parent, false)
            holder = ViewHolder(view)
            view.tag = holder
        } else {
            view = convertView
            holder = view.tag as ViewHolder
        }

        val produto = getItem(position)
        holder.produtoNomeTextView.text = produto.nome
        holder.produtoPrecoTextView.text = "Preço: R$ ${produto.preco}"
        holder.produtoDescricaoTextView.text = produto.descricao
        // Você pode definir a imagem do produto aqui se tiver

        // Configurar NumberPicker
        holder.produtoQuantidadePicker.minValue = 1
        holder.produtoQuantidadePicker.maxValue = 5
        holder.produtoQuantidadePicker.value = 1

        view.setOnClickListener {
            if (holder.produtoDescricaoTextView.visibility == View.GONE) {
                holder.produtoPrecoTextView.visibility = View.VISIBLE
                holder.produtoDescricaoTextView.visibility = View.VISIBLE
                holder.produtoQuantidadePicker.visibility = View.VISIBLE
                holder.addToCartButton.visibility = View.VISIBLE
            } else {
                holder.produtoPrecoTextView.visibility = View.GONE
                holder.produtoDescricaoTextView.visibility = View.GONE
                holder.produtoQuantidadePicker.visibility = View.GONE
                holder.addToCartButton.visibility = View.GONE
            }
        }

        holder.addToCartButton.setOnClickListener {
            val quantidade = holder.produtoQuantidadePicker.value
            // Lógica para adicionar ao carrinho com a quantidade selecionada
        }

        return view
    }

    private class ViewHolder(view: View) {
        val produtoNomeTextView: TextView = view.findViewById(R.id.produtoNomeTextView)
        val produtoPrecoTextView: TextView = view.findViewById(R.id.produtoPrecoTextView)
        val produtoDescricaoTextView: TextView = view.findViewById(R.id.produtoDescricaoTextView)
        val produtoQuantidadePicker: NumberPicker = view.findViewById(R.id.produtoQuantidadePicker)
        val addToCartButton: Button = view.findViewById(R.id.addToCartButton)
    }
}
