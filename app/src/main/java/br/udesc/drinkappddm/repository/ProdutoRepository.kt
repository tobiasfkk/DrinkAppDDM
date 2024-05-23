package br.udesc.drinkappddm.repository

import br.udesc.drinkappddm.Model.Categoria
import br.udesc.drinkappddm.Model.Produto
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class ProdutoRepository {
    private val db = FirebaseFirestore.getInstance()
    private val produtosCollection = db.collection("produtos")

    suspend fun addProduto(produto: Produto) {
        try {
            produtosCollection.add(produto).await()
        } catch (e: Exception) {
            // Tratar possíveis exceções aqui

        }
    }

    suspend fun obterCategorias(): List<Categoria> {
        return try {
            val categoriasSnapshot = db.collection("categorias").get().await()
            val categoriasList = mutableListOf<Categoria>()
            for (categoriaSnapshot in categoriasSnapshot.documents) {
                val nomeCategoria = categoriaSnapshot.getString("nome") ?: continue
                categoriasList.add(Categoria(nomeCategoria))
            }
            categoriasList
        } catch (e: Exception) {
            // Lidar com erros de obtenção das categorias
            emptyList()
        }
    }

    suspend fun obterProdutosPorCategoria(categoria: Categoria): List<Produto> {
        return try {
            val produtosSnapshot = produtosCollection
                .whereEqualTo("categoria.nome", categoria.nome)
                .get()
                .await()
            val produtosList = mutableListOf<Produto>()
            for (produtoSnapshot in produtosSnapshot.documents) {
                val nome = produtoSnapshot.getString("nome") ?: continue
                val imagem = produtoSnapshot.getString("imagem") ?: continue
                val preco = produtoSnapshot.getDouble("preco") ?: continue
                val descricao = produtoSnapshot.getString("descricao") ?: continue
                val quantidadeEstoque = produtoSnapshot.getLong("quantidadeEstoque")?.toInt() ?: continue
                produtosList.add(Produto(nome, imagem, preco, categoria, descricao, quantidadeEstoque))
            }
            produtosList
        } catch (e: Exception) {
            // Lidar com erros de obtenção dos produtos
            emptyList()
        }
    }
}
