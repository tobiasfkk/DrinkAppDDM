package br.udesc.drinkappddm.repository

import br.udesc.drinkappddm.Model.Categoria
import br.udesc.drinkappddm.Model.ItemCarrinho
import br.udesc.drinkappddm.Model.Produto
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class CarrinhoRepository {
    private val db = FirebaseFirestore.getInstance()
    private val carrinhoCollection = db.collection("carrinho")

    suspend fun addItemCarrinho(itemCarrinho: ItemCarrinho) {
        try {
            carrinhoCollection.add(itemCarrinho).await()
        } catch (e: Exception) {
            // Tratar possíveis exceções aqui
        }
    }

    suspend fun atualizarItemCarrinho(itemCarrinho: ItemCarrinho) {
        try {
            val querySnapshot = carrinhoCollection
                .whereEqualTo("produto.nome", itemCarrinho.produto.nome)
                .get()
                .await()

            if (querySnapshot.documents.isNotEmpty()) {
                val document = querySnapshot.documents[0]
                carrinhoCollection.document(document.id).set(itemCarrinho).await()
            } else {
                addItemCarrinho(itemCarrinho)
            }
        } catch (e: Exception) {
            // Tratar possíveis exceções aqui
        }
    }

    suspend fun obterItensCarrinho(): List<ItemCarrinho> {
        return try {
            val carrinhoSnapshot = carrinhoCollection.get().await()
            val carrinhoList = mutableListOf<ItemCarrinho>()
            for (itemSnapshot in carrinhoSnapshot.documents) {
                val produtoNome = itemSnapshot.getString("produto.nome") ?: continue
                val produtoImagem = itemSnapshot.getString("produto.imagem") ?: continue
                val produtoPreco = itemSnapshot.getDouble("produto.preco") ?: continue
                val produtoCategoriaNome = itemSnapshot.getString("produto.categoria.nome") ?: continue
                val produtoDescricao = itemSnapshot.getString("produto.descricao") ?: continue
                val produtoQuantidadeEstoque = itemSnapshot.getLong("produto.quantidadeEstoque")?.toInt() ?: continue
                val quantidade = itemSnapshot.getLong("quantidade")?.toInt() ?: continue

                val categoria = Categoria(produtoCategoriaNome)
                val produto = Produto(produtoNome, produtoImagem, produtoPreco, categoria, produtoDescricao, produtoQuantidadeEstoque)
                val itemCarrinho = ItemCarrinho(produto, quantidade)
                carrinhoList.add(itemCarrinho)
            }
            carrinhoList
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun limparCarrinho() {
        try {
            val carrinhoSnapshot = carrinhoCollection.get().await()
            for (document in carrinhoSnapshot.documents) {
                carrinhoCollection.document(document.id).delete().await()
            }
        } catch (e: Exception) {
            // Tratar possíveis exceções aqui
        }
    }
}
