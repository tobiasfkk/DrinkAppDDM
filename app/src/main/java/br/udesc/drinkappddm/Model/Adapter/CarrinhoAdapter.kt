package br.udesc.drinkappddm.View

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import br.udesc.drinkappddm.Model.ItemCarrinho
import br.udesc.drinkappddm.R

class CarrinhoAdapter(private val context: Context, private val itens: List<ItemCarrinho>) : BaseAdapter() {

    override fun getCount(): Int {
        return itens.size
    }

    override fun getItem(position: Int): ItemCarrinho {
        return itens[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val holder: ViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_carrinho, parent, false)
            holder = ViewHolder(view)
            view.tag = holder
        } else {
            view = convertView
            holder = view.tag as ViewHolder
        }

        val item = getItem(position)
        holder.produtoNomeTextView.text = item.produto.nome
        holder.produtoQuantidadeTextView.text = "Quantidade: ${item.quantidade}"
        holder.produtoPrecoTextView.text = "Preço: R$ ${item.produto.preco * item.quantidade}"

        return view
    }

    private class ViewHolder(view: View) {
        val produtoNomeTextView: TextView = view.findViewById(R.id.produtoNomeTextView)
        val produtoQuantidadeTextView: TextView = view.findViewById(R.id.produtoQuantidadeTextView)
        val produtoPrecoTextView: TextView = view.findViewById(R.id.produtoPrecoTextView)
    }
}
